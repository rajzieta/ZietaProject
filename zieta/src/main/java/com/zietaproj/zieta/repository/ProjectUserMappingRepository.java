package com.zietaproj.zieta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.ProjectUserMapping;

@Repository
public interface ProjectUserMappingRepository extends JpaRepository<ProjectUserMapping, Long>{
	
	List<ProjectUserMapping> findByUserId(long userId);
}
