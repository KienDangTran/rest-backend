package com.giong.api.repository.oauth;

import com.giong.api.persistence.oauth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

	@Query("SELECT u FROM User u WHERE u.username = :username")
	List<User> loadUserByUsername(@Param("username") String username);
}
