package com.giong.api.repository.oauth;

import com.giong.api.persistence.oauth.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Authority, String> {
}
