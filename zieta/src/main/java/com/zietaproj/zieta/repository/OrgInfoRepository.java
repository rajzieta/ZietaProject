package com.zietaproj.zieta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zietaproj.zieta.model.OrgInfo;

public interface OrgInfoRepository extends JpaRepository<OrgInfo, Long> {

	List<OrgInfo> findByClientId(Long clientId);

	List<OrgInfo> findByClientIdAndIsDelete(Long clientId, short notDeleted);

	List<OrgInfo> findByIsDelete(short notDeleted);

	

}
