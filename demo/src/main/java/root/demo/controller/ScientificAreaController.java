package root.demo.controller;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import root.demo.model.ScientificArea;
import root.demo.service.ScientificAreaService;

@RestController
@RequestMapping(value = "/api/scientificarea")
public class ScientificAreaController {

	@Autowired
	private ScientificAreaService scientificAreaService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<ScientificArea>> getScientificAreas() {

		ArrayList<ScientificArea> sc = (ArrayList<ScientificArea>) scientificAreaService.findAll();
		if (sc != null) {
			return new ResponseEntity<Collection<ScientificArea>>(sc, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	private ResponseEntity<ScientificArea> getScientificArea(@PathVariable String id) {
		ScientificArea sc = scientificAreaService.findOne(id);
		return new ResponseEntity<ScientificArea>(sc, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	private ResponseEntity<ScientificArea> saveScientificArea(@Valid @RequestBody ScientificArea sc) {

		ScientificArea ssc = scientificAreaService.save(sc);

		return new ResponseEntity<ScientificArea>(ssc, HttpStatus.CREATED);
	}

}
