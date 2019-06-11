package root.demo.taskService;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.Magazine;
import root.demo.model.User;
import root.demo.repository.UserRepository;
import root.demo.service.EmailService;

@Service
public class EmailPickingChiefEditorService implements JavaDelegate {

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserRepository userRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		System.err.println("TaskService: EmailPickingChiefEditor");
		Magazine magazine = (Magazine) execution.getVariable("magazine");
		User chiefEditor = userRepository.findByUsername(magazine.getUser().getUsername());
		execution.setVariable("chiefEditor", chiefEditor.getUsername());
		User author = userRepository.findByUsername((String) execution.getVariable("author"));

		this.emailService.sendEmail(chiefEditor.getEmail(), "New article!",
				"Dear " + chiefEditor.getName() + ",\nThere is new article in magazine " + magazine.getTitle()
						+ ". Please, login in to review.\n\nLooking forward,\nNaucna Centrala Team!");

		this.emailService.sendEmail(author.getEmail(), "New article submition",
				"Dear " + author.getName() + ",\nYou successfuly submited new article to magazine "
						+ magazine.getTitle()
						+ ". Please, wait to respond.\n\nLooking forward,\nNaucna Centrala Team!");

	}

}
