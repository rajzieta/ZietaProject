package com.zieta.tms.service;

import java.util.List;

import javax.validation.Valid;

import com.zieta.tms.dto.ProcessConfigDTO;
import com.zieta.tms.dto.ProcessMasterDTO;
import com.zieta.tms.dto.ProcessStepsDTO;
import com.zieta.tms.model.ProcessMaster;
import com.zieta.tms.model.ProcessSteps;
import com.zieta.tms.model.ProjectInfo;
import com.zieta.tms.model.TaskInfo;

public interface ProcessService {

	public List<ProcessStepsDTO> getAllProcessSteps(Integer pageNo, Integer pageSize) throws Exception;
	
	public List<ProcessStepsDTO> getProcessStepsByClientIdByProjectId(long clientId, long projectId,
			Integer pageNo, Integer pageSize) throws Exception;
	

	public void addProcessSteps(@Valid ProcessSteps processstep);

	public void editProcessStepsById(@Valid ProcessStepsDTO processstepsDto) throws Exception;

	public void deleteProcessStepsById(Long id) throws Exception;

	public List<ProcessMasterDTO> getAllProcess();

	public void addProcess(@Valid ProcessMaster processmaster);

	public void editProcessById(@Valid ProcessMasterDTO processmastersDto) throws Exception;

	public void deleteProcessById(Long id) throws Exception;

	public List<ProcessConfigDTO> getAllProcessConfig();
	
	public List<ProcessSteps> createProcessSteps(TaskInfo taskInfo, ProjectInfo projectInfo);

	
	
}
