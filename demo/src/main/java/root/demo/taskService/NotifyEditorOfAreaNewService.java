package root.demo.taskService;

import java.util.List;
import java.util.Set;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.Article;
import root.demo.model.Magazine;
import root.demo.model.ScientificArea;
import root.demo.model.User;
import root.demo.service.ArticleService;
import root.demo.service.EmailService;
import root.demo.service.MagazineService;
import root.demo.service.UserService;

@Service
public class NotifyEditorOfAreaNewService implements JavaDelegate {

	@Autowired
	private UserService userService;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private MagazineService magazineService;

	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	EmailService emailService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		System.err.println("TaskService: notify all (recenziranje)");
		long articleId = (long) execution.getVariable("articleId");
		Magazine mag = (Magazine) execution.getVariable("magazine");

		Article article = articleService.findOne(articleId);
		ScientificArea scArea = article.getScArea();
		User urednik = userService.findOne(scArea.getUrednik());	//EDITOR OF AREA- ako postoji bira najmanje 2 recezenta, ako ih nema recezent postaje glavni urednik
		List<Magazine> allMag = magazineService.findAll();

		if (urednik == null) {
			System.out.println("Ne postoji glavni urednik oblasti");
			// glavni urednik
			for (Magazine m : allMag) {
				if (m.getArticles().contains(article)) {
					urednik = m.getUser();							//CHIEF EDITOR
				}
			}
		}
		execution.setVariable("urednikOblasti", urednik.getUsername());
		execution.setVariable("recezenti", findRecezentiByMagazine(mag.getIssn()));

		this.emailService.sendEmail(urednik.getEmail(), "Article for review", "Dear " + urednik.getName()
				+ ",\nYou have new article in scientific area. Please, choose reviewers.\n\nLooking forward,\nNaucna Centrala Team!");

	}

	public Set<User> findRecezentiByMagazine(String magId) {
		Magazine m = magazineService.findOne(magId);
		return m.getRecezenti();
	}

}
