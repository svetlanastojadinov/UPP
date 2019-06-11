package root.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.ScientificArea;
import root.demo.repository.ScientificAreaRepository;
import root.demo.service.ScientificAreaService;

@Service
public class ScientificAreaJpa implements ScientificAreaService {

	@Autowired
	ScientificAreaRepository scientificAreaRepository;

	@Override
	public List<ScientificArea> findAll() {
		// TODO Auto-generated method stub
		return scientificAreaRepository.findAll();
	}

	@Override
	public ScientificArea save(ScientificArea scientificArea) {
		// TODO Auto-generated method stub
		return scientificAreaRepository.save(scientificArea);
	}

	@Override
	public void delete(ScientificArea scientificArea) {
		// TODO Auto-generated method stub
		scientificAreaRepository.delete(scientificArea);
	}

	@Override
	public ScientificArea update(ScientificArea scientificArea, String id) {
		// TODO Auto-generated method stub
		ScientificArea scientificAreaToUpdate = scientificAreaRepository.getOne(id);
		scientificAreaToUpdate.setName(scientificArea.getName());
		scientificAreaToUpdate.setYear(scientificArea.getYear());
		return null;
	}

	@Override
	public ScientificArea findOne(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ScientificArea findOneByName(String name) {
		ScientificArea scientificArea = scientificAreaRepository.findByName(name);
		return scientificArea;
	}

}
