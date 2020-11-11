package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zieta.tms.dto.SkillsetMasterDTO;
import com.zieta.tms.dto.TeamMasterDTO;
import com.zieta.tms.model.SkillsetMaster;
import com.zieta.tms.model.TeamMaster;
import com.zieta.tms.repository.ClientInfoRepository;
import com.zieta.tms.repository.SkillsetMasterRepository;
import com.zieta.tms.repository.SkillsetUserMappingRepository;
import com.zieta.tms.repository.TeamMasterRepository;
import com.zieta.tms.service.SkillsetMasterService;
import com.zieta.tms.service.TeamMasterService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class TeamMasterServiceImpl implements TeamMasterService {

	@Autowired
	TeamMasterRepository teamMasterRepository;
	
	
	@Autowired
	ClientInfoRepository clientInfoRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	
	@Override
	public List<TeamMasterDTO> getAllTeamMaster() {
		List<TeamMaster> teamMasters= teamMasterRepository.findAll();
		List<TeamMasterDTO> teamMasterDTOs = new ArrayList<TeamMasterDTO>();
		TeamMasterDTO teamMasterDTO = null;
		for (TeamMaster teamMaster : teamMasters) {
			teamMasterDTO = modelMapper.map(teamMaster,TeamMasterDTO.class);
			teamMasterDTO.setClientCode(clientInfoRepository.findById(teamMaster.getClientId()).get().getClientCode());
			teamMasterDTO.setClientDescription(clientInfoRepository.findById(teamMaster.getClientId()).get().getClientName());
			//staMasterDTO.setClientStatus(clientInfoRepository.findById(skillMaster.getClientId()).get().getClientStatus());

			teamMasterDTOs.add(teamMasterDTO);
		}
		return teamMasterDTOs;
	}
	
	
	 @Override 
	  public void addTeamMaster(TeamMaster teammaster) {
		
	  teamMasterRepository.save(teammaster); 
	  
	  }
	 
	 
	 public void deleteTeamMasterById(Long id) throws Exception {
			
			Optional<TeamMaster> teamMaster = teamMasterRepository.findById(id);
			if (teamMaster.isPresent()) {
				teamMasterRepository.deleteById(id);

			}else {
				log.info("No Team found with the provided ID{} in the DB",id);
				throw new Exception("No Team found with the provided ID in the DB :"+id);
			}
			
			
		}
	 
	 
	 @Override
		public void editTeamMaster(@Valid TeamMasterDTO teamMasterDTO) throws Exception {
		
			Optional<TeamMaster> teamEntity = teamMasterRepository.findById(teamMasterDTO.getId());
			if(teamEntity.isPresent()) {
				TeamMaster teaminfo = modelMapper.map(teamMasterDTO, TeamMaster.class);
				teamMasterRepository.save(teaminfo);
				
			}else {
				throw new Exception("Details not found with the provided ID : "+teamMasterDTO.getId());
			}
			
			
		}
	 
	 
	 @Override
		public List<TeamMasterDTO> findByClientId(Long clientId) {
		 short notDeleted = 0;
			List<TeamMaster> teamList = teamMasterRepository.findByClientIdAndIsDelete(clientId, notDeleted);
			List<TeamMasterDTO> teamsByClientList = new ArrayList<>();
			for(TeamMaster teammaster: teamList) {
				TeamMasterDTO teamByClientList = null;
				teamByClientList = modelMapper.map(teammaster,TeamMasterDTO.class);
			//	teamByClientList.setClientCode(clientInfoRepository.findById(teammaster.getClientId()).get().getClientCode());
			//	teamByClientList.setClientDescription(clientInfoRepository.findById(teammaster.getClientId()).get().getClientName());

				
				teamsByClientList.add(teamByClientList);
			}
			
			return teamsByClientList;

}
}
