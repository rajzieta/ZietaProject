package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.CustOrgNodeMapping;

@Repository
public interface CustOrgNodeMappingRepository extends JpaRepository<CustOrgNodeMapping, Long>{
	
	
	//List<CustOrgNodeMapping> findByClientIdAndOrgNode(long clientId, long orgNode);

	List<CustOrgNodeMapping> findByClientIdAndOrgNodeAndIsDelete(long clientId, long orgNode, short notDeleted);

}
