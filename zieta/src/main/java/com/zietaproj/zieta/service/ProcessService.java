package com.zietaproj.zieta.service;

import java.util.List;

import javax.validation.Valid;

import com.zietaproj.zieta.dto.ProcessConfigDTO;
import com.zietaproj.zieta.dto.ProcessMasterDTO;
import com.zietaproj.zieta.dto.ProcessStepsDTO;
import com.zietaproj.zieta.model.ProcessMaster;
import com.zietaproj.zieta.model.ProcessSteps;
import com.zietaproj.zieta.model.ProjectInfo;
import com.zietaproj.zieta.model.TaskInfo;

public interface ProcessService {

	public List<ProcessStepsDTO> getAllProcessSteps() throws Exception;

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
