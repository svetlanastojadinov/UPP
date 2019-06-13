package root.demo.taskService;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.Article;
import root.demo.model.User;
import root.demo.service.ArticleService;
import root.demo.service.EmailService;
import root.demo.service.UserService;

@Service
public class NotifyAutorShowCommentsService implements JavaDelegate{

	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		System.err.println("TaskService: notify editing");
		Long id = (Long) execution.getVariable("articleId");
		Article a=articleService.findOne(id);
		User u=userService.findOne(a.getAuthor());
		
		this.emailService.sendEmail(u.getEmail(), "Article status",
				"Dear " + u.getUsername() + ",\nYour article need to be edited!\n\nLooking forward,\nNaucna Centrala Team!");
		
	}
}
