package com.zietaproj.zieta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.ProcessSteps;

@Repository
public interface ProcessStepsRepository extends JpaRepository<ProcessSteps, Long> {

	
	
}
