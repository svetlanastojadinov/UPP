package root.demo.taskService;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.dto.FormSubmissionDto;
import root.demo.repository.UserRepository;
import root.demo.service.EmailService;

@Service
public class SuccessfulRegistrationService implements JavaDelegate {

	@Autowired
	IdentityService identityService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	EmailService emailService;

	@SuppressWarnings("unchecked")
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("ServiceTask - SuccessfulRegistration: executed");

		List<FormSubmissionDto> registration = (List<FormSubmissionDto>) execution.getVariable("registration_process");
		System.out.println("ServiceTask - Registration: executed");
		User user = identityService.newUser("");

		String city = "";
		String country = "";
		String pass = "";

		if (registration != null)
			for (FormSubmissionDto formField : registration) {

				if (formField.getFieldId().equals("username")) {

					user.setId(formField.getFieldValue());
				}

				if (formField.getFieldId().equals("email")) {

					user.setEmail(formField.getFieldValue());
				}

				if (formField.getFieldId().equals("password")) {
					user.setPassword(formField.getFieldValue());
					pass = formField.getFieldValue();
				}
				if (formField.getFieldId().equals("lastname")) {
					user.setLastName(formField.getFieldValue());
				}
				if (formField.getFieldId().equals("firstname")) {
					user.setFirstName(formField.getFieldValue());
				}
				if (formField.getFieldId().equals("city")) {
					city = formField.getFieldValue();
				}
				if (formField.getFieldId().equals("country")) {
					country = formField.getFieldValue();
				}
			}
		root.demo.model.User user1 = new root.demo.model.User();
		user1.setUsername(user.getId());
		user1.setName(user.getFirstName());
		user1.setSurname(user.getLastName());
		user1.setPassword(pass);
		user1.setEmail(user.getEmail());
		user1.setCity(city);
		user1.setCountry(country);

		userRepository.save(user1);
		identityService.saveUser(user);

		this.emailService.sendEmail(user.getEmail(), "Successful registration",
				"Your registration with username: " + user1.getUsername() + " was successful!");

	}
}
