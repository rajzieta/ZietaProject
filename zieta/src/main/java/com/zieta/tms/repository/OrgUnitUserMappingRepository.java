package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.SkillsetMaster;
import com.zieta.tms.model.OrgUnitUserMapping;

@Repository
public interface OrgUnitUserMappingRepository extends JpaRepository<OrgUnitUserMapping, Long> {

	List<OrgUnitUserMapping> findByClientId(Long clientId);

	//List<Long> findByClientIdAndOrgUnitId(Long clientId, Long orgUnitId);
	
	//OrgUnitUserMapping findAllById(Long clientId);
	
	@Query( "select o.userId from OrgUnitUserMapping o where o.clientId= :clientId AND o.orgUnitId= :orgUnitId")
	  List<Long> findByClientIdANDOrgUnitId(@Param("clientId") Long clientId,
		  @Param("orgUnitId") Long orgUnitId);
//
//	

	
}
