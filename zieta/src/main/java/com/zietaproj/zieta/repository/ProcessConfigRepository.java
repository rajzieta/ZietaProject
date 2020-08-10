package com.zietaproj.zieta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.ProcessConfig;

@Repository
public interface ProcessConfigRepository extends JpaRepository<ProcessConfig, Long> {
	
	
	List<ProcessConfig> findByTemplateId(Long templateId);

}