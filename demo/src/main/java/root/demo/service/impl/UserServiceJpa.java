package root.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.User;
import root.demo.repository.UserRepository;
import root.demo.service.UserService;

@Service
public class UserServiceJpa implements UserService{
	
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User findOne(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public void delete(String username) {
		userRepository.deleteById(username);
		
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	

}
