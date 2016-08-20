package com.giong.api.repository.idschema;

import com.giong.api.persistence.domain.IdScheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IdSchemeRepository extends JpaRepository<IdScheme, Integer> {
	@Query("SELECT i FROM IdScheme i WHERE i.schemeName = :schemeName")
	List<IdScheme> getSchemeByName(@Param("schemeName") String schemeName);
}
