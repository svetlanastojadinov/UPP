package root.demo.taskService;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.User;
import root.demo.repository.UserRepository;
import root.demo.service.EmailService;

@Service
public class NotifyAuthorEditingService implements JavaDelegate{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("TaskService: Notify author editing");
		User author=userRepository.findByUsername((String)execution.getVariable("author"));
		this.emailService.sendEmail(author.getEmail(), "Submitted article",
                "Dear "+author.getName()+",\nYour submited article need to be edited in next hour. Please submit your article again.\n\nLooking forward,\nNaucna Centrala Team!" );
		
	
	}
	
}
