package root.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import root.demo.model.Magazine;
import root.demo.model.User;

@Repository
public interface MagazineRepository extends JpaRepository<Magazine, String> {

	public Magazine findByIssn(String issn);


}