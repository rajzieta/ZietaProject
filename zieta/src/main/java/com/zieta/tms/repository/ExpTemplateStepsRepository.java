package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.ExpTemplateSteps;
import com.zieta.tms.model.ProcessSteps;

@Repository
public interface ExpTemplateStepsRepository extends JpaRepository<ExpTemplateSteps, Long> {

	public List<ExpTemplateSteps> findByTemplateIdOrderByStepId(long expTemplateId);
	public List<ExpTemplateSteps> findByTemplateIdAndIsDelete(long expTemplateId, Short notDeleted);
	
	//public List<ExpTemplateSteps> findByProjectId(long projectId);
	
	//public Page<ExpTemplateSteps> findByClientIdAndProjectIdOrderByStepId(
			//long clientId, long projectId, Pageable pageable);

}
