package root.demo.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import root.demo.exceptions.BadLoginCredentialsException;
import root.demo.model.Article;
import root.demo.model.Magazine;
import root.demo.model.User;
import root.demo.model.dto.FormSubmissionDto;
import root.demo.model.dto.LoginDto;
import root.demo.model.role.RoleName;
import root.demo.repository.MagazineRepository;
import root.demo.service.ArticleService;
import root.demo.service.MagazineService;
import root.demo.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	FormService formService;

	@Autowired
	TaskService taskService;

	@Autowired
	private UserService userService;

	@Autowired
	private MagazineService magazineService;
	
	@Autowired
	private ArticleService articleService;

	@Autowired
	private RuntimeService runtimeService;

	@SuppressWarnings("rawtypes")
	@PostMapping(path = "/registration/{taskId}", produces = "application/json")
	public @ResponseBody ResponseEntity post(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {

		HashMap<String, Object> map = this.mapListToDto(dto);

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "registration_process", dto);
		formService.submitTaskForm(taskId, map);

		return new ResponseEntity(HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping(path = "/registration/confirm/{processId}", produces = "application/json")
	public @ResponseBody ResponseEntity registrationConfirm(@PathVariable("processId") String processId) {

		HashMap<String, Object> map = new HashMap<>();
		map.put("emailVerified", true);
		Task task = taskService.createTaskQuery().processInstanceId(processId).list().get(0);
		formService.submitTaskForm(task.getId(), map);
		System.out.println("Potvrdjen email");

		return new ResponseEntity(HttpStatus.OK);
	}

	@RequestMapping(path = "/recezenti/mag={magId}", method = RequestMethod.GET)
	public ResponseEntity<Collection<User>> getRecezentiMag(@PathVariable String magId) {

		Magazine m = magazineService.findOne(magId);
		System.out.println("za magazine " + m.getTitle());
		if (m.getRecezenti() != null) {
			System.out.println(" ima " + m.getRecezenti().size() + " dostupnih recezenata");
		}

		return new ResponseEntity<Collection<User>>(m.getRecezenti(), HttpStatus.OK);

	}

	@RequestMapping(path = "/recezenti/{area}", method = RequestMethod.GET)
	public ResponseEntity<Collection<User>> getRecezenti(@PathVariable String area) {

		ArrayList<User> all = (ArrayList<User>) userService.findAll();
		ArrayList<User> rec = new ArrayList<>();
		if (all != null) {
			for (User a : all) {
				if (a.getArea().getId().equals(area) && a.getRole() == RoleName.RECEZENT) {
					rec.add(a);
				}
			}
			return new ResponseEntity<Collection<User>>(rec, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(path = "/izabrani_recezenti/{taskId}/issn={issn}", method = RequestMethod.POST)
	public ResponseEntity<String> setRecezenti(@PathVariable String taskId,@PathVariable String issn,@RequestBody String users) {
		Set<User> recez =new HashSet<>();
		Collection<String>rec2=new ArrayList<String>();
		Article a=articleService.findOne(Long.parseLong(issn));
		if (users.contains("\",\"")) {
			String[] usernames = users.substring(2, users.length() - 2).split("\",\"");
			for (String u : usernames) {
				recez.add(userService.findOne(u));
				rec2.add(u);
				System.err.println("Za article "+issn +" dodat " + userService.findOne(u).getUsername());
			}
		}else if(users.contains("[\"")){
			System.err.println(users);
			recez.add(userService.findOne(users.substring(2, users.length() - 2)));
			rec2.add(users.substring(2, users.length() - 2));
		}else {
			System.err.println(users);
			recez.add(userService.findOne(users));
			rec2.add(users);
		}
		a.setRecezenti(recez);
		for(User u:recez) {
		userService.save(u);
		}
		articleService.save(a);

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "recezenti", rec2);
		runtimeService.setVariable(processInstanceId, "recezentiZaService", recez);
		formService.submitTaskForm(taskId, null);
		
					
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		for (FormSubmissionDto temp : list) {
			map.put(temp.getFieldId(), temp.getFieldValue());
		}

		return map;
	}

	@PostMapping(path = "/login", produces = "application/json")
	public @ResponseBody ResponseEntity<User> login(@RequestBody LoginDto loginDto) {
		User user = userService.findOne(loginDto.getUsername());
		if (user == null || !user.getPassword().equals(loginDto.getPassword())) {
			System.out.println("bad cred");
			throw new BadLoginCredentialsException();
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

}
