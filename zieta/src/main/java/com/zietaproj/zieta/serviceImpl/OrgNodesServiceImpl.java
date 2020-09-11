package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zietaproj.zieta.dto.OrgInfoDTO;
import com.zietaproj.zieta.model.OrgInfo;
import com.zietaproj.zieta.model.OrgUnitTypeMaster;
import com.zietaproj.zieta.repository.ClientInfoRepository;
import com.zietaproj.zieta.repository.OrgInfoRepository;
import com.zietaproj.zieta.repository.OrgUnitTypeRepository;
import com.zietaproj.zieta.response.OrgNodesByClientResponse;
import com.zietaproj.zieta.service.OrgNodesService;

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
			orginfoDTO = modelMapper.map(orgInfo, OrgInfoDTO.class);
			orginfoDTO.setOrgUnitTypeDescription(orgunitTypeRepository.findById(orgInfo.getOrgType()).get().getTypeName());
			orginfoDTO.setClientCode(clientInfoRepository.findById(orgInfo.getClientId()).get().getClientCode());
			orginfoDTO.setClientDescription(clientInfoRepository.findById(orgInfo.getClientId()).get().getClientName());
			
			orginfoDTOs.add(orginfoDTO);
		}
		return orginfoDTOs;
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
	
}
