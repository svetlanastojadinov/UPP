package root.demo.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import root.demo.model.Article;
import root.demo.model.Magazine;
import root.demo.model.User;
import root.demo.service.ArticleService;
import root.demo.service.MagazineService;

@RestController
@RequestMapping(value = "/api/magazines")
public class MagazineController {

	@Autowired
	private MagazineService magazineService;
	
	@Autowired
	private ArticleService articleService;

	@GetMapping
	public ResponseEntity<Collection<Magazine>> getMagazines() {

		return new ResponseEntity<Collection<Magazine>>(magazineService.findAll(), HttpStatus.OK);

	}

	@RequestMapping(value = "/{issn}", method = RequestMethod.GET)
	public ResponseEntity<Magazine> getMagazine(@PathVariable String issn) {
		Magazine magazine = magazineService.findOne(issn);

		return new ResponseEntity<Magazine>(magazine, HttpStatus.OK);

	}
	@RequestMapping(value = "/article={id}", method = RequestMethod.GET)
	public ResponseEntity<Magazine> getMagazineForArticle(@PathVariable String id) {
		Magazine magazine=null;
		for(Magazine m:magazineService.findAll()) {
			for(Article a:m.getArticles()) {
				if(a.getId()==Long.parseLong(id)) {
					magazine=m;
					return new ResponseEntity<Magazine>(magazine, HttpStatus.OK);

				}
			}
		}
		
		return new ResponseEntity<Magazine>(magazine, HttpStatus.NOT_FOUND);

	}
	@RequestMapping(value = "/recezenti/mag={magId}", method = RequestMethod.GET)
	public ResponseEntity<Collection<User>> getRecezentiMag(@PathVariable String magId) {
		
		Magazine m = magazineService.findOne(magId);
		if(m.getRecezenti()!=null) {
			System.err.println("Za magazin "+m.getTitle() +" ima "+m.getRecezenti().size());
		}

		return new ResponseEntity<Collection<User>>(m.getRecezenti(), HttpStatus.OK);

	}
}
