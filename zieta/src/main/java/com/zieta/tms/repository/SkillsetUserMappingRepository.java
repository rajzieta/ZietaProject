package com.zieta.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.SkillsetMaster;
import com.zieta.tms.model.SkillsetUserMapping;

@Repository
public interface SkillsetUserMappingRepository extends JpaRepository<SkillsetUserMapping, Long> {

	
	
}
