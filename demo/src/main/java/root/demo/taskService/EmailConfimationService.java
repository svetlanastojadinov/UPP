package root.demo.taskService;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.service.EmailService;

@Service
public class EmailConfimationService implements JavaDelegate{
	
	@Autowired
    private EmailService emailService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("TaskService: EmailConfirmation");
		
		String email = (String) execution.getVariable("email");
        String id = execution.getProcessInstanceId();
        this.emailService.sendEmail(email, "Registration",
                "Please, confirm your email on link: \n http://localhost:4200/registration/confirm/" + id);
        System.out.println("email sent! :O");
		
	}

}
