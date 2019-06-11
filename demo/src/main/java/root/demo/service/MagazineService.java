package root.demo.service;

import java.util.List;

import root.demo.model.Magazine;

public interface MagazineService {
	
	public Magazine findOne(String issn);

	public List<Magazine> findAll();

	public Magazine save(Magazine magazine);

	public void delete(Magazine magazine);

	public Magazine update(Magazine magazine, String issn);

}
