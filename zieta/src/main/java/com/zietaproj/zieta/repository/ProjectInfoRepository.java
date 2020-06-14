package com.zietaproj.zieta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zietaproj.zieta.model.ProjectInfo;

public interface ProjectInfoRepository extends JpaRepository<ProjectInfo, Long> {
	
	List<ProjectInfo> findByClientId(long clientId);

	

}
