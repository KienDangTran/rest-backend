package com.giong.api.service;

import com.giong.api.domain.User;
import com.giong.api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service("userService")
public class UserService extends BaseService<User, String, UserRepository> {

	public Optional<User> loadUserByUsername(String username) {
		return this.repository.loadUserByUsername(username);
	}

}
