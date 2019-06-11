package root.demo.taskService;

import java.util.Collection;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.User;
import root.demo.service.EmailService;

@Service
public class NotifyReviewersService implements JavaDelegate {

	@Autowired
	private EmailService emailService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		System.err.println("Task service: Notify Reviewers");
		Collection<User> users = (Collection<User>) execution.getVariable("recezentiZaService");
		for (User u : users) {
			this.emailService.sendEmail(u.getEmail(), "New article for review", "Dear " + u.getUsername()
					+ ",\nYou got new article for review. Please login to review.\\n\\nLooking forward,\\nNaucna Centrala Team!");

			System.err.println(u.getUsername());
		}

	}

}
