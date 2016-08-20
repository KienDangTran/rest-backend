package com.giong.api.service.master;

import com.giong.api.persistence.oauth.User;
import com.giong.api.repository.oauth.UserRepository;
import com.giong.api.service.BaseService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("userDetailsService")
public class UserService extends BaseService<User, String, UserRepository> implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final List<User> users = this.repository.loadUserByUsername(username);
		final User user = users.isEmpty() ? null : users.get(0);
		if (user == null)
			throw new UsernameNotFoundException("Username Is Not Found");
		return user;
	}

}
