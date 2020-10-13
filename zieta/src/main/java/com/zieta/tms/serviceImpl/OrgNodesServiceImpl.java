package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zieta.tms.dto.OrgInfoDTO;
import com.zieta.tms.dto.OrgUnitTypeMasterDTO;
import com.zieta.tms.model.OrgInfo;
import com.zieta.tms.model.OrgUnitTypeMaster;
import com.zieta.tms.model.TaskInfo;
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.repository.ClientInfoRepository;
import com.zieta.tms.repository.OrgInfoRepository;
import com.zieta.tms.repository.OrgUnitTypeRepository;
import com.zieta.tms.repository.UserInfoRepository;
import com.zieta.tms.response.OrgNodesByClientResponse;
import com.zieta.tms.response.TasksByClientProjectResponse;
import com.zieta.tms.service.OrgNodesService;
import com.zieta.tms.util.TSMUtil;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@Slf4j
public class OrgNodesServiceImpl implements OrgNodesService {

	@Autowired
	OrgInfoRepository orgInfoRepository;
	
	@Autowired
	OrgUnitTypeRepository orgunitTypeRepository;
	
	@Autowired
	ClientInfoRepository clientInfoRepository;
	
	@Autowired
	UserInfoRepository userInfoRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	public List<OrgNodesByClientResponse> getOrgNodesByClient(Long clientId) {
		short notDeleted = 0;
		List<OrgInfo> orgnodesByClientList = orgInfoRepository.findByClientIdAndIsDelete(clientId, notDeleted);
		List<OrgNodesByClientResponse> orgnodesByClientResponseList = new ArrayList<>();
		OrgNodesByClientResponse orgnodesByClientResponse = null;
		for (OrgInfo orgnodesByClient : orgnodesByClientList) {
			orgnodesByClientResponse = modelMapper.map(orgnodesByClient, 
					OrgNodesByClientResponse.class);
			orgnodesByClientResponse.setOrgUnitTypeDescription(orgunitTypeRepository.findById(orgnodesByClient.getOrgType()).get().getTypeName());
			orgnodesByClientResponse.setClientCode(clientInfoRepository.findById(orgnodesByClient.getClientId()).get().getClientCode());
			orgnodesByClientResponse.setClientDescription(clientInfoRepository.findById(orgnodesByClient.getClientId()).get().getClientName());
			
			orgnodesByClientResponse.setOrgManagerName(StringUtils.EMPTY);
			if(null != orgnodesByClient.getOrgManager()) {
				Optional <UserInfo> userInfo = userInfoRepository.findById(orgnodesByClient.getOrgManager());
				if(userInfo.isPresent()) {
					String userName = TSMUtil.getFullName(userInfo.get());
					orgnodesByClientResponse.setOrgManagerName(userName);
				}
			}
			
			orgnodesByClientResponseList.add(orgnodesByClientResponse);
		}
		return orgnodesByClientResponseList;
	}
	
	
	@Override
	public List<OrgInfoDTO> getAllOrgNodes() {
		short notDeleted=0;
		List<OrgInfo> orginfos= orgInfoRepository.findByIsDelete(notDeleted);
		List<OrgInfoDTO> orginfoDTOs = new ArrayList<OrgInfoDTO>();
		OrgInfoDTO orginfoDTO = null;
		for (OrgInfo orgInfo : orginfos) {
			//modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			orginfoDTO = modelMapper.map(orgInfo, OrgInfoDTO.class);
			orginfoDTO.setOrgUnitTypeDescription(orgunitTypeRepository.findById(orgInfo.getOrgType()).get().getTypeName());
			orginfoDTO.setClientCode(clientInfoRepository.findById(orgInfo.getClientId()).get().getClientCode());
			orginfoDTO.setClientDescription(clientInfoRepository.findById(orgInfo.getClientId()).get().getClientName());
			orginfoDTO.setClientStatus(clientInfoRepository.findById(orgInfo.getClientId()).get().getClientStatus());

			orginfoDTO.setOrgManagerName(StringUtils.EMPTY);
			if(null != orgInfo.getOrgManager()) {
				Optional <UserInfo> userInfo = userInfoRepository.findById(orgInfo.getOrgManager());
				if(userInfo.isPresent()) {
					String userName = TSMUtil.getFullName(userInfo.get());
					orginfoDTO.setOrgManagerName(userName);
				}
			}
			
			orginfoDTOs.add(orginfoDTO);
		}
		//List<OrgInfoDTO> treeList = TSMUtil.createTreeStructureHeirarchy(orginfoDTOs);
	//	return treeList;
		return orginfoDTOs;
	}
	
	
	
	@Override
	public List<OrgInfoDTO> getAllOrgNodesAsHeirarchy() {
		short notDeleted=0;
		List<OrgInfo> orginfos= orgInfoRepository.findByIsDelete(notDeleted);
		List<OrgInfoDTO> orginfoDTOs = new ArrayList<OrgInfoDTO>();
		OrgInfoDTO orginfoDTO = null;
		for (OrgInfo orgInfo : orginfos) {
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			orginfoDTO = modelMapper.map(orgInfo, OrgInfoDTO.class);
			orginfoDTO.setOrgUnitTypeDescription(orgunitTypeRepository.findById(orgInfo.getOrgType()).get().getTypeName());
			orginfoDTO.setClientCode(clientInfoRepository.findById(orgInfo.getClientId()).get().getClientCode());
			orginfoDTO.setClientDescription(clientInfoRepository.findById(orgInfo.getClientId()).get().getClientName());
			orginfoDTO.setClientStatus(clientInfoRepository.findById(orgInfo.getClientId()).get().getClientStatus());

			orginfoDTO.setOrgManagerName(StringUtils.EMPTY);
			if(null != orgInfo.getOrgManager()) {
				Optional <UserInfo> userInfo = userInfoRepository.findById(orgInfo.getOrgManager());
				if(userInfo.isPresent()) {
					String userName = TSMUtil.getFullName(userInfo.get());
					orginfoDTO.setOrgManagerName(userName);
				}
			}
			
			orginfoDTOs.add(orginfoDTO);
		}
		List<OrgInfoDTO> treeList = TSMUtil.createTreeStructureHeirarchy(orginfoDTOs);
		return treeList;
	//	return orginfoDTOs;
	}
	
	
	@Override
	public void addOrgInfo(OrgInfo orginfo)
	{
		orgInfoRepository.save(orginfo);
	}
	
	
	@Override
	public void editOrgInfo(OrgInfoDTO orginfodto) throws Exception {
		
		Optional<OrgInfo> orginfoEntity = orgInfoRepository.findById(orginfodto.getOrgUnitId());
		if(orginfoEntity.isPresent()) {
			OrgInfo orginfo = modelMapper.map(orginfodto, OrgInfo.class);
			orgInfoRepository.save(orginfo);
			
		}else {
			throw new Exception("OrgInfo not found with the provided ID : "+orginfodto.getOrgUnitId());
		}
		
		
	}
	
	
	public void deleteOrgInfoById(Long id, String modifiedBy) throws Exception {
		
		Optional<OrgInfo> orginfo = orgInfoRepository.findById(id);
		if (orginfo.isPresent()) {
			OrgInfo orginfoEntity = orginfo.get();
			short delete = 1;
			orginfoEntity.setIsDelete(delete);
			orginfoEntity.setModifiedBy(modifiedBy);
			orgInfoRepository.save(orginfoEntity);

		}else {
			log.info("No OrgInfo found with the provided ID{} in the DB",id);
			throw new Exception("No OrgInfo found with the provided ID in the DB :"+id);
		}
		
		
	}
	
	@Override
	public List<OrgNodesByClientResponse> findByClientIdAsHierarchy(Long clientId) {
		short notDeleted = 0;
		List<OrgInfo> orgnodesByClientList = orgInfoRepository.findByClientIdAndIsDelete(clientId, notDeleted);
		List<OrgNodesByClientResponse> orgnodesByClientResponseList = new ArrayList<>();
			constructOrgInfoData(orgnodesByClientList, orgnodesByClientResponseList);
		
		List<OrgNodesByClientResponse> treeList = TSMUtil.createTreeStructure(orgnodesByClientResponseList);
		return treeList;
	}
	
	
	
	private void constructOrgInfoData(List<OrgInfo> orgnodesByClientList,
			List<OrgNodesByClientResponse> orgnodesByClientResponseList) {
		for (OrgInfo orgnodesByClient : orgnodesByClientList) {
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			OrgNodesByClientResponse orgnodesByClientResponse = modelMapper.map(orgnodesByClient, OrgNodesByClientResponse.class);
	
			orgnodesByClientResponse.setOrgUnitTypeDescription(orgunitTypeRepository.findById(orgnodesByClient.getOrgType()).get().getTypeName());
			orgnodesByClientResponse.setClientCode(clientInfoRepository.findById(orgnodesByClient.getClientId()).get().getClientCode());
			orgnodesByClientResponse.setClientDescription(clientInfoRepository.findById(orgnodesByClient.getClientId()).get().getClientName());
			
			orgnodesByClientResponseList.add(orgnodesByClientResponse);
		}
	}
	
	
	public List<OrgUnitTypeMasterDTO> getAllOrgUnitTypeMaster() {
		
		short notDeleted=0;
		List<OrgUnitTypeMaster> orgunitMasters= orgunitTypeRepository.findByIsDelete(notDeleted);
		List<OrgUnitTypeMasterDTO> orgUnitMastersDTOs = new ArrayList<OrgUnitTypeMasterDTO>();
		OrgUnitTypeMasterDTO orgUnitTypeMasterDTO = null;
		for (OrgUnitTypeMaster orgs : orgunitMasters) {
			orgUnitTypeMasterDTO = modelMapper.map(orgs, OrgUnitTypeMasterDTO.class);
			orgUnitMastersDTOs.add(orgUnitTypeMasterDTO);
		}
		return orgUnitMastersDTOs;
	}
	
}
