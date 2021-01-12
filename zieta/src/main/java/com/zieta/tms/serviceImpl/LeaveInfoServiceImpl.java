package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.zieta.tms.dto.LeaveInfoDTO;
import com.zieta.tms.dto.LeaveTypeMasterDTO;
import com.zieta.tms.model.CustInfo;
import com.zieta.tms.model.LeaveInfo;
import com.zieta.tms.model.LeaveTypeMaster;
import com.zieta.tms.repository.LeaveInfoRepository;
import com.zieta.tms.repository.LeaveMasterRepository;
import com.zieta.tms.response.CustomerInformationModel;
import com.zieta.tms.service.LeaveInfoService;

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
	
}
