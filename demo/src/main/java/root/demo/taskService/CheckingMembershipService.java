package root.demo.taskService;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class CheckingMembershipService implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("TaskService: CheckingMembership "+execution.getVariable("author")); 

        execution.setVariable("paid", true);
		
	}

}
