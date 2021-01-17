package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.zieta.tms.dto.LeaveInfoDTO;
import com.zieta.tms.dto.LeaveTypeMasterDTO;
import com.zieta.tms.model.CustInfo;
import com.zieta.tms.model.LeaveInfo;
import com.zieta.tms.model.LeaveTypeMaster;
import com.zieta.tms.model.OrgInfo;
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.repository.LeaveInfoRepository;
import com.zieta.tms.repository.LeaveMasterRepository;
import com.zieta.tms.response.CustomerInformationModel;
import com.zieta.tms.response.OrgNodesByClientResponse;
import com.zieta.tms.service.LeaveInfoService;
import com.zieta.tms.util.TSMUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LeaveInfoServiceImpl implements LeaveInfoService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LeaveInfoServiceImpl.class);
	
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	LeaveInfoRepository leaveInfoRepository;
	
	@Autowired
	LeaveMasterRepository leaveMasterRepository;
	
	@Override
	public void addLeaveInfo(LeaveInfo leaveInfo){
		
		try {
		leaveInfoRepository.save(leaveInfo);
		}
		catch(Exception ex) {
			log.error("Exception occured during the save Leave information",ex);
			}
		}
	
	@Override
public void editleaveInfoById(LeaveInfoDTO leaveinfoDTO) throws Exception {
		
		Optional<LeaveInfo> leaveinfoEntity = leaveInfoRepository.findById(leaveinfoDTO.getId());
		if(leaveinfoEntity.isPresent()) {
			LeaveInfo leaveinfo = modelMapper.map(leaveinfoDTO, LeaveInfo.class);
			leaveInfoRepository.save(leaveinfo);
			
		}else {
			throw new Exception("Leave Information not found with the provided ID : "+leaveinfoDTO.getId());
		}
		
		
	}
	
	
	@Override
	public List<LeaveInfoDTO> getAllLeaveInfo() {
		short notDeleted = 0;
		List<LeaveInfo> leaveInfos = leaveInfoRepository.findByIsDelete(notDeleted);
		List<LeaveInfoDTO>  leaveInfoList = new ArrayList<>();
		
		for (LeaveInfo leaveInfo : leaveInfos) {
			LeaveInfoDTO leaveInformationModel = modelMapper.map(
					leaveInfo, LeaveInfoDTO.class);
			
			leaveInfoList.add(leaveInformationModel);
			
		}
		return leaveInfoList;
	}

	/////////////////////


@Override
public void addLeaveTypeMaster(@Valid LeaveTypeMaster leavemaster) {
	
	try {
		leaveMasterRepository.save(leavemaster);
	}
	catch(Exception ex) {
		log.error("Exception occured during the save Leave information",ex);
		}
	}

@Override
public void editLeaveMasterById(@Valid LeaveTypeMasterDTO leavemasterDTO) throws Exception {
	
	Optional<LeaveTypeMaster> leavemasterEntity = leaveMasterRepository.findById(leavemasterDTO.getId());
	if(leavemasterEntity.isPresent()) {
		LeaveTypeMaster leavemaster = modelMapper.map(leavemasterDTO, LeaveTypeMaster.class);
		leaveMasterRepository.save(leavemaster);
		
	}else {
		throw new Exception("Leave Information not found with the provided ID : "+leavemasterDTO.getId());
	}
	
	
}

@Override
public List<LeaveTypeMasterDTO> getAllLeaveMaster() {
	short notDeleted = 0;
	List<LeaveTypeMaster> leaveInfoList = leaveMasterRepository.findByIsDelete(notDeleted);
	List<LeaveTypeMasterDTO>  leaveMasterInfoList = new ArrayList<>();
	
	for (LeaveTypeMaster leaveMasterInfo : leaveInfoList) {
		LeaveTypeMasterDTO leaveInformationModel = modelMapper.map(
				leaveMasterInfo, LeaveTypeMasterDTO.class);
		
		leaveMasterInfoList.add(leaveInformationModel);
		
	}
	return leaveMasterInfoList;
}
	

@Override
public List<LeaveTypeMasterDTO> getAllLeaveTypesByClient(Long clientId) {
	short notDeleted = 0;
	List<LeaveTypeMaster> orgnodesByClientList = leaveMasterRepository.findByClientIdAndIsDelete(clientId, notDeleted);
	List<LeaveTypeMasterDTO> orgnodesByClientResponseList = new ArrayList<>();
	LeaveTypeMasterDTO orgnodesByClientResponse = null;
	for (LeaveTypeMaster orgnodesByClient : orgnodesByClientList) {
		orgnodesByClientResponse = modelMapper.map(orgnodesByClient, 
				LeaveTypeMasterDTO.class);
		//orgnodesByClientResponse.setOrgUnitTypeDescription(orgunitTypeRepository.findById(orgnodesByClient.getOrgType()).get().getTypeName());
		//orgnodesByClientResponse.setClientCode(clientInfoRepository.findById(orgnodesByClient.getClientId()).get().getClientCode());
		//orgnodesByClientResponse.setClientDescription(clientInfoRepository.findById(orgnodesByClient.getClientId()).get().getClientName());
		
//		orgnodesByClientResponse.setOrgManagerName(StringUtils.EMPTY);
//		if(null != orgnodesByClient.getOrgManager()) {
//			Optional <UserInfo> userInfo = userInfoRepository.findById(orgnodesByClient.getOrgManager());
//			if(userInfo.isPresent()) {
//				String userName = TSMUtil.getFullName(userInfo.get());
//				orgnodesByClientResponse.setOrgManagerName(userName);
//			}
//		}
		
		orgnodesByClientResponseList.add(orgnodesByClientResponse);
	}
	return orgnodesByClientResponseList;
}



@Override
public List<LeaveInfoDTO> getAllLeavesByClient(Long clientId) {
	short notDeleted = 0;
	List<LeaveInfo> orgnodesByClientList = leaveInfoRepository.findByClientIdAndIsDelete(clientId, notDeleted);
	List<LeaveInfoDTO> orgnodesByClientResponseList = new ArrayList<>();
	LeaveInfoDTO orgnodesByClientResponse = null;
	for (LeaveInfo orgnodesByClient : orgnodesByClientList) {
		orgnodesByClientResponse = modelMapper.map(orgnodesByClient, 
				LeaveInfoDTO.class);
		
		orgnodesByClientResponseList.add(orgnodesByClientResponse);
	}
	return orgnodesByClientResponseList;
}

@Override
public List<LeaveInfoDTO> getAllLeavesByClientUser(Long clientId, Long userId) {
	short notDeleted = 0;
	List<LeaveInfo> orgnodesByClientList = leaveInfoRepository.findByClientIdAndUserIdAndIsDelete(clientId, userId, notDeleted);
	List<LeaveInfoDTO> orgnodesByClientResponseList = new ArrayList<>();
	LeaveInfoDTO orgnodesByClientResponse = null;
	for (LeaveInfo orgnodesByClient : orgnodesByClientList) {
		orgnodesByClientResponse = modelMapper.map(orgnodesByClient, 
				LeaveInfoDTO.class);
		orgnodesByClientResponse.setLeaveTypeDescription(leaveMasterRepository.findById(orgnodesByClient.getLeaveType()).get().getLeaveType());
		
		
		orgnodesByClientResponseList.add(orgnodesByClientResponse);
	}
	return orgnodesByClientResponseList;
}



public void deleteLeaveInfoById(Long id, String modifiedBy) throws Exception {
	
	Optional<LeaveInfo> leaveinfo = leaveInfoRepository.findById(id);
	if (leaveinfo.isPresent()) {
		LeaveInfo leaveinfoEntity = leaveinfo.get();
		short delete = 1;
		leaveinfoEntity.setIsDelete(delete);
		leaveinfoEntity.setModifiedBy(modifiedBy);
		leaveInfoRepository.save(leaveinfoEntity);

	}else {
		log.info("No Leave Information found with the provided ID{} in the DB",id);
		throw new Exception("No Leave Information found with the provided ID in the DB :"+id);
	}
	
	
}

}
