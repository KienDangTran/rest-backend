package com.giong.repository.mt;

import com.giong.persistence.mt.MtPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<MtPermission, String> {

}
