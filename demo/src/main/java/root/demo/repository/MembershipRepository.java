package root.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import root.demo.model.Membership;

public interface MembershipRepository extends JpaRepository<Membership, Long>{

}
