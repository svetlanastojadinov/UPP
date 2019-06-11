//package root.demo.handlers;
//
//import org.camunda.bpm.engine.FormService;
//import org.camunda.bpm.engine.IdentityService;
//import org.camunda.bpm.engine.delegate.DelegateTask;
//import org.camunda.bpm.engine.delegate.TaskListener;
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class EnterDataRegistrationHandler implements TaskListener {
//	@Autowired
//	IdentityService identityService;
//	
//	@Autowired
//	FormService formService;
//	
//	  public void notify(DelegateTask delegateTask) {
//		  System.err.println("UserTask - EnterData : start");
//		  if(delegateTask.getExecution().getVariable("logged").equals("") ){
//			//  delegateTask.get
//		  }
//		 			  
//	  }
//}