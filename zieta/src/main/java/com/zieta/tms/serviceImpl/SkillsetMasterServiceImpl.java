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
import com.zieta.tms.dto.SkillsetUserMappingDTO;
import com.zieta.tms.model.ProjectInfo;
import com.zieta.tms.model.SkillsetMaster;
import com.zieta.tms.model.SkillsetUserMapping;
import com.zieta.tms.model.StatusMaster;
import com.zieta.tms.repository.ClientInfoRepository;
import com.zieta.tms.repository.SkillsetMasterRepository;
import com.zieta.tms.repository.SkillsetUserMappingRepository;
import com.zieta.tms.request.ProjectMasterEditRequest;
import com.zieta.tms.response.StatusByClienttypeResponse;
import com.zieta.tms.service.SkillsetMasterService;


import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class SkillsetMasterServiceImpl implements SkillsetMasterService {

	@Autowired
	SkillsetMasterRepository skillsetMasterRepository;
	
	@Autowired
	SkillsetUserMappingRepository skillsetUserMappingRepository;
	
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
			skillMasterDTO.setClientCode(clientInfoRepository.findById(skillMaster.getClientId()).get().getClientCode());
			skillMasterDTO.setClientDescription(clientInfoRepository.findById(skillMaster.getClientId()).get().getClientName());
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
	 
	 
	 @Override
		public void editskillmaster(@Valid SkillsetMasterDTO skillsetMasterDTO) throws Exception {
		
			Optional<SkillsetMaster> skillEntity = skillsetMasterRepository.findById(skillsetMasterDTO.getId());
			if(skillEntity.isPresent()) {
				SkillsetMaster skillinfo = modelMapper.map(skillsetMasterDTO, SkillsetMaster.class);
				skillsetMasterRepository.save(skillinfo);
				
			}else {
				throw new Exception("Details not found with the provided ID : "+skillsetMasterDTO.getId());
			}
			
			
		}
	 
	 
	 @Override
		public List<SkillsetMasterDTO> findByClientId(Long clientId) {
			List<SkillsetMaster> skillList = skillsetMasterRepository.findByClientId(clientId);
			List<SkillsetMasterDTO> skillsByClientList = new ArrayList<>();
			for(SkillsetMaster skillmaster: skillList) {
				SkillsetMasterDTO skillbyclientList = null;
				skillbyclientList = modelMapper.map(skillmaster,SkillsetMasterDTO.class);
				skillbyclientList.setClientCode(clientInfoRepository.findById(skillmaster.getClientId()).get().getClientCode());
				skillbyclientList.setClientDescription(clientInfoRepository.findById(skillmaster.getClientId()).get().getClientName());

				
				skillsByClientList.add(skillbyclientList);
			}
			
			return skillsByClientList;

}
	
	 ///Skillset UserMapping DTO
	 
	 @Override
		public List<SkillsetUserMappingDTO> getAllSkillsetUserMapping() {
			List<SkillsetUserMapping> skillUsers= skillsetUserMappingRepository.findAll();
			List<SkillsetUserMappingDTO> skillUsermapDTOs = new ArrayList<SkillsetUserMappingDTO>();
			SkillsetUserMappingDTO skillUsermapDTO = null;
			for (SkillsetUserMapping skillMaster : skillUsers) {
				skillUsermapDTO = modelMapper.map(skillMaster,SkillsetUserMappingDTO.class);
			//	skillUsermapDTO.setClientCode(clientInfoRepository.findById(skillMaster.getClientId()).get().getClientCode());
			//	skillUsermapDTO.setClientDescription(clientInfoRepository.findById(skillMaster.getClientId()).get().getClientName());
				//staMasterDTO.setClientStatus(clientInfoRepository.findById(skillMaster.getClientId()).get().getClientStatus());

				skillUsermapDTOs.add(skillUsermapDTO);
			}
			return skillUsermapDTOs;
		}
		
		
		 @Override 
		  public void addSkillsetUserMapping(SkillsetUserMapping skillsetUsermap) {
			
		  skillsetUserMappingRepository.save(skillsetUsermap); 
		  
		  }
		 
		 
		 public void deleteSkillUserMappingById(Long id) throws Exception {
				
				Optional<SkillsetUserMapping> skillmaster = skillsetUserMappingRepository.findById(id);
				if (skillmaster.isPresent()) {
					skillsetUserMappingRepository.deleteById(id);

				}else {
					log.info("No Skill found with the provided ID{} in the DB",id);
					throw new Exception("No Skill found with the provided ID in the DB :"+id);
				}
				
				
			}
		 
		 @Override
			public List<SkillsetUserMappingDTO> findByClientIdAndUserId(Long clientId, Long userId) {
				List<SkillsetUserMapping> skillList = skillsetUserMappingRepository.findByClientIdAndUserId(clientId, userId);
				List<SkillsetUserMappingDTO> skillsByClientList = new ArrayList<>();
				for(SkillsetUserMapping skillmaster: skillList) {
					SkillsetUserMappingDTO skillbyclientList = null;
					skillbyclientList = modelMapper.map(skillmaster,SkillsetUserMappingDTO.class);
					skillbyclientList.setClientCode(clientInfoRepository.findById(skillmaster.getClientId()).get().getClientCode());
					skillbyclientList.setClientDescription(clientInfoRepository.findById(skillmaster.getClientId()).get().getClientName());

					
					skillsByClientList.add(skillbyclientList);
				}
				
				return skillsByClientList;

	}
}
