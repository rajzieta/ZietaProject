package com.zietaproj.zieta.service;

import java.util.List;

import javax.validation.Valid;

import com.zietaproj.zieta.dto.ProjectMasterDTO;
import com.zietaproj.zieta.model.ProjectMaster;

public interface ProjectMasterService {

	public List<ProjectMasterDTO> getAllProjects();

	public void addProjectmaster(@Valid ProjectMaster projectmaster);
}
