package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.ProcessSteps;

@Repository
public interface ProcessStepsRepository extends JpaRepository<ProcessSteps, Long> {

	public List<ProcessSteps> findByClientIdAndProjectIdAndProjectTaskIdOrderByStepId(
			long clientId, long projectId, long taskId);
	
	public List<ProcessSteps> findByProjectId(long projectId);
	
	public Page<ProcessSteps> findByClientIdAndProjectIdOrderByStepId(
			long clientId, long projectId, Pageable pageable);

}
