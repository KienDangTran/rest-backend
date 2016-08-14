package com.giong.repository.mt;

import com.giong.persistence.mt.MtUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<MtUser, String> {

	@Query("SELECT e FROM MtUser e WHERE e.username = :username")
	List<MtUser> loadUserByUsername(@Param("username") String username);
}
