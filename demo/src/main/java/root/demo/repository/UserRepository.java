package root.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import root.demo.model.User;

public interface UserRepository extends JpaRepository<User, String>{
	public User findByEmail(String email);

	public User findByUsername(String username);
}
