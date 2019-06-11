package root.demo.service;

import java.util.List;
import java.util.Optional;

import root.demo.model.Membership;


public interface MembershipService {
	
	public Optional<Membership> findOne(Long id);

	public List<Membership> findAll();

	public Membership save(Membership membership);

	public void delete(Long id);

}
