package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.SkillsetMaster;
import com.zieta.tms.model.OrgUnitUserMapping;

@Repository
public interface OrgUnitUserMappingRepository extends JpaRepository<OrgUnitUserMapping, Long> {

	List<OrgUnitUserMapping> findByClientId(Long clientId);

	
}
