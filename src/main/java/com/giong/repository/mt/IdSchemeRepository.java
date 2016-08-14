package com.giong.repository.mt;

import com.giong.persistence.mt.MtIdScheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IdSchemeRepository extends JpaRepository<MtIdScheme, Integer> {

	@Query("SELECT e FROM MtIdScheme e WHERE e.schemeName = :schemeName")
	List<MtIdScheme> getSchemeByName(@Param("schemeName") String schemeName);

}
