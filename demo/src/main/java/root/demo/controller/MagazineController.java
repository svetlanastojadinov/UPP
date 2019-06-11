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

import root.demo.model.Magazine;
import root.demo.model.User;
import root.demo.service.MagazineService;

@RestController
@RequestMapping(value = "/api/magazines")
public class MagazineController {

	@Autowired
	private MagazineService magazineService;

	@GetMapping
	public ResponseEntity<Collection<Magazine>> getMagazines() {

		return new ResponseEntity<Collection<Magazine>>(magazineService.findAll(), HttpStatus.OK);

	}

	@RequestMapping(value = "/{issn}", method = RequestMethod.GET)
	public ResponseEntity<Magazine> getMagazine(@PathVariable String issn) {
		Magazine magazine = magazineService.findOne(issn);

		return new ResponseEntity<Magazine>(magazine, HttpStatus.OK);

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
