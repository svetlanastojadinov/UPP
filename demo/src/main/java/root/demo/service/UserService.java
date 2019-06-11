package root.demo.service;

import java.util.List;

import root.demo.model.User;

public interface UserService {
	User findOne(String username);

	List<User> findAll();

	User save(User user);

	void delete(String username);

	User findByEmail(String email);
}
