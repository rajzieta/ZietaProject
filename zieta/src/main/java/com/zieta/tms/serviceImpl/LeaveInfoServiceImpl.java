package com.zieta.tms.serviceImpl;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.zieta.tms.dto.LeaveInfoDTO;
import com.zieta.tms.model.LeaveInfo;
import com.zieta.tms.repository.LeaveInfoRepository;
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
	
	@Override
	public void addLeaveInfo(LeaveInfo leaveInfo){
		
		try {
		leaveInfoRepository.save(leaveInfo);
		}
		catch(Exception ex) {
			log.error("Exception occured during the save Cust information",ex);
			}
		}
	
	
public void editleaveInfoById(LeaveInfoDTO leaveinfoDTO) throws Exception {
		
		Optional<LeaveInfo> leaveinfoEntity = leaveInfoRepository.findById(leaveinfoDTO.getId());
		if(leaveinfoEntity.isPresent()) {
			LeaveInfo leaveinfo = modelMapper.map(leaveinfoDTO, LeaveInfo.class);
			leaveInfoRepository.save(leaveinfo);
			
		}else {
			throw new Exception("Customer Information not found with the provided ID : "+leaveinfoDTO.getId());
		}
		
		
	}

	
	
}
