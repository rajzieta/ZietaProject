package com.zietaproj.zieta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.ProjectMaster;


@Repository
public interface ProjectMasterRepository extends JpaRepository<ProjectMaster, Long>{

	
	
}
