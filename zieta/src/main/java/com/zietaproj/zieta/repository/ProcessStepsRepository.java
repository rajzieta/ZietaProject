package com.zietaproj.zieta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.ProcessSteps;

@Repository
public interface ProcessStepsRepository extends JpaRepository<ProcessSteps, Long> {

	public List<ProcessSteps> findByClientIdAndProjectIdAndProjectTaskIdOrderByStepId(
			long clientId, long projectId, long taskId);
	
	public List<ProcessSteps> findByProjectId(long projectId);

}
