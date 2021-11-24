package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zieta.tms.model.OrgInfo;

public interface OrgInfoRepository extends JpaRepository<OrgInfo, Long> {

	List<OrgInfo> findByClientId(Long clientId);

	List<OrgInfo> findByClientIdAndIsDelete(Long clientId, short notDeleted);

	List<OrgInfo> findByIsDelete(short notDeleted);
	
	@Query(value="select * from orgunit_info where ext_id=?1", nativeQuery=true)
	OrgInfo findByExtId(String extOrgNode);
	
	@Query(value="select * from orgunit_info where ext_id=?1 and client_id=?2", nativeQuery=true)
	OrgInfo findByExtIdAndClientId(String extOrgNode, Long clientId);



	
	

}
