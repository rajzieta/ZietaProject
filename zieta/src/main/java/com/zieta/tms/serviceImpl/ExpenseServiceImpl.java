package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hpsf.Decimal;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zieta.tms.common.TMSConstants;
import com.zieta.tms.dto.DateRange;
import com.zieta.tms.dto.ExpenseEntriesDTO;
import com.zieta.tms.dto.ExpenseInfoDTO;
import com.zieta.tms.dto.ExpenseMasterDTO;
import com.zieta.tms.model.CountryMaster;
import com.zieta.tms.model.CurrencyMaster;
import com.zieta.tms.model.ExpTemplateSteps;
import com.zieta.tms.model.ExpenseEntries;
import com.zieta.tms.model.ExpenseInfo;
import com.zieta.tms.model.ExpenseTypeMaster;
import com.zieta.tms.model.ExpenseWorkflowRequest;
import com.zieta.tms.model.OrgInfo;
import com.zieta.tms.model.ProjectInfo;
import com.zieta.tms.model.StatusMaster;
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.model.WorkflowRequest;
import com.zieta.tms.repository.CountryMasterRepository;
import com.zieta.tms.repository.CurrencyMasterRepository;
import com.zieta.tms.repository.ExpTemplateStepsRepository;
import com.zieta.tms.repository.ExpenseEntriesRepository;
import com.zieta.tms.repository.ExpenseInfoRepository;
import com.zieta.tms.repository.ExpenseTypeMasterRepository;
import com.zieta.tms.repository.ExpenseWorkflowRepository;
import com.zieta.tms.repository.OrgInfoRepository;
import com.zieta.tms.repository.ProjectInfoRepository;
import com.zieta.tms.repository.StatusMasterRepository;
import com.zieta.tms.repository.UserInfoRepository;
import com.zieta.tms.service.ExpenseService;
import com.zieta.tms.util.TSMUtil;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.build.Plugin.Engine.Source.Empty;

@Service
@Transactional
@Slf4j
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	ExpenseTypeMasterRepository expenseTypeMasterRepository;

	@Autowired
	CountryMasterRepository countryMasterRepository;

	@Autowired
	CurrencyMasterRepository currencyMasterRepository;

	@Autowired
	ExpenseInfoRepository expenseInfoRepository;

	@Autowired
	ProjectInfoRepository projectInfoRepository;

	@Autowired
	ExpenseEntriesRepository expenseEntriesRepository;

	@Autowired
	ExpenseWorkflowRepository expenseWorkflowRepository;

	@Autowired
	OrgInfoRepository orgInfoRepository;
	
	@Autowired
	UserInfoRepository userInfoRepository;

	@Autowired
	StatusMasterRepository statusMasterRepository;
	
	@Autowired
	ExpTemplateStepsRepository expTemplateStepsRepository;

	@Autowired
	ModelMapper modelMapper;

	
	  @Autowired
	  @Qualifier("stateByName") 
	  Map<String, Long> stateByName;
	 

	
	  @Autowired
	  @Qualifier("actionTypeByName") 
	  Map<String, Long> actionTypeByName;
	 

	// @Autowired
	// @Qualifier("statusIdByCode")
	// Map<String, Long> statusIdByCode;

	@Override
	public List<ExpenseInfoDTO> getAllExpenses() {
		short notDeleted = 0;
		List<ExpenseInfo> expenseInfos = expenseInfoRepository.findByIsDelete(notDeleted);
		List<ExpenseInfoDTO> expenseInfoDTOs = new ArrayList<ExpenseInfoDTO>();
		ExpenseInfoDTO expenseInfoDTO = null;
		for (ExpenseInfo expenseInfo : expenseInfos) {
			expenseInfoDTO = modelMapper.map(expenseInfo, ExpenseInfoDTO.class);
//			expenseInfoDTO.setOrgUnitCode(StringUtils.EMPTY);
//			if (null != expenseInfo.getOrgUnitId()) {
//			Optional<OrgInfo>  orgInfo = orgInfoRepository.findById(expenseInfo.getOrgUnitId());
//			if (orgInfo.isPresent()) {
//			//	expenseInfoDTO.setOrgUnitCode(orgInfo.get().getOrgNodeCode());
//
//			}
//		}

			expenseInfoDTO.setOrgUnitDesc(StringUtils.EMPTY);
			if (null != expenseInfo.getOrgUnitId()) {
				Optional<OrgInfo> orgInfo = orgInfoRepository.findById(expenseInfo.getOrgUnitId());
				if (orgInfo.isPresent()) {
					expenseInfoDTO.setOrgUnitDesc(orgInfo.get().getOrgNodeName());

				}
			}

			expenseInfoDTOs.add(expenseInfoDTO);
		}
		return expenseInfoDTOs;
	}

	@Override
	public List<ExpenseInfoDTO> findByClientIdAndUserId(Long clientId, Long userId, String startDate, String endDate) {

		short notDeleted = 0;
		StatusMaster statusDetails = statusMasterRepository.findByClientIdAndStatusTypeAndStatusCodeAndIsDelete(clientId,
				TMSConstants.EXPENSE, TMSConstants.EXPENSE_DRAFT,notDeleted);
		List<Long> filterOnStatusId = new ArrayList<>();
		filterOnStatusId.add(statusDetails.getId());
		List<ExpenseInfoDTO> expenseInfoList = new ArrayList<>();
		List<ExpenseInfo> expenseInfos = expenseInfoRepository.findByClientIdAndUserIdAndIsDeleteAndStatusIdNotInAndExpStartDateBetween(clientId, userId,
				notDeleted,filterOnStatusId, startDate, endDate);
		ExpenseInfoDTO expenseInfoDTO = null;

//			Long statusIds =  statusMasterRepository
//				.findByClientIdAndStatusTypeAndStatusCodeNotAndIsDelete(clientId,
//							TMSConstants.EXPENSE, TMSConstants.EXPENSE_DRAFT, (short) 0);
//		//	System.out.println(+statusId);
//			for (Long statuses : statusIds) {
//			List<ExpenseInfo> expenseInfos  = expenseInfoRepository.findByClientIdAndUserIdAndStatusId(clientId, userId, statuses);
//			
		for (ExpenseInfo expenses : expenseInfos) {
			expenseInfoDTO = modelMapper.map(expenses, ExpenseInfoDTO.class);

//				expenseInfoDTO.setProjectCode(StringUtils.EMPTY);
//				if (null != expenses.getProjectId()) {
//				Optional<ProjectInfo> projectInfo  = projectInfoRepository.findById(expenses.getProjectId());
//				if (projectInfo.isPresent()) {
//					expenseInfoDTO.setProjectCode(projectInfo.get().getProjectCode());
//
//				}
//			}

			expenseInfoDTO.setProjectDesc(StringUtils.EMPTY);
			if (null != expenses.getProjectId()) {
				Optional<ProjectInfo> projectInfo = projectInfoRepository.findById(expenses.getProjectId());
				if (projectInfo.isPresent()) {
					expenseInfoDTO.setProjectDesc(projectInfo.get().getProjectName());

				}
			}

//				expenseInfoDTO.setOrgUnitCode(StringUtils.EMPTY);
//				if (null != expenses.getOrgUnitId()) {
//				Optional<OrgInfo>  orgInfo = orgInfoRepository.findById(expenses.getOrgUnitId());
//				if (orgInfo.isPresent()) {
//					expenseInfoDTO.setOrgUnitCode(orgInfo.get().getOrgNodeCode());
//
//				}
//			}

			expenseInfoDTO.setOrgUnitDesc(StringUtils.EMPTY);
			if (null != expenses.getOrgUnitId()) {
				Optional<OrgInfo> orgInfo = orgInfoRepository.findById(expenses.getOrgUnitId());
				if (orgInfo.isPresent()) {
					expenseInfoDTO.setOrgUnitDesc(orgInfo.get().getOrgNodeName());

				}
			}

			expenseInfoList.add(expenseInfoDTO);
	//	}
			}
		return expenseInfoList;
	}

	// draft Expenses

//	@Override
//	public List<ExpenseInfoDTO> findActiveExpensesByClientIdAndUserId(Long clientId, Long userId) {
//		
//		List<Long> actionTypes = new ArrayList<Long>();
//		actionTypes.add(actionTypeByName.get(TMSConstants.EXPENSE_SUBMITTED));
//		actionTypes.add(actionTypeByName.get(TMSConstants.EXPENSE_APPROVED));
//		actionTypes.add(actionTypeByName.get(TMSConstants.EXPENSE_REJECTED));
//
//		
//		List<ExpenseInfo> expenseRequestList = expenseInfoRepository
//				.findByClientIdAndUserIdAndActionTypeNotIn(clientId, userId, actionTypes);
//		return getActiveExpensesDetails(expenseRequestList);
//	}
//	

	@Override
	@Transactional
	public List<ExpenseInfoDTO> findActiveExpensesByClientIdAndUserId(Long clientId, Long userId) {

		short notDeleted = 0;
		List<ExpenseInfoDTO> expenseInfoList = new ArrayList<>();
		ExpenseInfoDTO expenseInfoDTO = null;

		long statusId = statusMasterRepository.findByClientIdAndStatusTypeAndStatusCodeAndIsDelete(clientId,
				TMSConstants.EXPENSE, TMSConstants.EXPENSE_DRAFT, (short) 0).getId();
		List<ExpenseInfo> expInfo = expenseInfoRepository.findByClientIdAndUserIdAndStatusIdAndIsDelete(clientId,
				userId, statusId, notDeleted);
		for (ExpenseInfo expensess : expInfo) {
			expenseInfoDTO = modelMapper.map(expensess, ExpenseInfoDTO.class);
			expenseInfoDTO.setStatusId(expensess.getStatusId());

//				expenseInfoDTO.setProjectCode(StringUtils.EMPTY);
//				if (null != expensess.getProjectId()) {
//				Optional<ProjectInfo> projectInfo  = projectInfoRepository.findById(expensess.getProjectId());
//				if (projectInfo.isPresent()) {
//					expenseInfoDTO.setProjectCode(projectInfo.get().getProjectCode());
//
//				}
//			}

			expenseInfoDTO.setProjectDesc(StringUtils.EMPTY);
			if (null != expensess.getProjectId()) {
				Optional<ProjectInfo> projectInfo = projectInfoRepository.findById(expensess.getProjectId());
				if (projectInfo.isPresent()) {
					expenseInfoDTO.setProjectDesc(projectInfo.get().getProjectName());

				}
			}

//				expenseInfoDTO.setOrgUnitCode(StringUtils.EMPTY);
//				if (null != expensess.getOrgUnitId()) {
//				Optional<OrgInfo>  orgInfo = orgInfoRepository.findById(expensess.getOrgUnitId());
//				if (orgInfo.isPresent()) {
//					expenseInfoDTO.setOrgUnitCode(orgInfo.get().getOrgNodeCode());
//
//				}
//			}

			expenseInfoDTO.setOrgUnitDesc(StringUtils.EMPTY);
			if (null != expensess.getOrgUnitId()) {
				Optional<OrgInfo> orgInfo = orgInfoRepository.findById(expensess.getOrgUnitId());
				if (orgInfo.isPresent()) {
					expenseInfoDTO.setOrgUnitDesc(orgInfo.get().getOrgNodeName());

				}
			}

			expenseInfoList.add(expenseInfoDTO);
		}
		return expenseInfoList;
	}

	@Override
	public List<ExpenseMasterDTO> getAllExpenseMasters() {
		short notDeleted = 0;
		List<ExpenseTypeMaster> expenseTypeMasters = expenseTypeMasterRepository.findByIsDelete(notDeleted);
		List<ExpenseMasterDTO> expenseMasterDTOs = new ArrayList<ExpenseMasterDTO>();
		ExpenseMasterDTO expenseMasterDTO = null;
		for (ExpenseTypeMaster expenseTypeMaster : expenseTypeMasters) {
			expenseMasterDTO = modelMapper.map(expenseTypeMaster, ExpenseMasterDTO.class);
			expenseMasterDTOs.add(expenseMasterDTO);
		}
		return expenseMasterDTOs;
	}

	@Override
	public List<ExpenseEntriesDTO> getAllExpenseEntries() {
		short notDeleted = 0;
		List<ExpenseEntries> expenseEntries = expenseEntriesRepository.findByIsDelete(notDeleted);
		List<ExpenseEntriesDTO> expenseEntriesDTOs = new ArrayList<ExpenseEntriesDTO>();
		ExpenseEntriesDTO expenseEntriesDTO = null;
		for (ExpenseEntries expenseEntry : expenseEntries) {
			expenseEntriesDTO = modelMapper.map(expenseEntry, ExpenseEntriesDTO.class);
			expenseEntriesDTOs.add(expenseEntriesDTO);
		}
		return expenseEntriesDTOs;
	}

	@Override
	public List<ExpenseEntriesDTO> findByExpId(Long expId) {

		short notDeleted = 0;
		List<ExpenseEntriesDTO> expenseEntriesDTOs = new ArrayList<>();
		List<ExpenseEntries> expenseEntries = expenseEntriesRepository.findByExpIdAndIsDelete(expId, notDeleted);
		ExpenseEntriesDTO expenseEntriDTO = null;
		for (ExpenseEntries expenseEntry : expenseEntries) {
			expenseEntriDTO = modelMapper.map(expenseEntry, ExpenseEntriesDTO.class);

			expenseEntriDTO.setExpenseType(StringUtils.EMPTY);
			if (null != expenseEntry.getExpType()) {
				Optional<ExpenseTypeMaster> exps = expenseTypeMasterRepository.findById(expenseEntry.getExpType());
				if (exps.isPresent()) {
					expenseEntriDTO.setExpenseType(exps.get().getExpenseType());

				}
			}

			expenseEntriDTO.setExpCountryCode(StringUtils.EMPTY);
			if (null != expenseEntry.getExpCountry()) {
				Optional<CountryMaster> exps = countryMasterRepository.findById(expenseEntry.getExpCountry());
				if (exps.isPresent()) {
					expenseEntriDTO.setExpCountryCode(exps.get().getCountryCode());

				}
			}

			expenseEntriDTO.setExpCountryName(StringUtils.EMPTY);
			if (null != expenseEntry.getExpCountry()) {
				Optional<CountryMaster> exps = countryMasterRepository.findById(expenseEntry.getExpCountry());
				if (exps.isPresent()) {
					expenseEntriDTO.setExpCountryName(exps.get().getCountryName());

				}
			}

			expenseEntriDTO.setExpCurrencyType(StringUtils.EMPTY);
			if (null != expenseEntry.getExpCurrency()) {
				Optional<CurrencyMaster> exps = currencyMasterRepository.findById(expenseEntry.getExpCurrency());
				if (exps.isPresent()) {
					expenseEntriDTO.setExpCurrencyType(exps.get().getCurrencyName());

				}
			}

			expenseEntriDTO.setExpCurrencyCode(StringUtils.EMPTY);
			if (null != expenseEntry.getExpCurrency()) {
				Optional<CurrencyMaster> exps = currencyMasterRepository.findById(expenseEntry.getExpCurrency());
				if (exps.isPresent()) {
					expenseEntriDTO.setExpCurrencyCode(exps.get().getCurrencyCode());

				}
			}
			expenseEntriesDTOs.add(expenseEntriDTO);
		}
		return expenseEntriesDTOs;
	}

	@Override
	public void addExpenseEntries(@Valid List<ExpenseEntries> expenseEntries) throws Exception {
		expenseEntriesRepository.saveAll(expenseEntries);
	}

	public void editExpenseEntries(@Valid ExpenseEntriesDTO expenseEntriesDTO) throws Exception {

		Optional<ExpenseEntries> expenseEntriesEntity = expenseEntriesRepository.findById(expenseEntriesDTO.getId());
		if (expenseEntriesEntity.isPresent()) {
			ExpenseEntries expenseinfo = modelMapper.map(expenseEntriesDTO, ExpenseEntries.class);
			expenseEntriesRepository.save(expenseinfo);

		} else {
			throw new Exception("expenseEntries not found with the provided ID : " + expenseEntriesDTO.getId());
		}

	}

	@Override
	@Transactional
	public void editExpenseEntriesById(@Valid List<ExpenseEntriesDTO> expenseEntriesDTO) throws Exception {

		for (ExpenseEntriesDTO updateRequest : expenseEntriesDTO) {
			editExpenseEntries(updateRequest);
		}
	}

	public void deleteExpenseEntriesById(Long id, String modifiedBy) throws Exception {

		Optional<ExpenseEntries> expenseEntries = expenseEntriesRepository.findById(id);
		if (expenseEntries.isPresent()) {
			ExpenseEntries expenseEntriesEntitiy = expenseEntries.get();
			short delete = 1;
			expenseEntriesEntitiy.setIsDelete(delete);
			expenseEntriesEntitiy.setModifiedBy(modifiedBy);
			expenseEntriesRepository.save(expenseEntriesEntitiy);

		} else {
			log.info("No ExpenseEntries found with the provided ID{} in the DB", id);
			throw new Exception("No ExpenseEntries found with the provided ID in the DB :" + id);
		}

	}

	@Override
	public void addExpenseInfo(@Valid ExpenseInfo expenseInfo) throws Exception {
		expenseInfoRepository.save(expenseInfo);
	}

	@Override
	public List<ExpenseInfo> addExpenseInfo(List<ExpenseInfo> expenseInfo) throws Exception {

		//List<ExpenseInfo> expenseInfoList = expenseInfoRepository.saveAll(expenseInfo); //prev concept		
		// BEING TO RESOLVE ORGUINTID ISSUE WITH ZERO VALUE
		List<ExpenseInfo> expenseInfoLst = new ArrayList<ExpenseInfo>();
		for(ExpenseInfo expenseInfoData:expenseInfo) {
			if(expenseInfoData.getOrgUnitId()!=null && expenseInfoData.getOrgUnitId()==0) {
				expenseInfoData.setOrgUnitId(null);
			}
			expenseInfoLst.add(expenseInfoData);			
		}
		//END IMPL
		// submitExpenses(expenseInfoList);
		List<ExpenseInfo> expenseInfoList = expenseInfoRepository.saveAll(expenseInfoLst);
		return expenseInfoList;
	}

	@Override
	public void editExpenseInfoById(@Valid ExpenseInfoDTO expenseInfoDTO) throws Exception {

		Optional<ExpenseInfo> expenseInfoEntity = expenseInfoRepository.findById(expenseInfoDTO.getId());
		if (expenseInfoEntity.isPresent()) {
			ExpenseInfo expenseinfo = modelMapper.map(expenseInfoDTO, ExpenseInfo.class);
			expenseInfoRepository.save(expenseinfo);

		} else {
			throw new Exception("ExpenseInfo not found with the provided ID : " + expenseInfoDTO.getId());
		}

	}

	public void editExpenseInfos(@Valid ExpenseInfoDTO expenseInfoDTO) throws Exception {

		Optional<ExpenseInfo> expenseInfoEntity = expenseInfoRepository.findById(expenseInfoDTO.getId());
		if (expenseInfoEntity.isPresent()) {
			ExpenseInfo expenseinfo = modelMapper.map(expenseInfoDTO, ExpenseInfo.class);
			expenseInfoRepository.save(expenseinfo);

		} else {
			throw new Exception("ExpenseInfo not found with the provided ID : " + expenseInfoDTO.getId());
		}

	}

	@Override
	@Transactional
	public void editExpenseInfoByIds(@Valid List<ExpenseInfoDTO> expenseInfoDTO) throws Exception {

		for (ExpenseInfoDTO updateRequest : expenseInfoDTO) {
			editExpenseInfos(updateRequest);
		}
	}

	public void deleteExpenseInfoById(Long id, String modifiedBy) throws Exception {

		Optional<ExpenseInfo> expenseInfo = expenseInfoRepository.findById(id);
		if (expenseInfo.isPresent()) {
			ExpenseInfo expenseInfoEntitiy = expenseInfo.get();
			short delete = 1;
			expenseInfoEntitiy.setIsDelete(delete);
			expenseInfoEntitiy.setModifiedBy(modifiedBy);
			expenseInfoRepository.save(expenseInfoEntitiy);

		} else {
			log.info("No ExpenseInfo found with the provided ID{} in the DB", id);
			throw new Exception("No ExpenseInfo found with the provided ID in the DB :" + id);
		}

	}

	@Override
	public boolean submitExpenses(List<ExpenseInfo> expenseInfoList) throws Exception {
		
		List<ExpenseWorkflowRequest> expenseWorkflowRequestList = new ArrayList<>();
		try {
			ExpenseWorkflowRequest expenseWorkflowRequest = null;
			for (ExpenseInfo expenseInfo : expenseInfoList) {
				
				if(expenseInfo.getOrgUnitId()!=null && expenseInfo.getOrgUnitId()==0) {
					expenseInfo.setOrgUnitId(null);
				}
				
				/*
				 * ExpenseWorkflowRequest expenseWorkflowRequest = expenseWorkflowRepository
				 * .findByExpId(expenseInfo.getId());
				 */				
				expenseWorkflowRequestList = expenseWorkflowRepository
						.findByExpIdOrderByStepId(expenseInfo.getId());					
				ExpenseInfo expenseInfoEntitiy = expenseInfoRepository.findById(expenseInfo.getId()).get();
				expenseInfoEntitiy.setExpPostingDate(new Date());
				 
				Long statusId = statusMasterRepository
						.findByClientIdAndStatusTypeAndStatusCodeAndIsDelete(expenseInfo.getClientId(),
								TMSConstants.EXPENSE, TMSConstants.EXPENSE_SUBMITTED, (short) 0).getId();
				
				expenseInfo.setStatusId(statusId);
				expenseInfo.setExpPostingDate(new Date());
				expenseInfoRepository.save(expenseInfo);
				
				///IMPLEMENTATION FOR MULTI LEVEL APPROVALBEING
				UserInfo userInfo = userInfoRepository.findByUserId(expenseInfo.getUserId());						
				List<ExpTemplateSteps>  expTemplateStepsList = null;
				if(userInfo.getExpTemplateId()!=null) {
					expTemplateStepsList = expTemplateStepsRepository.findByTemplateIdOrderByStepId(userInfo.getExpTemplateId());
				}
					
				///PREV BEING
				if (expenseWorkflowRequestList.isEmpty()) {
					log.info("Creating new expense WFR objects...");					
					//BEING ITERATION FOR  EXPtEMPLATESTEPSREPOSITORY					
					expenseWorkflowRequest = new ExpenseWorkflowRequest();					
					expenseWorkflowRequest.setClientId(expenseInfo.getClientId());
					expenseWorkflowRequest.setProjectId(expenseInfo.getProjectId());
					expenseWorkflowRequest.setOrgUnitId(expenseInfo.getOrgUnitId());
					expenseWorkflowRequest.setExpId(expenseInfo.getId());
					expenseWorkflowRequest.setRequestorId(expenseInfo.getUserId());
					expenseWorkflowRequest.setRequestDate(new Date());
					long approverId = getApproverId(expenseInfo);//projectManagerId
					
					if(approverId == expenseInfo.getUserId()) {
						expenseWorkflowRequest.setApproverId(userInfo.getReportingMgr());
					}else {
						expenseWorkflowRequest.setApproverId(approverId);
					}
					expenseWorkflowRequest.setStateType(stateByName.get(TMSConstants.STATE_START));
					expenseWorkflowRequest.setActionType(actionTypeByName.get(TMSConstants.ACTION_NULL));
					
					
					//SET STEPID AND CURRENT STEP
					expenseWorkflowRequest.setStepId(1L);
					expenseWorkflowRequest.setCurrentStep(1L);					
					expenseWorkflowRequestList.add(expenseWorkflowRequest);
					
					///IMPLEMENT FOR MULTILEVEL APPROVAL
					for(ExpTemplateSteps expTemplateStep:expTemplateStepsList) {
						
						float chkAmt = expTemplateStep.getCheckAmt();
						float expAmt = expenseInfo.getExpAmount();
						Double approvalAmt = expTemplateStep.getApproverAmount();						
						expenseWorkflowRequest = new ExpenseWorkflowRequest();					
						expenseWorkflowRequest.setClientId(expenseInfo.getClientId());
						expenseWorkflowRequest.setProjectId(expenseInfo.getProjectId());
						//to set cost center id
						expenseWorkflowRequest.setOrgUnitId(expenseInfo.getOrgUnitId());
						expenseWorkflowRequest.setExpId(expenseInfo.getId());
						expenseWorkflowRequest.setRequestorId(expenseInfo.getUserId());
						expenseWorkflowRequest.setRequestDate(new Date());						
						expenseWorkflowRequest.setStepId(expTemplateStep.getStepId()+1);
						expenseWorkflowRequest.setCurrentStep(0L);						
						expenseWorkflowRequest.setStateType(stateByName.get(TMSConstants.STATE_OPEN));
						///IN CASE REQUESTER  AND APPROVER IS SAME PERSON
						if(expTemplateStep.getApproverId() == expenseInfo.getUserId()) {
							expenseWorkflowRequest.setApproverId(userInfo.getReportingMgr());
						}else {
							expenseWorkflowRequest.setApproverId(expTemplateStep.getApproverId());
						}						
						expenseWorkflowRequestList.add(expenseWorkflowRequest);							
						if((chkAmt ==1 || chkAmt ==1.0) &&( (approvalAmt!=null && (expAmt <= approvalAmt)) || approvalAmt ==null) ) {
							///STOP MULTILEVEL APPROVAL  IN CASE EXP AMOUNT IS LESSER THEN APPROVER'S APPROVAL AMOUNT
							break;
						}
												
					}					
					///END ITERATION		
					
				} else {
					/// existing records came for revision					
					log.info("Existing wfrequests came for revision..");
					/*for(ExpTemplateSteps expTemplateStep:expTemplateStepsList) {
						expenseWorkflowRequest.setStateType(stateByName.get(TMSConstants.STATE_START));
						expenseWorkflowRequest.setActionType(actionTypeByName.get(TMSConstants.ACTION_NULL));
					}*/		
					
					for(ExpenseWorkflowRequest oldWorkflowRequest: expenseWorkflowRequestList ) {
						
						oldWorkflowRequest.setActionType(actionTypeByName.get(TMSConstants.ACTION_NULL));
						oldWorkflowRequest.setRequestDate(new Date());
						oldWorkflowRequest.setActionDate(null);
					}		
					
				}
				///PREV END					
				///IMPLEMENTATION END				
				expenseInfoRepository.save(expenseInfo);

			}
			
			expenseWorkflowRepository.saveAll(expenseWorkflowRequestList);

			log.info("Expense WFRequests are submited...");
		} catch (Exception e) {
			log.error("Exception occured while populating workflow request", e);
			return false;
		}

		return true;

	}

	private long getApproverId(ExpenseInfo expenseInfo) {
		Long approverId = 0L;
		try {
			if (expenseInfo.getProjectId() != null && expenseInfo.getProjectId() != 0) {
				approverId = projectInfoRepository.findById(expenseInfo.getProjectId()).get().getProjectManager();

			} else if (expenseInfo.getOrgUnitId() != null && expenseInfo.getOrgUnitId() != 0) {
				approverId = orgInfoRepository.findById(expenseInfo.getOrgUnitId()).get().getOrgManager();
			}
			
			if(expenseInfo.getUserId().equals(approverId)) {
				
				UserInfo userInfo = userInfoRepository.findById(expenseInfo.getUserId()).get();
				if(userInfo.getReportingMgr() != null) {
					
					log.info("SubmiterId {} is same as ApproverId {}, hence switiching to RM {}",
							expenseInfo.getUserId(),approverId,userInfo.getReportingMgr());
					approverId = userInfo.getReportingMgr();
				}
				
			}
		} catch (NoSuchElementException e) {
			log.error("Exception occured while fetching approverId", e);
		}

		return approverId;
	}

	@Override
	public List<ExpenseTypeMaster> getAllExpenseMastersByClient(Long clientId) {
		//set isDelete 0 due to not required delete record
		short notDeleted = 0;
		List<ExpenseTypeMaster> expenseTypeMasterDetails = expenseTypeMasterRepository.findByClientIdAndIsDelete(clientId, notDeleted);
		return expenseTypeMasterDetails;
	}

	@Override
	public boolean updateFileDetails(String key) throws Exception {
		
		String tokens[] = key.split("/");
		Long expenseEntryId = null;
		String fileName = StringUtils.EMPTY;
		String filePath = StringUtils.EMPTY;
		if (tokens != null && tokens.length > 0) {

			fileName = tokens[tokens.length - 1];
			expenseEntryId = Long.parseLong(tokens[tokens.length - 2]);
			filePath= key.substring(0,key.lastIndexOf("/")+1);
		}
		
		log.info("fileName: "+fileName);
		log.info("expenseEntryId: "+expenseEntryId);
		log.info("filePath: "+filePath);
		if(expenseEntryId != null) {
			ExpenseEntries expenseEntries = expenseEntriesRepository.findById(expenseEntryId).get();
			expenseEntries.setFileName(fileName);
			expenseEntries.setFilePath(filePath);
			expenseEntriesRepository.save(expenseEntries);
		}else {
			log.error("Invalid key-path with expense entryId: "+key);
			throw new Exception ("Invalid key with expense entryId: "+key);
		}
		
		log.info("Expense entry file details updated successfully !! {}",key);
		
		return true;
	}
	
}
