package com.giong.repository.mt;

import com.giong.persistence.mt.MtRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<MtRole, String> {

}
