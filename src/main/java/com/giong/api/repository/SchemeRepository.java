package com.giong.api.repository;

import com.giong.api.domain.IdScheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SchemeRepository extends JpaRepository<IdScheme, Integer> {
	@Query("SELECT i FROM IdScheme i WHERE i.schemeName = :schemeName")
	List<IdScheme> getSchemeByName(@Param("schemeName") String schemeName);
}
