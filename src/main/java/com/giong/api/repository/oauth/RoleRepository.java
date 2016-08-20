package com.giong.api.repository.oauth;

import com.giong.api.persistence.oauth.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
