package root.demo.taskService;

import java.util.Collection;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.service.EmailService;
import root.demo.service.UserService;

@Service
public class NotifyEditorOfAreaService implements JavaDelegate {

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserService userService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		System.err.println("Task Service: Notify editor of Area");
		Long id = (Long) execution.getVariable("articleId");
		String chief = (String) execution.getVariable("chiefEditor");
		Collection<String> rec = (Collection<String>) execution.getVariable("recezenti");
		String message = "";
		for (String r : rec) {
			String desicion = (String) execution.getVariable("recezent" + r);
			message += r + ": " + desicion + "\n\n";
		}

		this.emailService.sendEmail(userService.findOne(chief).getEmail(), "Review - done",
				"Dear " + chief + ",\nFor article with id " + id + ":\n" + message
						+ "Please, login to procced further: http://localhost:4200/aprove-article1/"+id+"\n\nLooking forward,\nNaucna Centrala Team!");
		System.err.println("email sent! to chief :O");

	}

}
