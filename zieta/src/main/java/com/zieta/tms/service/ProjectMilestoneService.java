package com.zieta.tms.service;

import java.util.List;

import com.zieta.tms.dto.ProjectMilestoneDTO;
import com.zieta.tms.model.ProjectMilestone;

public interface ProjectMilestoneService {

	public void addProjectMilestone(ProjectMilestone projectMilestone);

	public List<ProjectMilestoneDTO> getProjectMilestoneDetailsByClientId(Long clientId);

	public void updateProjectMilestone(ProjectMilestone projectMilestone);

	public void inActiveByProjectMilestone(Long id,String modifiedBy);
}
