package com.zietaproj.zieta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.CustOrgNodeMapping;

@Repository
public interface CustOrgNodeMappingRepository extends JpaRepository<CustOrgNodeMapping, Long>{
	
	
	List<CustOrgNodeMapping> findByClientIdAndOrgNode(long clientId, long orgNode);

}
