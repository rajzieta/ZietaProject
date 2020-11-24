package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zieta.tms.dto.SkillsetMasterDTO;
import com.zieta.tms.dto.OrgUnitUserMappingDTO;
import com.zieta.tms.model.SkillsetMaster;
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.model.AccessTypeScreenMapping;
import com.zieta.tms.model.OrgInfo;
import com.zieta.tms.model.OrgUnitUserMapping;
import com.zieta.tms.model.ScreensMaster;
import com.zieta.tms.repository.ClientInfoRepository;
import com.zieta.tms.repository.UserInfoRepository;
import com.zieta.tms.request.OrgUnitUsersRequest;
import com.zieta.tms.response.OrgUnitUsersResponse;
import com.zieta.tms.response.UserDetailsResponse;
import com.zieta.tms.repository.OrgInfoRepository;
import com.zieta.tms.repository.SkillsetMasterRepository;
import com.zieta.tms.repository.SkillsetUserMappingRepository;
import com.zieta.tms.repository.OrgUnitUserMappingRepository;
import com.zieta.tms.service.SkillsetMasterService;
import com.zieta.tms.service.UserInfoService;
import com.zieta.tms.util.TSMUtil;
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
	OrgInfoRepository orgInfoRepository;
	
	@Autowired
	UserInfoRepository userInfoRepository;
	
	@Autowired
	UserInfoService userInfoService;
	
	@Autowired
	ModelMapper modelMapper;
	
	
	@Override
	public List<OrgUnitUserMappingDTO> getAllTeamMaster() {
		List<OrgUnitUserMapping> teamMasters= orgUUMRepository.findAll();
		List<OrgUnitUserMappingDTO> teamMasterDTOs = new ArrayList<OrgUnitUserMappingDTO>();
		OrgUnitUserMappingDTO teamMasterDTO = null;
		for (OrgUnitUserMapping teamMaster : teamMasters) {
			teamMasterDTO = modelMapper.map(teamMaster,OrgUnitUserMappingDTO.class);
			teamMasterDTO.setClientCode(clientInfoRepository.findById(teamMaster.getClientId()).get().getClientCode());
			teamMasterDTO.setClientDescription(clientInfoRepository.findById(teamMaster.getClientId()).get().getClientName());
			
			
//			teamMasterDTO.setOrgNodeCode(StringUtils.EMPTY);
//			if (null != teamMaster.getOrgUnitId()) {
//			Optional<OrgInfo>  orgInfo = orgInfoRepository.findById(teamMaster.getOrgUnitId());
//			if (orgInfo.isPresent()) {
//				teamMasterDTO.setOrgNodeCode(orgInfo.get().getOrgNodeCode());
//
//			}
//		}
			
			teamMasterDTO.setOrgNodeName(StringUtils.EMPTY);
			if (null != teamMaster.getOrgUnitId()) {
			Optional<OrgInfo> orgInfo  = orgInfoRepository.findById(teamMaster.getOrgUnitId());
			if (orgInfo.isPresent()) {
				teamMasterDTO.setOrgNodeName(orgInfo.get().getOrgNodeName());

			}
		}
			
			
			teamMasterDTO.setUserName(StringUtils.EMPTY);
			if(null != teamMaster.getUserId()) {
				Optional <UserInfo> userInfo = userInfoRepository.findById(teamMaster.getUserId());
				if(userInfo.isPresent()) {
					String userName = TSMUtil.getFullName(userInfo.get());
					teamMasterDTO.setUserName(userName);
				}
			}
			
			
			 List<Long> accessControlConfigList = orgUUMRepository.findByClientIdANDOrgUnitId(teamMasterDTO.getClientId(), teamMasterDTO.getOrgUnitId());
				
				List<UserInfo> usersListByClientId = userInfoService.getUsersByIds(accessControlConfigList);
				teamMasterDTO.setUsersByClient(usersListByClientId);
			
			
			
			//staMasterDTO.setClientStatus(clientInfoRepository.findById(skillMaster.getClientId()).get().getClientStatus());

			teamMasterDTOs.add(teamMasterDTO);
		}
		return teamMasterDTOs;
	}
	
	
	 @Override 
	  public void addTeamMaster(OrgUnitUsersRequest usersRequest) {
		
		 List<OrgUnitUserMapping> accessTypeScreenMappingList = new ArrayList<OrgUnitUserMapping>();
		 OrgUnitUserMapping accessTypeScreenMapping = null;
			List<Long> userIds = usersRequest.getUserIds();
			for (Long userId : userIds) {
				accessTypeScreenMapping = new OrgUnitUserMapping();
				accessTypeScreenMapping.setId(usersRequest.getId());
				accessTypeScreenMapping.setClientId(usersRequest.getClientId());
				accessTypeScreenMapping.setOrgUnitId(usersRequest.getOrgUnitId());
				accessTypeScreenMapping.setUserId(userId);
				accessTypeScreenMappingList.add(accessTypeScreenMapping);
			}
			orgUUMRepository.saveAll(accessTypeScreenMappingList);
		 
		 
		
	  
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
			OrgUnitUserMappingDTO teamByClientList = null;
			for(OrgUnitUserMapping teammaster: teamList) {
				
				teamByClientList = modelMapper.map(teammaster,OrgUnitUserMappingDTO.class);
				teamByClientList.setClientCode(clientInfoRepository.findById(teammaster.getClientId()).get().getClientCode());
				teamByClientList.setClientDescription(clientInfoRepository.findById(teammaster.getClientId()).get().getClientName());
				
				
//				teamByClientList.setOrgNodeCode(StringUtils.EMPTY);
//				if (null != teammaster.getOrgUnitId()) {
//				Optional<OrgInfo>  orgInfo = orgInfoRepository.findById(teammaster.getOrgUnitId());
//				if (orgInfo.isPresent()) {
//					teamByClientList.setOrgNodeCode(orgInfo.get().getOrgNodeCode());
//
//				}
//			}
				
				teamByClientList.setOrgNodeName(StringUtils.EMPTY);
				if (null != teammaster.getOrgUnitId()) {
				Optional<OrgInfo> orgInfo  = orgInfoRepository.findById(teammaster.getOrgUnitId());
				if (orgInfo.isPresent()) {
					teamByClientList.setOrgNodeName(orgInfo.get().getOrgNodeName());

				}
			}
				
				
				teamByClientList.setUserName(StringUtils.EMPTY);
				if(null != teammaster.getUserId()) {
					Optional <UserInfo> userInfo = userInfoRepository.findById(teammaster.getUserId());
					if(userInfo.isPresent()) {
						String userName = TSMUtil.getFullName(userInfo.get());
						teamByClientList.setUserName(userName);
					}
				}
				
				 List<Long> accessControlConfigList = orgUUMRepository.findByClientIdANDOrgUnitId(teamByClientList.getClientId(), teamByClientList.getOrgUnitId());
					
					List<UserInfo> usersListByClientId = userInfoService.getUsersByIds(accessControlConfigList);
					teamByClientList.setUsersByClient(usersListByClientId);
				
				
				teamsByClientList.add(teamByClientList);
			}
			
			return teamsByClientList;

}
	 
	 
	 
}
