package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.SkillsetCategoryMaster;
@Repository
public interface SkillsetCategoryMasterRepository extends JpaRepository<SkillsetCategoryMaster, Long> {

	public List<SkillsetCategoryMaster> findByClientId(Long clientId);

	
	
}
