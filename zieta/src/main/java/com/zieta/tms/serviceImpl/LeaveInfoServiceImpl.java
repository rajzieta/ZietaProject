package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zieta.tms.common.TMSConstants;
import com.zieta.tms.dto.LeaveInfoDTO;
import com.zieta.tms.dto.LeaveTypeMasterDTO;
import com.zieta.tms.model.ClientInfo;
import com.zieta.tms.model.LeaveInfo;
import com.zieta.tms.model.LeaveTypeMaster;
import com.zieta.tms.repository.ClientInfoRepository;
import com.zieta.tms.repository.LeaveInfoRepository;
import com.zieta.tms.repository.LeaveMasterRepository;
import com.zieta.tms.repository.StatusMasterRepository;
import com.zieta.tms.service.LeaveInfoService;
import com.zieta.tms.model.StatusMaster;

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

	@Autowired
	StatusMasterRepository statusMasterRepository;
	
	@Autowired
	ClientInfoRepository clientInfoRepository;

	@Override
	public void addLeaveInfo(LeaveInfo leaveInfo) {

		try {
			leaveInfoRepository.save(leaveInfo);
		} catch (Exception ex) {
			log.error("Exception occured during the save Leave information", ex);
		}
	}

	@Override
	public void editleaveInfoById(LeaveInfoDTO leaveinfoDTO) throws Exception {

		Optional<LeaveInfo> leaveinfoEntity = leaveInfoRepository.findById(leaveinfoDTO.getId());
		if (leaveinfoEntity.isPresent()) {
			LeaveInfo leaveinfo = modelMapper.map(leaveinfoDTO, LeaveInfo.class);
			leaveInfoRepository.save(leaveinfo);

		} else {
			throw new Exception("Leave Information not found with the provided ID : " + leaveinfoDTO.getId());
		}

	}

	@Override
	public List<LeaveInfoDTO> getAllLeaveInfo() {
		short notDeleted = 0;
		List<LeaveInfo> leaveInfos = leaveInfoRepository.findByIsDelete(notDeleted);
		List<LeaveInfoDTO> leaveInfoList = new ArrayList<>();

		for (LeaveInfo leaveInfo : leaveInfos) {
			LeaveInfoDTO leaveInformationModel = modelMapper.map(leaveInfo, LeaveInfoDTO.class);

			leaveInfoList.add(leaveInformationModel);

		}
		return leaveInfoList;
	}


	@Override
	public void addLeaveTypeMaster(@Valid LeaveTypeMaster leavemaster) {

		try {
			leaveMasterRepository.save(leavemaster);
		} catch (Exception ex) {
			log.error("Exception occured during the save Leave information", ex);
		}
	}

	@Override
	public void editLeaveMasterById(@Valid LeaveTypeMasterDTO leavemasterDTO) throws Exception {

		Optional<LeaveTypeMaster> leavemasterEntity = leaveMasterRepository.findById(leavemasterDTO.getId());
		if (leavemasterEntity.isPresent()) {
			LeaveTypeMaster leavemaster = modelMapper.map(leavemasterDTO, LeaveTypeMaster.class);
			leaveMasterRepository.save(leavemaster);

		} else {
			throw new Exception("Leave Information not found with the provided ID : " + leavemasterDTO.getId());
		}

	}

	@Override
	public List<LeaveTypeMasterDTO> getAllLeaveMaster() {
		short notDeleted = 0;
		List<LeaveTypeMaster> leaveInfoList = leaveMasterRepository.findByIsDelete(notDeleted);
		List<LeaveTypeMasterDTO> leaveMasterInfoList = new ArrayList<>();

		for (LeaveTypeMaster leaveMasterInfo : leaveInfoList) {
			LeaveTypeMasterDTO leaveInformationModel = modelMapper.map(leaveMasterInfo, LeaveTypeMasterDTO.class);
			ClientInfo clientInfo =clientInfoRepository.findById(leaveMasterInfo.getClientId()).get();
			if(clientInfo != null) {
				leaveInformationModel.setClientCode(clientInfo.getClientCode());
				leaveInformationModel.setClientName(clientInfo.getClientName());
			}
			

			leaveMasterInfoList.add(leaveInformationModel);

		}
		return leaveMasterInfoList;
	}

	@Override
	public List<LeaveTypeMasterDTO> getAllLeaveTypesByClient(Long clientId) {
		short notDeleted = 0;
		List<LeaveTypeMaster> orgnodesByClientList = leaveMasterRepository.findByClientIdAndIsDelete(clientId,
				notDeleted);
		List<LeaveTypeMasterDTO> orgnodesByClientResponseList = new ArrayList<>();
		LeaveTypeMasterDTO orgnodesByClientResponse = null;
		for (LeaveTypeMaster orgnodesByClient : orgnodesByClientList) {
			orgnodesByClientResponse = modelMapper.map(orgnodesByClient, LeaveTypeMasterDTO.class);
			ClientInfo clientInfo =clientInfoRepository.findById(clientId).get();
			if(clientInfo != null) {
				orgnodesByClientResponse.setClientCode(clientInfo.getClientCode());
				orgnodesByClientResponse.setClientName(clientInfo.getClientName());
			}

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
			orgnodesByClientResponse = modelMapper.map(orgnodesByClient, LeaveInfoDTO.class);

			orgnodesByClientResponseList.add(orgnodesByClientResponse);
		}
		return orgnodesByClientResponseList;
	}

	@Override
	public List<LeaveInfoDTO> getAllLeavesByClientUser(Long clientId, Long userId) {
		short notDeleted = 0;
		List<LeaveInfo> orgnodesByClientList = leaveInfoRepository.findByClientIdAndUserIdAndIsDelete(clientId, userId,
				notDeleted);
		List<LeaveInfoDTO> orgnodesByClientResponseList = new ArrayList<>();
		LeaveInfoDTO orgnodesByClientResponse = null;
		for (LeaveInfo orgnodesByClient : orgnodesByClientList) {
			orgnodesByClientResponse = modelMapper.map(orgnodesByClient, LeaveInfoDTO.class);
			orgnodesByClientResponse.setLeaveTypeDescription(
					leaveMasterRepository.findById(orgnodesByClient.getLeaveType()).get().getLeaveType());

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

		} else {
			log.info("No Leave Information found with the provided ID{} in the DB", id);
			throw new Exception("No Leave Information found with the provided ID in the DB :" + id);
		}

	}

	@Override
	@Transactional
	public List<LeaveInfoDTO> findActiveLeavesByClientIdAndApproverId(Long clientId, Long approverId) {

		short notDeleted = 0;
		
		log.info(clientId +" == "+TMSConstants.LEAVE+"=="+TMSConstants.LEAVE_SUBMITTED);
		//COMMENT PREV CONCEPT BCOZ IT PROVIDE NULL POINTER EXCEPTION IN CASE CLIENT ID NOT EXIT IN STATUS MASTER TABLE
		//long statusId = statusMasterRepository.findByClientIdAndStatusTypeAndStatusCodeAndIsDelete(clientId,TMSConstants.LEAVE, TMSConstants.LEAVE_SUBMITTED, (short) 0).getId();		
		
		StatusMaster sm = statusMasterRepository.findByClientIdAndStatusTypeAndStatusCodeAndIsDelete(clientId,TMSConstants.LEAVE, TMSConstants.LEAVE_SUBMITTED, (short) 0);
		long statusId =0;
		if(sm!=null) {
			statusId = sm.getId();
		}		
		List<LeaveInfo> levInfo = leaveInfoRepository.findByClientIdAndApproverIdAndStatusIdAndIsDelete(clientId,
				approverId, statusId, notDeleted);
		 
		return transfromLeaveData(levInfo);
	}

	private List<LeaveInfoDTO> transfromLeaveData(List<LeaveInfo> levInfo) {
		List<LeaveInfoDTO> leaveInfoList = new ArrayList<>();
		LeaveInfoDTO leaveInfoDTO;
		for (LeaveInfo leaves : levInfo) {
			leaveInfoDTO = modelMapper.map(leaves, LeaveInfoDTO.class);
			leaveInfoDTO.setStatusId(leaves.getStatusId());

			leaveInfoDTO.setLeaveTypeDescription(StringUtils.EMPTY);
			if (null != leaves.getLeaveType()) {
				Optional<LeaveTypeMaster> orgInfo = leaveMasterRepository.findById(leaves.getLeaveType());
				if (orgInfo.isPresent()) {
					leaveInfoDTO.setLeaveTypeDescription(orgInfo.get().getLeaveType());

				}
			}

			leaveInfoList.add(leaveInfoDTO);
		}
		return leaveInfoList;
	}

	public void deleteLeaveTypeById(Long id, String modifiedBy) throws Exception {

		Optional<LeaveTypeMaster> leaveTypeMaster = leaveMasterRepository.findById(id);
		if (leaveTypeMaster.isPresent()) {
			LeaveTypeMaster leaveinfoEntity = leaveTypeMaster.get();
			short delete = 1;
			leaveinfoEntity.setIsDelete(delete);
			leaveinfoEntity.setModifiedBy(modifiedBy);
			leaveMasterRepository.save(leaveinfoEntity);

		} else {
			log.info("No Leave Information found with the provided ID{} in the DB", id);
			throw new Exception("No Leave Information found with the provided ID in the DB :" + id);
		}

	}

	@Override
	public List<LeaveInfoDTO> getLeaveHistoryByApprover(Long clientId, Long approverId, String startDate, String endDate) {
		
		List<String> statusCode = new ArrayList<>();
			statusCode.add(TMSConstants.LEAVE_APPROVED);
			statusCode.add(TMSConstants.LEAVE_REJECTED);
		
		List<Long> statusList = statusMasterRepository.getStatusIdByClientByCodeByType(clientId, statusCode, TMSConstants.LEAVE);
		List<LeaveInfo> leaveList = leaveInfoRepository.findByApproverIdAndStatusIdInAndLeaveStartDateBetween
																		(approverId, statusList, startDate, endDate);
		return transfromLeaveData(leaveList);
	}
	
	
	@Override
	public List<LeaveInfoDTO> getAllLeavesByClientUserAndDateRange(Long clientId, Long userId, String startDate, String endDate) {
		short notDeleted = 0;
		//List<LeaveInfo> orgnodesByClientList = leaveInfoRepository.findByClientIdAndUserIdAndIsDelete(clientId, userId, notDeleted);
		
		List<LeaveInfo> orgnodesByClientList = leaveInfoRepository.findByClientIdAndUserIdAndLeaveStartDateBetweenAndIsDelete(clientId, userId, notDeleted, startDate, endDate);
				
		List<LeaveInfoDTO> orgnodesByClientResponseList = new ArrayList<>();
		LeaveInfoDTO orgnodesByClientResponse = null;
		for (LeaveInfo orgnodesByClient : orgnodesByClientList) {
			orgnodesByClientResponse = modelMapper.map(orgnodesByClient, LeaveInfoDTO.class);
			orgnodesByClientResponse.setLeaveTypeDescription(
					leaveMasterRepository.findById(orgnodesByClient.getLeaveType()).get().getLeaveType());

			orgnodesByClientResponseList.add(orgnodesByClientResponse);
		}
		return orgnodesByClientResponseList;
	}
	
	
	@Override
	public List<LeaveInfoDTO> getAllLeavesByClientAndDateRange(Long clientId, String startDate, String endDate) {
		short notDeleted = 0;
		//List<LeaveInfo> orgnodesByClientList = leaveInfoRepository.findByClientIdAndUserIdAndIsDelete(clientId, userId, notDeleted);
		
		List<LeaveInfo> orgnodesByClientList = leaveInfoRepository.findByClientIdAndLeaveStartDateBetweenAndIsDelete(clientId, notDeleted, startDate, endDate);
				
		List<LeaveInfoDTO> orgnodesByClientResponseList = new ArrayList<>();
		LeaveInfoDTO orgnodesByClientResponse = null;
		for (LeaveInfo orgnodesByClient : orgnodesByClientList) {
			orgnodesByClientResponse = modelMapper.map(orgnodesByClient, LeaveInfoDTO.class);
			orgnodesByClientResponse.setLeaveTypeDescription(
					leaveMasterRepository.findById(orgnodesByClient.getLeaveType()).get().getLeaveType());

			orgnodesByClientResponseList.add(orgnodesByClientResponse);
		}
		return orgnodesByClientResponseList;
	}
	
	
	
	

}
