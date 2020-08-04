package com.zietaproj.zieta.service;

import java.util.List;

import javax.validation.Valid;

import com.zietaproj.zieta.dto.ProcessStepsDTO;
import com.zietaproj.zieta.model.ProcessSteps;

public interface ProcessService {

	public List<ProcessStepsDTO> getAllProcessSteps();

	public void addProcessSteps(@Valid ProcessSteps processstep);

	public void editProcessStepsById(@Valid ProcessStepsDTO processstepsDto) throws Exception;

	public void deleteProcessStepsById(Long id) throws Exception;

	
	
}
