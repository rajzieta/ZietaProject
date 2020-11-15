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
import com.zieta.tms.dto.OrgUnitUserMappingDTO;
import com.zieta.tms.model.SkillsetMaster;
import com.zieta.tms.model.OrgUnitUserMapping;
import com.zieta.tms.repository.ClientInfoRepository;
import com.zieta.tms.repository.SkillsetMasterRepository;
import com.zieta.tms.repository.SkillsetUserMappingRepository;
import com.zieta.tms.repository.OrgUnitUserMappingRepository;
import com.zieta.tms.service.SkillsetMasterService;
import com.zieta.tms.service.OrgUnitUserMappingService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class OrgUnitUserMappingServiceImpl implements OrgUnitUserMappingService {

	@Autowired
	OrgUnitUserMappingRepository orgUUMRepository;
	
	
	@Autowired
	ClientInfoRepository clientInfoRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	
	@Override
	public List<OrgUnitUserMappingDTO> getAllTeamMaster() {
		List<OrgUnitUserMapping> teamMasters= orgUUMRepository.findAll();
		List<OrgUnitUserMappingDTO> teamMasterDTOs = new ArrayList<OrgUnitUserMappingDTO>();
		OrgUnitUserMappingDTO teamMasterDTO = null;
		for (OrgUnitUserMapping teamMaster : teamMasters) {
			teamMasterDTO = modelMapper.map(teamMaster,OrgUnitUserMappingDTO.class);
		//	teamMasterDTO.setClientCode(clientInfoRepository.findById(teamMaster.getClientId()).get().getClientCode());
		//	teamMasterDTO.setClientDescription(clientInfoRepository.findById(teamMaster.getClientId()).get().getClientName());
			//staMasterDTO.setClientStatus(clientInfoRepository.findById(skillMaster.getClientId()).get().getClientStatus());

			teamMasterDTOs.add(teamMasterDTO);
		}
		return teamMasterDTOs;
	}
	
	
	 @Override 
	  public void addTeamMaster(OrgUnitUserMapping teammaster) {
		
		 orgUUMRepository.save(teammaster); 
	  
	  }
	 
	 
	 public void deleteTeamMasterById(Long id) throws Exception {
			
			Optional<OrgUnitUserMapping> teamMaster = orgUUMRepository.findById(id);
			if (teamMaster.isPresent()) {
				orgUUMRepository.deleteById(id);

			}else {
				log.info("No Detail found with the provided ID{} in the DB",id);
				throw new Exception("No Detail found with the provided ID in the DB :"+id);
			}
			
			
		}
	 
	 
	 @Override
		public void editTeamMaster(@Valid OrgUnitUserMappingDTO teamMasterDTO) throws Exception {
		
			Optional<OrgUnitUserMapping> teamEntity = orgUUMRepository.findById(teamMasterDTO.getId());
			if(teamEntity.isPresent()) {
				OrgUnitUserMapping teaminfo = modelMapper.map(teamMasterDTO, OrgUnitUserMapping.class);
				orgUUMRepository.save(teaminfo);
				
			}else {
				throw new Exception("Details not found with the provided ID : "+teamMasterDTO.getId());
			}
			
			
		}
	 
	 
	 @Override
		public List<OrgUnitUserMappingDTO> findByClientId(Long clientId) {
			List<OrgUnitUserMapping> teamList = orgUUMRepository.findByClientId(clientId);
			List<OrgUnitUserMappingDTO> teamsByClientList = new ArrayList<>();
			for(OrgUnitUserMapping teammaster: teamList) {
				OrgUnitUserMappingDTO teamByClientList = null;
				teamByClientList = modelMapper.map(teammaster,OrgUnitUserMappingDTO.class);
			//	teamByClientList.setClientCode(clientInfoRepository.findById(teammaster.getClientId()).get().getClientCode());
			//	teamByClientList.setClientDescription(clientInfoRepository.findById(teammaster.getClientId()).get().getClientName());

				
				teamsByClientList.add(teamByClientList);
			}
			
			return teamsByClientList;

}
}
