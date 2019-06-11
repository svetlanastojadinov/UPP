package root.demo.taskService;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.exceptions.ExistingUserException;
import root.demo.model.dto.FormSubmissionDto;
import root.demo.repository.UserRepository;

@Service
public class RegistrationService implements JavaDelegate {

	@Autowired
	IdentityService identityService;

	@Autowired
	UserRepository userRepository;

	private String logged = "";

	@SuppressWarnings("unchecked")
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		List<FormSubmissionDto> registration = (List<FormSubmissionDto>) execution.getVariable("registration_process");
		System.out.println("ServiceTask - Registration: executed");

		String var = "";

		if (registration != null)
			for (FormSubmissionDto formField : registration) {

				if (formField.getFieldId().equals("username")) {
					if (userRepository.findByUsername(formField.getFieldValue()) != null) {
						execution.setVariable("logged", var);
						// return;
						throw new ExistingUserException(formField.getFieldValue());
					}
					var = formField.getFieldValue();
				}

				if (formField.getFieldId().equals("email")) {
					if (userRepository.findByEmail(formField.getFieldValue()) != null) {
						execution.setVariable("logged", var);
						// return;
						throw new ExistingUserException(formField.getFieldValue());
					}
				}

			}
		var = var.toUpperCase();
		execution.setVariable("logged", var);
	}
}
