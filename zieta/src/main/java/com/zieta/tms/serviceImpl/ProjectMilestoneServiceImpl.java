package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.zieta.tms.dto.ProjectMilestoneDTO;
import com.zieta.tms.model.ProjectMilestone;
import com.zieta.tms.repository.ProjectMilestoneRepository;
import com.zieta.tms.service.ProjectMilestoneService;

@Service("ProjectMilestoneServiceImpl")
public class ProjectMilestoneServiceImpl implements ProjectMilestoneService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	@Qualifier("ProjectMilestoneRepository")
	private ProjectMilestoneRepository projectMilestoneRepository;

	@Override
	public void addProjectMilestone(ProjectMilestone projectMilestone) {
		projectMilestoneRepository.save(projectMilestone);
	}

	@Override
	public List<ProjectMilestoneDTO> getProjectMilestoneDetailsByClientId(Long clientId) {
		short notDeleted = 0;
		List<ProjectMilestone> listProjectMilestone = projectMilestoneRepository.findByClientIdAndIsDelete(clientId,
				notDeleted);
		List<ProjectMilestoneDTO> listProjectMilestoneDto = new ArrayList<>();
		listProjectMilestone.forEach(projectMilestone -> {
			ProjectMilestoneDTO prjectMilestoneDTO = modelMapper.map(projectMilestone, ProjectMilestoneDTO.class);
			listProjectMilestoneDto.add(prjectMilestoneDTO);
		});

		return listProjectMilestoneDto;
	}

	@Override
	public void updateProjectMilestone(ProjectMilestone projectMilestone) {
		projectMilestoneRepository.save(projectMilestone);
	}

	@Override
	public void inActiveByProjectMilestone(Long id, String modifiedBy) {
		projectMilestoneRepository.inactiveByProjectMilestone(id, modifiedBy);
	}

}
