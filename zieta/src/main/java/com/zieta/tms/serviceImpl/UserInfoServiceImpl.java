package com.zieta.tms.serviceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/*import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;*/
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.medialive.model.InputSource;
import com.mysql.cj.xdevapi.JsonArray;
import com.sun.xml.txw2.Document;
//import com.zieta.tms.byd.dto.ProjectByElementsQuery;
//import com.zieta.tms.byd.dto.ProjectByElementsQuery.ProjectSelectionByElements;
import com.zieta.tms.common.MessagesConstants;
import com.zieta.tms.dto.UserInfoDTO;
import com.zieta.tms.dto.UserDetailsDTO;
import com.zieta.tms.model.ClientInfo;
import com.zieta.tms.model.ScreenCategoryMaster;
import com.zieta.tms.model.ScreensMaster;
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.model.UserDetails;
import com.zieta.tms.repository.AccessTypeMasterRepository;
import com.zieta.tms.repository.AccessTypeScreenMappingRepository;
import com.zieta.tms.repository.ClientInfoRepository;
import com.zieta.tms.repository.MessageMasterRepository;
import com.zieta.tms.repository.OrgInfoRepository;
import com.zieta.tms.repository.UserInfoRepository;
import com.zieta.tms.repository.UserDetailsRepository;
import com.zieta.tms.request.PasswordEditRequest;
import com.zieta.tms.request.UserInfoEditRequest;
import com.zieta.tms.request.UserDetailsEditRequest;
import com.zieta.tms.response.LoginResponse;
import com.zieta.tms.response.ResponseMessage;
import com.zieta.tms.response.AddUserResponse;
import com.zieta.tms.response.UserDetailsResponse;
import com.zieta.tms.service.AccessTypeMasterService;
import com.zieta.tms.service.ScreenCategoryMasterService;
import com.zieta.tms.service.ScreensMasterService;
import com.zieta.tms.service.UserInfoService;
import com.zieta.tms.util.TSMUtil;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.dynamic.scaffold.MethodGraph.NodeList;

///
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.math3.util.Pair;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

///



@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

	
	@Autowired
	UserInfoRepository userInfoRepositoryRepository;
	
	@Autowired
	UserDetailsRepository userDetailsRepository;
	
	@Autowired
	AccessTypeScreenMappingRepository accessControlConfigRepository;
	
	
	@Autowired
	ScreensMasterService screensMasterService;
	
	@Autowired
	ScreenCategoryMasterService screenCategoryMasterService;
	
	@Autowired
	AccessTypeMasterService accessTypeMasterService;
	
	@Autowired
	AccessTypeMasterRepository accessTypeMasterRepo;
	
	@Autowired
	UserInfoService userInfoService;
	
	@Autowired
	ClientInfoRepository clientInfoRepo;
	
	@Autowired
	MessageMasterRepository messageMasterRepository;
	
	@Autowired
	OrgInfoRepository orgInfoRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<UserInfoDTO> getAllUserInfoDetails() {
		short notDeleted = 0;
		List<UserInfo> userInfoList= userInfoRepositoryRepository.findByIsDelete(notDeleted);
		List<UserInfoDTO> userInfoDTOs = new ArrayList<UserInfoDTO>();
		mapUserInfoModelToDTO(userInfoList, userInfoDTOs);
		return userInfoDTOs;
	}

	private void mapUserInfoModelToDTO(List<UserInfo> userInfoList, List<UserInfoDTO> userInfoDTOs) {
		UserInfoDTO userInfoDTO = null;
		for (UserInfo userInfo : userInfoList) {
			userInfoDTO =  modelMapper.map(userInfo, UserInfoDTO.class);
			userInfoDTO.setPassword("Welcome1");
			userInfoDTO.setClientCode(clientInfoRepo.findById(userInfo.getClientId()).get().getClientCode());
			userInfoDTO.setClientDescription(clientInfoRepo.findById(userInfo.getClientId()).get().getClientName());
			userInfoDTO.setClientStatus(clientInfoRepo.findById(userInfo.getClientId()).get().getClientStatus());
			userInfoDTO.setAccessType(accessTypeMasterRepo.findById(userInfo.getAccessTypeId()).get().getAccessType());
			if(userInfo.getOrgNode() !=null) {
				userInfoDTO.setOrgNodeName(orgInfoRepository.findById(userInfo.getOrgNode()).get().getOrgNodeName());
			}
		
			String prjMgrName = StringUtils.EMPTY;
			String rempId = StringUtils.EMPTY;
			if(userInfo.getReportingMgr() !=null) {
				Optional<UserInfo> userInfos = userInfoRepositoryRepository.findById(userInfo.getReportingMgr());
				if (userInfos.isPresent()) {
					prjMgrName = TSMUtil.getFullName(userInfos.get());
					 rempId= userInfos.get().getEmpId();
					// otherdetails
					userInfoDTO.setReportingMgrName(prjMgrName);
					userInfoDTO.setReportingMgrEmpId(rempId);
				}
			}
			 userInfoDTOs.add(userInfoDTO);
		}
	}

	@Override
	public UserInfoDTO findByEmail(String email) {
		UserInfoDTO userInfoDTO = null;
		UserInfo userInfo = userInfoRepositoryRepository.findByEmail(email);
		if(userInfo !=null) {
			userInfoDTO =  modelMapper.map(userInfo, UserInfoDTO.class);
		}
		return userInfoDTO;
		
	}

	
	
	@Override
	public UserDetailsResponse getUserData(String emailId) {
		UserInfo userInfo = userInfoRepositoryRepository.findByEmail(emailId);
		String reportingManager = StringUtils.EMPTY;
		
		if(userInfo.getReportingMgr() != null) {
			UserInfo rmInfo = userInfoRepositoryRepository.findById(userInfo.getReportingMgr()).get();
			reportingManager = TSMUtil.getFullName(rmInfo);
		}
		
		 List<Long> accessControlConfigList = accessControlConfigRepository.
				 findByClientIdANDAccessTypeId(userInfo.getClientId(), userInfo.getAccessTypeId());
		 List<ScreensMaster> screensListByClientId = screensMasterService.getScreensByIds(accessControlConfigList);
		 //GET SCREEN CATEGORY DATA
		 List<ScreenCategoryMaster> screenCategoryMasterList = screenCategoryMasterService.getAllScreenMasterCategory();
		 
		 List<String> accessTypes = accessTypeMasterService.findByClientIdANDAccessTypeId(userInfo.getClientId(), userInfo.getAccessTypeId());
		 UserDetailsResponse userDetails = fillUserData(userInfo);
		 userDetails.setReportingManagerName(reportingManager);
		 userDetails.setClientCode(clientInfoRepo.findById(userInfo.getClientId()).get().getClientCode());
		 userDetails.setClientDescription(clientInfoRepo.findById(userInfo.getClientId()).get().getClientName());
		 if(userInfo.getOrgNode() !=null) {
			 userDetails.setOrgNodeName(orgInfoRepository.findById(userInfo.getOrgNode()).get().getOrgNodeName());
		 }
		 userDetails.setScreensByClient(screensListByClientId);
		 userDetails.setAccessTypesByClient(accessTypes);
		 //TO SET SCREENCATEGORYMASTER
		 userDetails.setScreenCategoryMaster(screenCategoryMasterList);
		
		return userDetails;
	}

	
	private UserDetailsResponse fillUserData(UserInfo userInfo) {
		
		UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
		userDetailsResponse.setClientId(userInfo.getClientId());
		userDetailsResponse.setFirstName(userInfo.getUserFname());
		userDetailsResponse.setMiddleName(userInfo.getUserMname());
		userDetailsResponse.setLastName(userInfo.getUserLname());
		userDetailsResponse.setUserEmailId(userInfo.getEmail());
		userDetailsResponse.setEmpId(userInfo.getEmpId());
		userDetailsResponse.setEmpId(userInfo.getExtId());
		if(userInfo.getOrgNode() !=null) {
			userDetailsResponse.setOrgNode(userInfo.getOrgNode());
		}
		if(userInfo.getReportingMgr() !=null) {
			userDetailsResponse.setReportingMgr(userInfo.getReportingMgr());
		}
		userDetailsResponse.setAccessTypeId(userInfo.getAccessTypeId());
		userDetailsResponse.setStatus(userInfo.getIsActive());
		userDetailsResponse.setUserId(userInfo.getId());
		userDetailsResponse.setInfoMessage("User details after successful login");
		
		return userDetailsResponse;
	}
	
	
	public LoginResponse authenticateUser(String email, String password) {
		LoginResponse loginResponse = LoginResponse.builder().infoMessage("").build();
		UserInfoDTO dbUserInfo = findByEmail(email);
		if (dbUserInfo != null) {
			ClientInfo clientInfo = clientInfoRepo.findById(dbUserInfo.getClientId()).get();
			Long clientStatus = clientInfo.getClientStatus();
			Boolean active = Boolean.FALSE;
			loginResponse.setActive(active);
			loginResponse.setLoginValid(active);

			if (clientStatus == 0) {
				loginResponse
						.setInfoMessage(messageMasterRepository.findByMsgCode(MessagesConstants.E103).getMsgDesc());
				return loginResponse;
			} else if (dbUserInfo.getIsActive() == 0) {
				loginResponse
						.setInfoMessage(messageMasterRepository.findByMsgCode(MessagesConstants.E102).getMsgDesc());
				return loginResponse;
			}
			active = ((dbUserInfo.getIsActive() != 0) && (clientStatus != 0));
			if (password.equals(dbUserInfo.getPassword())) {
				loginResponse.setIsSuperAdmin(clientInfo.getSuperAdmin());
				loginResponse.setActive(active);
				loginResponse.setLoginValid(active);
				return loginResponse;

			} else {
				loginResponse
						.setInfoMessage(messageMasterRepository.findByMsgCode(MessagesConstants.E101).getMsgDesc());
				return loginResponse;
			}
		} else {
			loginResponse.setInfoMessage(messageMasterRepository.findByMsgCode(MessagesConstants.E101).getMsgDesc());
			return loginResponse;
		}

	}

	@Override
	public List<UserInfoDTO> findByClientId(Long client_id) {
		short notDeleted = 0;
		List<UserInfo> userInfoList = userInfoRepositoryRepository.findByClientIdAndIsDelete(client_id, notDeleted);
		List<UserInfoDTO> userInfoDTOs = new ArrayList<UserInfoDTO>();
		mapUserInfoModelToDTO(userInfoList, userInfoDTOs);
		return userInfoDTOs;
	}

	
	
	@Override
	public AddUserResponse addUsersInfo(UserInfo userinfo) throws Exception {
		
		short notDeleted = 0;
		AddUserResponse addUserResponse = new AddUserResponse();		
		UserInfo userinfoEntities = userInfoRepositoryRepository.findByEmailAndIsDelete(userinfo.getEmail(),notDeleted);		
		if(userinfoEntities !=null) {
			//set email id already exist validation
			addUserResponse.setInfoMessage("EmailId "+userinfoEntities.getEmail()+" already in use ");
			addUserResponse.setStatus("501");
			//log.info("EmailId is already in use ",userinfoEntities.getEmail());
			return addUserResponse;
			//throw new Exception("Emailid is already in use " +userinfoEntities.getEmail());
		}else{
			
			 addUserResponse.setInfoMessage("User Information Saved successfully ");
			 addUserResponse.setStatus("201");
			 userInfoRepositoryRepository.save(userinfo);	
			 return addUserResponse;
		}
	}
	
	//TO SAVE ADDUSERDETAILS 
	@Override
	public UserDetails addUserDetails(UserDetails userDetails) throws Exception {
		
		short notDeleted = 0;
				
		try {			
			 userDetailsRepository.save(userDetails);				
			
		}catch(Exception e) {
			
		}
			
		return userDetails;
		
	}
	
	
	
	
	
	
	private boolean emailExist(String email) {
		//boolean isValid = fa
		UserInfo userin = userInfoRepositoryRepository.findByEmail(email);
		if(userin!=null) {
			return true;
		}
		return false;
	}
//

	public void editUsersById(@Valid UserInfoEditRequest userinfoeditRequest) throws Exception {
		
		Optional<UserInfo> userinfoEntity = userInfoRepositoryRepository.findById(userinfoeditRequest.getId());
		if(userinfoEntity.isPresent()) {
			UserInfo userInfo = userinfoEntity.get();
			 modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
			 modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
			 modelMapper.map(userinfoeditRequest,userInfo );
			userInfoRepositoryRepository.save(userInfo);
			
		}else {
			throw new Exception("User not found with the provided ID : "+userinfoeditRequest.getId());
		}
		
	}
	
/*
 * UPDATE USER DETAILS BY USER ID
 * 
 */
public ResponseMessage editUserDetailsByUserId(@Valid UserDetailsEditRequest userDetailsEditRequest) throws Exception {	
	try {
		
		ResponseMessage resMsg = new ResponseMessage();
		Optional<UserDetails> userDetailsEntity = userDetailsRepository.findByUserId(userDetailsEditRequest.getUserId());
		if(userDetailsEntity.isPresent()) {
			UserDetails userDetails = userDetailsEntity.get();
			 modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
			 modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
			 modelMapper.map(userDetailsEditRequest,userDetails );
			 userDetailsRepository.save(userDetails);			
			resMsg.setMessage("User Details Successfully Updated...");
			resMsg.setStatus(200);
			return resMsg;
			
		}else {
			throw new Exception("User Details not found with the provided User ID : "+userDetailsEditRequest.getUserId());
		}
	}catch(Exception e) {
		ResponseMessage resMsg = new ResponseMessage();
		resMsg.setMessage("Failed to update User Details!!!");
		resMsg.setStatus(403);
		return resMsg;		
	}		
}

@Override
public UserDetailsDTO findUserDetailsByUserId(long userId) {
	UserDetailsDTO userDetailsDTO = null;
	UserDetails userDetails = userDetailsRepository.findUserDetailsByUserId(userId);
	if(userDetails !=null) {
		userDetailsDTO =  modelMapper.map(userDetails, UserDetailsDTO.class);
	}
	return userDetailsDTO;
	
}

	
	public void deleteUsersById(Long id, String modifiedBy) throws Exception {
	Optional<UserInfo> userinfo = userInfoRepositoryRepository.findById(id);
	if (userinfo.isPresent()) {
		UserInfo userinfoEntitiy = userinfo.get();
		short delete = 1;
		userinfoEntitiy.setIsDelete(delete);
		userinfoEntitiy.setModifiedBy(modifiedBy);
		userInfoRepositoryRepository.save(userinfoEntitiy);

	}else {
		log.info("No User Info found with the provided ID{} in the DB",id);
		throw new Exception("No UserInfo found with the provided ID in the DB :"+id);
	}
	
	
}
	
	
	
	
	@Override
	public void EditPasswordByEmailId(@Valid PasswordEditRequest passwordeditRequest) throws Exception {
		
		Optional<UserInfo> userinfoEntit = userInfoRepositoryRepository.findById(passwordeditRequest.getId());
		UserInfo useremail = userInfoRepositoryRepository.findByEmail(passwordeditRequest.getEmail());
		//String salt = PasswordUtil.getSalt();
		if(useremail!= null && userinfoEntit.isPresent()) {
			
			UserInfo userPassSave = userinfoEntit.get();
		 //if (PasswordUtil.verifyUserPassword((passwordeditRequest.getOldPassword()), (userPassSave.getPassword()), (salt))) {
		//if ((PasswordUtil.getSecurePassword(passwordeditRequest.getOldPassword())).equals(userPassSave.getPassword())) {
			
			if ((passwordeditRequest.getOldPassword()).equals(userPassSave.getPassword())) {
			userPassSave.setPassword(passwordeditRequest.getNewPassword());
			userPassSave.setPassword(passwordeditRequest.getConfirmPassword());
			if(passwordeditRequest.getNewPassword().equals(passwordeditRequest.getConfirmPassword())) {
			
				//	String salt = PasswordUtil.getSalt();
				//userPassSave.setPassword(PasswordUtil.getSecurePassword(userPassSave.getPassword()));
				//userPassSave.setPassword(userPassSave);
			
				userInfoRepositoryRepository.save(userPassSave);
			}
			
			else {
				throw new Exception("New Password and confirm password doesn't match: ");
				
			}
		}
		else {
			throw new Exception("Old Password does not match with the existing password : ");
			
		}
		}
		else {
			throw new Exception("User does not exist : ");
			
		}
	}
	
	@Override
	public List<UserInfo> getUsersByIds(List<Long> teamList){
		
		return userInfoRepositoryRepository.findAllById(teamList);
		
	}

	@Override
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
	}
	/*
	 * TO UPLOAD USERDATA FROM EXCELSHEET
	 * */
	@Override
	public void uploadUsersData(MultipartFile multipartFile, long clientId) {
		//IMPLEMENTATION IS IN PROSSSSGRESS
		
		List<UserInfo> tempUserList = new ArrayList<UserInfo>();
        XSSFWorkbook workbook;
		try {
			workbook = new XSSFWorkbook(multipartFile.getInputStream());			
			XSSFSheet worksheet = workbook.getSheetAt(0);

	        for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
	            UserInfo tempUser = new UserInfo();

	            XSSFRow row = worksheet.getRow(i);

	            tempUser.setId((long) row.getCell(0).getNumericCellValue());
	            tempUser.setAccessTypeId((long) row.getCell(1).getNumericCellValue());
	            tempUser.setClientId((long) row.getCell(2).getNumericCellValue());
	            tempUser.setCreatedBy(row.getCell(1).getStringCellValue());
	            tempUser.setEmail(row.getCell(1).getStringCellValue());
	            tempUser.setEmpId(row.getCell(1).getStringCellValue());
	            tempUser.setExpTemplateId((long) row.getCell(2).getNumericCellValue());
	            tempUser.setExtId(row.getCell(1).getStringCellValue());
	            tempUser.setIsActive((short) row.getCell(2).getNumericCellValue());
	            tempUser.setIsDelete((short) row.getCell(2).getNumericCellValue());
	            tempUser.setIsExpOpen((short) row.getCell(2).getNumericCellValue());
	            tempUser.setIsTsOpen((short) row.getCell(2).getNumericCellValue());
	            tempUser.setModifiedBy(row.getCell(1).getStringCellValue());
	            //tempUser.setOrgNode((long) row.getCell(2).getNumericCellValue());
	            tempUser.setPassword(row.getCell(1).getStringCellValue());
	            tempUser.setPhoneNo(row.getCell(1).getStringCellValue());
	            tempUser.setReportingMgr((long) row.getCell(2).getNumericCellValue());
	            tempUser.setUserFname(row.getCell(1).getStringCellValue());	            
	            tempUser.setUserLname(row.getCell(1).getStringCellValue());
	            tempUser.setUserMname(row.getCell(1).getStringCellValue());
	            
	            log.info("uesr Data  "+tempUser);
	            //tempUser.setContent(row.getCell(1).getStringCellValue());
	            tempUserList.add(tempUser);   
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if(tempUserList.size()>0) {
        	userInfoRepositoryRepository.saveAll(tempUserList);
        }
		
		
	}

	/*@Override
	public List<Object> getAllBYDProject() {
		
		JAXBContext context;
		try {
			
			//log.info("====== in service=====>");
			context = JAXBContext.newInstance(ProjectByElementsQuery.class);
			//log.info("====== at line 534====>");
			Marshaller mar = context.createMarshaller();
			mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			ProjectByElementsQuery request = new ProjectByElementsQuery();
			
			ProjectByElementsQuery.ProcessingConditions processingConditions = new ProjectByElementsQuery.ProcessingConditions();						
			processingConditions.setQueryHitsMaximumNumberValue("10");
			processingConditions.setQueryHitsUnlimitedIndicator("false");
			
			request.setProcessingConditions(processingConditions);
			
			ProjectByElementsQuery.ProjectSelectionByElements projectSelectionByElements = new ProjectByElementsQuery.ProjectSelectionByElements();
			projectSelectionByElements.setInclusionExclusionCode("i");
			projectSelectionByElements.setIntervalBoundaryTypeCode("1");
			projectSelectionByElements.setLowerBoundaryProjectID("*");
			//projectSelectionByElements.setLowerBoundaryProjectID("CPSO1");
			
			request.setProjectSelectionByElements(projectSelectionByElements);
			
			//ProjectByElementsQuery.SelectionByProjectID SelectionByProjectID = new ProjectByElementsQuery.SelectionByProjectID();

			
			//ProjectSelectionByElements
			
			/*ProjectSelectionByElements.SelectionByProjectID SelectionByProjectID = new ProjectSelectionByElements.SelectionByProjectID();
			SelectionByProjectID.setInclusionExclusionCode("i");
			SelectionByProjectID.setIntervalBoundaryTypeCode("1");
			SelectionByProjectID.setLowerBoundaryProjectID("CPSO1");//*		
			
			///request.setProjectSelectionByElements(SelectionByProjectID);
			///request.setSelectionByProjectID(SelectionByProjectID);
			//request.setProjectSelectionByElements(SelectionByProjectID);
			
			//request.setProjectSelectionByElements(ProjectSelectionByElements.SelectionByProjectID);
			
			
			StringWriter sw = new StringWriter();
			mar.marshal(request, sw);
			
			String result = sw.toString();
			String requestData = result.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");
			String data = requestData.replace("<ProjectByElementsQuery>", "<glob:ProjectByElementsQuery>");//prev
			
			data = data.replace("<ProjectSelectionByElements>", "<ProjectSelectionByElements>\n<SelectionByProjectID>");
			data = data.replace("</ProjectSelectionByElements>", "</SelectionByProjectID>\n</ProjectSelectionByElements>");
			data = data.replace("</ProjectByElementsQuery>", "</glob:ProjectByElementsQuery>");
			//System.out.println("data====>"+data);

			String finalString = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:glob=\"http://sap.com/xi/SAPGlobal20/Global\">\n"
					+ "<soapenv:Header/>\n" + "<soapenv:Body>\n" 
					 + data
					+ "</soapenv:Body>\n" + "</soapenv:Envelope>";

			///System.out.println("finalString======> " + finalString);
			List<Pair<Integer, String>> list;
			try {
				list = bydHttpRequest(finalString);
				
				///System.out.println("HttpRequest Output=======" + list);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
		// mar.marshal(request, new File("./book.xml"));
		// mar.marshal(request, System.out);		
		return null;
	}*/
	
	
	private List<Pair<Integer, String>> bydHttpRequest(String finalString) throws ClientProtocolException, IOException {
		/*
		///System.out.println("======>called bydHttpRequest<=========");
		
		String url = "https://my351070.sapbydesign.com/sap/bc/srt/scs/sap/queryprojectin";
		HttpPut httpPut = new HttpPut(url);
		
		httpPut.setHeader("content-type", "text/xml");
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("zietauser", "Welcome123");
		provider.setCredentials(AuthScope.ANY, credentials);
		HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
		StringEntity entity = new StringEntity(finalString);
		httpPut.setEntity(entity);

		HttpResponse resp = client.execute(httpPut);
		Integer httpStatusCd = resp.getStatusLine().getStatusCode();
		String respString = EntityUtils.toString(resp.getEntity());
		///log.info("Response Data from portal {} ", respString);
		
		JSONObject data = XML.toJSONObject(respString);
		log.info("*****************************************************BBBB");
		
		//log.info("Data===>"+data.getJSONObject("soap-env:Envelope").getJSONObject("soap-env:Body").getJSONObject("n0:ProjectByElementsResponse_sync").getJSONArray("ProjectQueryResponse"));		
		//log.info("EEEE*****************************************************");
		
		JSONArray objArry = data.getJSONObject("soap-env:Envelope").getJSONObject("soap-env:Body").getJSONObject("n0:ProjectByElementsResponse_sync").getJSONArray("ProjectQueryResponse");
		log.info("objArry  ===>"+objArry.length());
		//BEING PROJECT ARRAY LOOP
		for(int i=0;i<objArry.length();i++) {
			String projectId = objArry.getJSONObject(i).get("ProjectID").toString();
			//log.info("projectid "+objArry.getJSONObject(i).get("ProjectID"));
			
			//JSONArray taskObjArry = objArry.getJSONObject(i).getJSONArray("ProjectTask");
			
			JSONArray taskObjArry = objArry.getJSONObject(i).getJSONArray("ProjectTask");
			//log.info("task array ===>>>"+taskObjArry);
			
			//BEING PROJECT TASK LOOP
			for(int j=0;j<taskObjArry.length();j++) {
				
				String projectElementId = taskObjArry.getJSONObject(i).get("ProjectElementID").toString();
				String taskName = taskObjArry.getJSONObject(i).getJSONObject("TaskName").getJSONObject("Name").get("content").toString();
				log.info("task name ===>>>"+taskName);
				
			}
			
			//END TASK LOOP
			
		}//END PROJECT ARRAY
		
		//============================TO READ XML RESP DATA=========================
		 
		
		String result = null;
		//System.out.println("entity ====>"+entity);
        if (entity != null) {

            // A Simple JSON Response Read
            InputStream instream = entity.getContent();
            result = convertStreamToString(instream);
            // now you have the string representation of the HTML request
            //System.out.println("=============================>RESPONSE:" + result);
            instream.close();
            if (resp.getStatusLine().getStatusCode() == 200) {
               // netState.setLogginDone(true);
            }

        }
		
		//DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
		
		
		
		
		
		//======================End read data ======================================
		
		//log.info(" ====Resp data===="+resp.getHeaders("ProjectQueryResponse"));
		List<Pair<Integer, String>> listOfPairs = new ArrayList<>();
		listOfPairs.add(new Pair<>(httpStatusCd, respString));
		//log.info("============>Status_Code and Response Binded to listOfPairs {} ", listOfPairs);		
		//log.info("list of data ======>"+listOfPairs);
		
		//log.info("=====================End Data response========================");
		return listOfPairs;
		
		*/
		
		return null;
	}
	
	
	private static String convertStreamToString(InputStream is) {

	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}

	
	
	
	
	//end temp impl 
	
	
	
}
