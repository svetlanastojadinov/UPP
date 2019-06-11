package root.demo.service;

import java.util.List;

import root.demo.model.ScientificArea;

public interface ScientificAreaService {

	public ScientificArea findOne(String id);

	public ScientificArea findOneByName(String name);

	public List<ScientificArea> findAll();

	public ScientificArea save(ScientificArea scientificArea);

	public void delete(ScientificArea scientificArea);

	public ScientificArea update(ScientificArea scientificArea, String id);

}
