package root.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import root.demo.model.dto.FormFieldsDto;
import root.demo.model.dto.FormSubmissionDto;
import root.demo.model.dto.TaskDto;

@Controller
@RequestMapping("/welcome")
public class DummyController {
	@Autowired
	IdentityService identityService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	TaskService taskService;

	@Autowired
	FormService formService;

	@GetMapping(path = "/start/{process}", produces = "application/json")
	public @ResponseBody FormFieldsDto startProcess(@PathVariable String process) {
		// provera da li korisnik sa id-jem pera postoji
		// List<User> users = identityService.createUserQuery().userId("pera").list();
		System.err.println("process:" + process);
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(process);

		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);

		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		for (FormField fp : properties) {
			System.out.println(fp.getId() + fp.getType());
		}

		return new FormFieldsDto(task.getId(), pi.getId(), properties);
	}

	@GetMapping(path = "/get/tasks/{processInstanceId}", produces = "application/json")
	public @ResponseBody ResponseEntity<List<TaskDto>> get(@PathVariable String processInstanceId) {

		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
		List<TaskDto> dtos = new ArrayList<TaskDto>();
		for (Task task : tasks) {
			TaskDto t = new TaskDto(task.getId(), task.getName(), task.getAssignee());
			dtos.add(t);
		}

		return new ResponseEntity(dtos, HttpStatus.OK);
	}

	@GetMapping(path = "/{processName}/task/{taskName}", produces = "application/json")
	public @ResponseBody FormFieldsDto getTask(@PathVariable String processName, @PathVariable String taskName) {

		Task task = taskService.createTaskQuery().taskName(taskName).singleResult();
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(processName);

		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		for (FormField fp : properties) {
			System.out.println(fp.getId() + fp.getType());
		}

		return new FormFieldsDto(task.getId(), pi.getId(), properties);

	}

	@GetMapping(path = "/{processName}/task/{taskName}/{username}", produces = "application/json")
	public @ResponseBody FormFieldsDto getTaskWithA(@PathVariable String processName, @PathVariable String taskName,
			@PathVariable String username) {

		ProcessInstance pi = runtimeService.startProcessInstanceByKey(processName);
		List<Task> tasks = taskService.createTaskQuery().list();
		Task task = null;
		for (Task t : tasks) {
			if (t.getName().equals(taskName) && t.getAssignee().equals(username)) {
				task = t;
			}
		}
	
		return new FormFieldsDto(task.getId(), pi.getId(), null);

	}

	@PostMapping(path = "/post/{taskId}", produces = "application/json")
	public @ResponseBody ResponseEntity post(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);
		System.err.println("POST MEDOTA");

		// list all running/unsuspended instances of the process
		// ProcessInstance processInstance =
		// runtimeService.createProcessInstanceQuery()
		// .processDefinitionKey("Process_1")
		// .active() // we only want the unsuspended process instances
		// .list().get(0);

		// Task task =
		// taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "registration_process", dto);
		formService.submitTaskForm(taskId, map);

		// complete(task.getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(path = "/tasks/claim/{taskId}", produces = "application/json")
	public @ResponseBody ResponseEntity claim(@PathVariable String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		String user = (String) runtimeService.getVariable(processInstanceId, "username");
		taskService.claim(taskId, user);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(path = "/tasks/complete/{taskId}", produces = "application/json")
	public @ResponseBody ResponseEntity<List<TaskDto>> complete(@RequestBody List<FormSubmissionDto> dto,
			@PathVariable String taskId) {

		HashMap<String, Object> map = this.mapListToDto(dto);
		System.err.println("POST MEDOTA");

		// list all running/unsuspended instances of the process
		// ProcessInstance processInstance =
		// runtimeService.createProcessInstanceQuery()
		// .processDefinitionKey("Process_1")
		// .active() // we only want the unsuspended process instances
		// .list().get(0);

		// Task task =
		// taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "registration_process", dto);
		formService.submitTaskForm(taskId, map);

		System.out.println("COMPLETE");
		Task taskTemp = taskService.createTaskQuery().taskId(taskId).singleResult();
		taskService.complete(taskId);
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(taskTemp.getProcessInstanceId()).list();
		List<TaskDto> dtos = new ArrayList<TaskDto>();
		for (Task task1 : tasks) {
			TaskDto t = new TaskDto(task1.getId(), task1.getName(), task1.getAssignee());
			dtos.add(t);
		}
		return new ResponseEntity<List<TaskDto>>(dtos, HttpStatus.OK);
	}

	private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		for (FormSubmissionDto temp : list) {
			map.put(temp.getFieldId(), temp.getFieldValue());
		}

		return map;
	}
}
