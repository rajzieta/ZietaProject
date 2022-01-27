package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.ProcessSteps;

@Repository
public interface ProcessStepsRepository extends JpaRepository<ProcessSteps, Long> {

	public List<ProcessSteps> findByClientIdAndProjectIdAndProjectTaskIdAndTemplateIdOrderByStepId(
			long clientId, long projectId, long taskId,long templateId);
	
	public List<ProcessSteps> findByProjectId(long projectId);
	
	public Page<ProcessSteps> findByClientIdAndProjectIdOrderByStepId(
			long clientId, long projectId, Pageable pageable);
	
	@Query(value="select * from wf_process_steps where client_id=?1 and template_id=?2 and project_id=?3 and project_task_id=?4 and step_id=?5", nativeQuery=true)
	public ProcessSteps getProcessStepByClientIdAndTemplateIdAndProjectIdAndProjectTaskIdAndStepId(
			long clientId, long templateId,long projectId, long projectTaskId,long stepId);

}
