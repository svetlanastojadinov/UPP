package root.demo.taskService;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.service.EmailService;

@Service
public class UnsuccessfulRegistrationService implements JavaDelegate{
	
	@Autowired
	EmailService emailService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		 String email = (String) execution.getVariable("email");
	        this.emailService.sendEmail(email, "Unsuccessful registration",
	                "Unfortunately your registration was unsuccessful. Please, try again.");
	}

}
