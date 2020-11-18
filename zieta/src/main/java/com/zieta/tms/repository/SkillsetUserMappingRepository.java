package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.SkillsetMaster;
import com.zieta.tms.model.SkillsetUserMapping;

@Repository
public interface SkillsetUserMappingRepository extends JpaRepository<SkillsetUserMapping, Long> {

	List<SkillsetUserMapping> findByClientIdAndUserId(Long clientId, Long userId);

	
	
}
