package root.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import root.demo.model.Article;
import root.demo.model.ArticleStatus;
import root.demo.model.Magazine;
import root.demo.model.Subscription;
import root.demo.model.User;
import root.demo.model.dto.FormSubmissionDto;
import root.demo.service.ArticleService;
import root.demo.service.MagazineService;
import root.demo.service.ScientificAreaService;

@RestController
@RequestMapping(value = "/api/articles")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private MagazineService magazineService;

	@Autowired
	private ScientificAreaService scAreaService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private FormService formService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<Article>> getArticles() {

		ArrayList<Article> articles = (ArrayList<Article>) articleService.findAll();
		if (articles != null) {
			return new ResponseEntity<Collection<Article>>(articles, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	//http://localhost:8080/api/articles/"+taskId+"/aproveFinal="+article+"/des="+des
	@RequestMapping(value = "/{taskId}/aproveFinal={article}/des={finalDes}", method = RequestMethod.POST)
	private ResponseEntity<?> finalDesicion(@PathVariable String taskId, @PathVariable String article,
			@PathVariable String finalDes) {
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();

		Article a=articleService.findOne(Long.parseLong(article));
		if(finalDes.equals("Accept"))
			a.setStatus(ArticleStatus.PRIHVACEN);
		else if(finalDes.equals("Reject"))
			a.setStatus(ArticleStatus.ODBIJEN);
		else
			a.setStatus(ArticleStatus.EDITUJE_SE);
		runtimeService.setVariable(processInstanceId, "finalDes", finalDes);
		System.err.println("final desicion "+finalDes);
		formService.submitTaskForm(taskId, null);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/recezent={username}", method = RequestMethod.GET)
	public ResponseEntity<Article> getArticlesForRecezent(@PathVariable String username) {
		for (Article a : articleService.findAll()) {
			for (User u : a.getRecezenti()) {
				if (u.getUsername().equals(username)) {
					return new ResponseEntity<>(a, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	private ResponseEntity<Article> getArticleById(@PathVariable long id) {
		Article article = articleService.findOne(id);
		return new ResponseEntity<Article>(article, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	private ResponseEntity<Article> saveArticle(@Valid @RequestBody Article article) {

		Article savedArticle = articleService.save(article);

		return new ResponseEntity<Article>(savedArticle, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	private ResponseEntity<Article> updateArticle(@PathVariable long id, @Valid @RequestBody Article article) {

		Article updatedArticle = articleService.findOne(id);

		if (article.getAuthor() != updatedArticle.getAuthor()) {
			updatedArticle.setAuthor(article.getAuthor());
		}
		if (article.getTitle() != updatedArticle.getTitle()) {
			updatedArticle.setTitle(article.getTitle());
		}
		articleService.update(updatedArticle, id);
		return new ResponseEntity<Article>(updatedArticle, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	private ResponseEntity<Article> deleteArticle(@PathVariable long id) {

		articleService.delete(articleService.findOne(id));

		return new ResponseEntity<Article>(HttpStatus.OK);
	}

	@RequestMapping(value = "/pickMagazine/{issn}/{author}/{taskId}", method = RequestMethod.POST)
	private ResponseEntity<Article> pickMagazine(@PathVariable String issn, @PathVariable String author,
			@PathVariable String taskId) {
		System.out.println("izabrao magazin " + issn);
		Magazine magazine = magazineService.findOne(issn);

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();

		if (magazine.getSubscription() == Subscription.OPEN_ACCESS) {
			System.out.println("open access");
			runtimeService.setVariable(processInstanceId, "openAccess", true);
		} else {
			System.out.println("reader pays");
			runtimeService.setVariable(processInstanceId, "openAccess", false);

		}
		runtimeService.setVariable(processInstanceId, "author", author);
		runtimeService.setVariable(processInstanceId, "magazine", magazine);
		formService.submitTaskForm(taskId, null);

		return new ResponseEntity<Article>(new Article(), HttpStatus.OK);
	}

	@RequestMapping(value = "/add={taskId}/id={id}/title={title}/area={area}/keyWords={keyWords}/abstract={abst}/user={username}", method = RequestMethod.POST)
	private ResponseEntity<String> addArticle(@PathVariable String taskId, @PathVariable String id,
			@PathVariable String title, @PathVariable String area, @PathVariable String keyWords,
			@PathVariable String abst, @PathVariable String username, @RequestParam("file") MultipartFile file) {
		String message = "";

		try {
			System.out.println("1.1");
			String filenamePath = saveUploadedFile(file);
			System.out.println(filenamePath);
			message = "You successfully uploaded file to" + filenamePath + "!";
			Article newArt = new Article();
			newArt.setTitle(title.replace('+', ' '));
			newArt.setAbst(abst.replace('+', ' '));
			newArt.setKeyWords(keyWords.replace('+', ' '));
			newArt.setFilePath(filenamePath);
			newArt.setAuthor(username);

			Magazine mag = magazineService.findOne(id);
			newArt.setChiefEditor(mag.getUser().getUsername());
			newArt.setScArea(scAreaService.findOneByName(area));

			mag.addArticle(newArt);

			articleService.save(newArt);
			System.out.println("Uspesno ste submitovali artikal!");

			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			String processInstanceId = task.getProcessInstanceId();
			runtimeService.setVariable(processInstanceId, "articleId", newArt.getId());

			formService.submitTaskForm(taskId, null);

			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/{taskId}/aprove={articleId}", method = RequestMethod.POST)
	private ResponseEntity<?> aproveArticle(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId,
			@PathVariable String articleId) {
		HashMap<String, Object> map = this.mapListToDto(dto);
		Article article = articleService.findOne(Long.parseLong(articleId));
		System.err.println("artikal " + article.getTitle() + " odluka " + dto.get(0).getFieldValue());
		if (dto.get(0).getFieldValue().equals("Article is irelevant")) {
			article.setStatus(ArticleStatus.ODBIJEN);
		} else if (dto.get(0).getFieldValue().equals("Article should be corrected")) {
			article.setStatus(ArticleStatus.EDITUJE_SE);
		} else if (dto.get(0).getFieldValue().equals("Accept article")) {
			article.setStatus(ArticleStatus.RECEZENTIRA_SE);
		}
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "des", map.get("des"));
		formService.submitTaskForm(taskId, null);

		return new ResponseEntity(HttpStatus.OK);

	}

	@RequestMapping(value = "/chief={username}", method = RequestMethod.GET)
	public ResponseEntity<Collection<Article>> getArticlesForChiefEditor(@PathVariable String username) {
		ArrayList<Article> articles = (ArrayList<Article>) articleService.findAll();
		ArrayList<Article> articles1 = new ArrayList<>();
		ArrayList<Magazine> mags = (ArrayList<Magazine>) magazineService.findAll();
		if (articles != null) {
			for (Magazine m : mags) {
				if (m.getUser().getUsername().equals(username)) {
					for (Article a : articles) {
						if (m.getArea().getName().equals(a.getScArea().getName())) {
							if (a.getStatus() == ArticleStatus.EDITUJE_SE
									|| a.getStatus() == ArticleStatus.PREGLEDA_SE) {
								articles1.add(a);
							}
						}
					}
				}
			}
			return new ResponseEntity<Collection<Article>>(articles1, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	// task="+taskId+"/des="+des+'/c='+comment+'/c2='+commentForEditor+'/id='+id+'/rec='+recez
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/task={taskId}/des={des}/c={comment}/c2={commentForChief}/id={id}/rec={rec}", method = RequestMethod.POST)
	private ResponseEntity<?> aproveArticleRecezent(@PathVariable String taskId, @PathVariable String des,
			@PathVariable String comment, @PathVariable String commentForChief, @PathVariable String id,
			@PathVariable String rec) {

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		System.out.println("taskid " + taskId + " des " + des+" com"+comment+"  "+commentForChief+"  "+id+"  "+rec);
		String processInstanceId = task.getProcessInstanceId();
		String desicion="For "+id+".\nDesicion: "+des+". Comment: "+comment+".\nComment for chief: "+commentForChief;
		runtimeService.setVariable(processInstanceId, "recezent"+rec, desicion);

		formService.submitTaskForm(taskId, null);
		return new ResponseEntity(HttpStatus.OK);

	}

	private String saveUploadedFile(MultipartFile file) throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		File filePath = new File(classLoader.getResource("application.properties").getFile());
		String retVal = null;
		if (!file.isEmpty()) {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(filePath.getAbsolutePath().replace("application.properties", "files/")
					+ file.getOriginalFilename());
			Files.write(path, bytes);
			retVal = path.toString();
		}
		return retVal;
	}

	private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		for (FormSubmissionDto temp : list) {
			map.put(temp.getFieldId(), temp.getFieldValue());
		}

		return map;
	}

}
