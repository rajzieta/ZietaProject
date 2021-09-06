package com.zieta.tms.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zieta.tms.common.MessagesConstants;
import com.zieta.tms.dto.UserInfoDTO;
import com.zieta.tms.dto.ConnectionMasterInfoDTO;
import com.zieta.tms.dto.UserDetailsDTO;
import com.zieta.tms.model.ClientInfo;
import com.zieta.tms.model.ConnectionMasterInfo;
import com.zieta.tms.model.ScreensMaster;
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.model.UserDetails;
import com.zieta.tms.repository.AccessTypeMasterRepository;
import com.zieta.tms.repository.AccessTypeScreenMappingRepository;
import com.zieta.tms.repository.ClientInfoRepository;
import com.zieta.tms.repository.ConnectionMasterInfoRepository;
import com.zieta.tms.repository.MessageMasterRepository;
import com.zieta.tms.repository.OrgInfoRepository;
import com.zieta.tms.repository.UserInfoRepository;
import com.zieta.tms.repository.UserDetailsRepository;
import com.zieta.tms.request.ConnectionMasterInfoEditRequest;
import com.zieta.tms.request.PasswordEditRequest;
import com.zieta.tms.request.UserInfoEditRequest;
import com.zieta.tms.request.UserDetailsEditRequest;
import com.zieta.tms.response.LoginResponse;
import com.zieta.tms.response.ResponseMessage;
import com.zieta.tms.response.AddResponse;
import com.zieta.tms.response.AddUserResponse;
import com.zieta.tms.response.UserDetailsResponse;
import com.zieta.tms.service.AccessTypeMasterService;
import com.zieta.tms.service.ConnectionMasterInfoService;
import com.zieta.tms.service.ScreensMasterService;
import com.zieta.tms.service.UserInfoService;
import com.zieta.tms.util.TSMUtil;

import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
public class ConnectionMasterInfoServiceImpl implements ConnectionMasterInfoService {


	@Autowired
	ConnectionMasterInfoRepository connectionMasterInfoRepository;
	
	
	@Autowired
	MessageMasterRepository messageMasterRepository;
	
	@Autowired
	OrgInfoRepository orgInfoRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	



	

	
	
	
	
	

	/*@Override
	public List<UserInfoDTO> findByClientId(Long client_id) {
		short notDeleted = 0;
		List<UserInfo> userInfoList = userInfoRepositoryRepository.findByClientIdAndIsDelete(client_id, notDeleted);
		List<UserInfoDTO> userInfoDTOs = new ArrayList<UserInfoDTO>();
		mapUserInfoModelToDTO(userInfoList, userInfoDTOs);
		return userInfoDTOs;
	}*/

	
	
	/*@Override
	public AddResponse addConnectionMasterInfo02(ConnectionMasterInfo connectionMasterInfo) throws Exception {
		
		short notDeleted = 0;
		AddResponse addResponse = new AddResponse();		
		//ConnectionMasterInfo userinfoEntities = connectionMasterInfoRepository.findByClientIdAndIsDelete(connectionMasterInfo.getClientId(),notDeleted);		
		ConnectionMasterInfo connectiomMasterInfoEntities = null;
		if(connectiomMasterInfoEntities !=null) {
			//set email id already exist validation
			//addResponse.setInfoMessage("EmailId "+userinfoEntities.getEmail()+" already in use ");
			//addResponse.setStatus("501");
			//log.info("EmailId is already in use ",userinfoEntities.getEmail());
			return addResponse;
			//throw new Exception("Emailid is already in use " +userinfoEntities.getEmail());
		}else{
			
			addResponse.setInfoMessage("Connection Master Information Saved successfully ");
			addResponse.setStatus("201");
			connectionMasterInfoRepository.save(connectionMasterInfo);	
			 return addResponse;
		}
	}*/
	
	
	@Override
	public ConnectionMasterInfo addConnectionMasterInfo(ConnectionMasterInfo connectionMasterInfo) throws Exception {
				return connectionMasterInfoRepository.save(connectionMasterInfo);
		
	}
	
	 
		
	
//

	public ConnectionMasterInfo editConnectionMasterById(@Valid ConnectionMasterInfoEditRequest connectionMasterInfoEditRequest) throws Exception {
		
		Optional<ConnectionMasterInfo> connectionMasterInfoEntity = connectionMasterInfoRepository.findById(connectionMasterInfoEditRequest.getId());
		if(connectionMasterInfoEntity.isPresent()) {
			ConnectionMasterInfo connectionMasterInfo = connectionMasterInfoEntity.get();
			 modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
			 modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
			 modelMapper.map(connectionMasterInfoEditRequest,connectionMasterInfo );
			return connectionMasterInfoRepository.save(connectionMasterInfo);
			
		}else {
			throw new Exception("Connection master not found with the provided ID : "+connectionMasterInfoEditRequest.getId());
		}
		
	}
	
/*
 * UPDATE USER DETAILS BY USER ID
 * 
 */


	
	
	

	/*@Override
	public UserInfoDTO findByUserId(long userId) {
		UserInfoDTO userInfoDTO = null;
		UserInfo userInfo = userInfoRepositoryRepository.findById(userId).get();
		if(userInfo !=null) {
			userInfoDTO =  modelMapper.map(userInfo, UserInfoDTO.class);
			String reportingManager = StringUtils.EMPTY;
			userInfoDTO.setPassword(StringUtils.EMPTY);
			
			if(userInfo.getReportingMgr() != null) {
				UserInfo rmInfo = userInfoRepositoryRepository.findById(userInfo.getReportingMgr()).get();
				reportingManager = TSMUtil.getFullName(rmInfo);
				userInfoDTO.setReportingMgrEmpId(rmInfo.getEmpId());
			}
			userInfoDTO.setReportingMgrName(reportingManager);
			ClientInfo clientInfo = clientInfoRepo.findById(userInfo.getClientId()).get();
			userInfoDTO.setClientCode(clientInfo.getClientCode());
			userInfoDTO.setClientDescription(clientInfo.getClientName());
			userInfoDTO.setClientStatus(clientInfo.getClientStatus());
			 if(userInfo.getOrgNode() !=null) {
				 userInfoDTO.setOrgNodeName(orgInfoRepository.findById(userInfo.getOrgNode()).get().getOrgNodeName());
			 }
		}
		return userInfoDTO;
	}*/
	private void mapConnectionMasterInfoModelToDTO(List<ConnectionMasterInfo> connectionMasterInfoList, List<ConnectionMasterInfoDTO> connectionMasterInfoDTOs) {
	ConnectionMasterInfoDTO connectionInfoDTO = null;
		for (ConnectionMasterInfo connectionMasterInfo : connectionMasterInfoList) {
			connectionInfoDTO =  modelMapper.map(connectionMasterInfo, ConnectionMasterInfoDTO.class);
			connectionInfoDTO.setPassword("Welcome1");
			//connectionInfoDTO.setClientCode(clientInfoRepo.findById(userInfo.getClientId()).get().getClientCode());
			//connectionInfoDTO.setClientDescription(clientInfoRepo.findById(userInfo.getClientId()).get().getClientName());
			//connectionInfoDTO.setClientStatus(clientInfoRepo.findById(userInfo.getClientId()).get().getClientStatus());
			//connectionInfoDTO.setAccessType(accessTypeMasterRepo.findById(userInfo.getAccessTypeId()).get().getAccessType());
			
			
			connectionMasterInfoDTOs.add(connectionInfoDTO);
		}
	}











	@Override
	public List<ConnectionMasterInfoDTO> findByClientId(Long client_id) {
		short notDeleted = 0;
		List<ConnectionMasterInfo> connectionMasterInfoList= connectionMasterInfoRepository.findByClientIdAndIsDelete(client_id,notDeleted);
		List<ConnectionMasterInfoDTO> connectionInfoDTOs = new ArrayList<ConnectionMasterInfoDTO>();
		mapConnectionMasterInfoModelToDTO(connectionMasterInfoList, connectionInfoDTOs);
		return connectionInfoDTOs;
	}

	@Override
	public ConnectionMasterInfoDTO findById(long id) {
		ConnectionMasterInfoDTO connectionMasterInfoDTO = null;
		
		try {
			ConnectionMasterInfo connectionMasterInfo = connectionMasterInfoRepository.findById(id).get();
				
				if(connectionMasterInfo !=null) {
					connectionMasterInfoDTO =  modelMapper.map(connectionMasterInfo, ConnectionMasterInfoDTO.class);
					String reportingManager = StringUtils.EMPTY;
					connectionMasterInfoDTO.setPassword(StringUtils.EMPTY);
				}
				
			}catch(Exception e) {
				
			}
		
		
		return connectionMasterInfoDTO;
	}
	
	
	
	public void deleteConnectionMasterById(Long id, String modifiedBy) throws Exception {
		Optional<ConnectionMasterInfo> connectionMasterInfo = connectionMasterInfoRepository.findById(id);
		if (connectionMasterInfo.isPresent()) {
			ConnectionMasterInfo ConnectionMasterInfoEntitiy = connectionMasterInfo.get();
			short delete = 1;
			ConnectionMasterInfoEntitiy.setIsDelete(delete);
			ConnectionMasterInfoEntitiy.setModifiedBy(modifiedBy);
			connectionMasterInfoRepository.save(ConnectionMasterInfoEntitiy);

		}else{
			log.info("No Connection Master Info found with the provided ID{} in the DB",id);
			throw new Exception("No ConnectionMasterInfo found with the provided ID in the DB :"+id);
		}
		
		
	}

	@Override
	public List<Object> getAllBYDProject() {
		System.out.println("testing======>");
		// TODO Auto-generated method stub
		return null;
	}

	
}
