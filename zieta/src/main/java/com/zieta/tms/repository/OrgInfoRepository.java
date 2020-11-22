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



	
	

}
