package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zieta.tms.dto.SkillsetMasterDTO;
import com.zieta.tms.model.SkillsetMaster;
import com.zieta.tms.repository.ClientInfoRepository;
import com.zieta.tms.repository.SkillsetMasterRepository;
import com.zieta.tms.service.SkillsetMasterService;


import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class SkillsetMasterServiceImpl implements SkillsetMasterService {

	@Autowired
	SkillsetMasterRepository skillsetMasterRepository;
	
	@Autowired
	ClientInfoRepository clientInfoRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	
	@Override
	public List<SkillsetMasterDTO> getAllSkillset() {
		List<SkillsetMaster> skillMasters= skillsetMasterRepository.findAll();
		List<SkillsetMasterDTO> skillMasterDTOs = new ArrayList<SkillsetMasterDTO>();
		SkillsetMasterDTO skillMasterDTO = null;
		for (SkillsetMaster skillMaster : skillMasters) {
			skillMasterDTO = modelMapper.map(skillMaster,SkillsetMasterDTO.class);
			//skillMasterDTO.setClientCode(clientInfoRepository.findById(skillMaster.getClientId()).get().getClientCode());
			//skillMasterDTO.setClientDescription(clientInfoRepository.findById(skillMaster.getClientId()).get().getClientName());
			//staMasterDTO.setClientStatus(clientInfoRepository.findById(skillMaster.getClientId()).get().getClientStatus());

			skillMasterDTOs.add(skillMasterDTO);
		}
		return skillMasterDTOs;
	}
	
	
	 @Override 
	  public void addSkillmaster(SkillsetMaster skillsetmaster) {
		
	  skillsetMasterRepository.save(skillsetmaster); 
	  
	  }
	 
	 
	 public void deleteSkillsetById(Long id) throws Exception {
			
			Optional<SkillsetMaster> skillmaster = skillsetMasterRepository.findById(id);
			if (skillmaster.isPresent()) {
				skillsetMasterRepository.deleteById(id);

			}else {
				log.info("No Skill found with the provided ID{} in the DB",id);
				throw new Exception("No Skill found with the provided ID in the DB :"+id);
			}
			
			
		}
	 
}
