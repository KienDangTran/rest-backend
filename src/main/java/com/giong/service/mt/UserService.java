package com.giong.service.mt;

import com.giong.repository.mt.UserRepository;
import com.giong.persistence.mt.MtUser;
import com.giong.service.BaseService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("userDetailsService")
public class UserService extends BaseService<MtUser, String, UserRepository> implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final List<MtUser> users = this.repository.loadUserByUsername(username);
		final MtUser user = users.isEmpty() ? null : users.get(0);
		if (user == null)
			throw new UsernameNotFoundException("Username Is Not Found");
		return user;
	}

}
