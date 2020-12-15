package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zieta.tms.common.TMSConstants;
import com.zieta.tms.dto.ExpenseEntriesDTO;
import com.zieta.tms.dto.ExpenseInfoDTO;
import com.zieta.tms.dto.ExpenseMasterDTO;
import com.zieta.tms.model.CountryMaster;
import com.zieta.tms.model.CurrencyMaster;
import com.zieta.tms.model.ExpenseEntries;
import com.zieta.tms.model.ExpenseInfo;
import com.zieta.tms.model.OrgInfo;
import com.zieta.tms.model.ExpenseTypeMaster;
import com.zieta.tms.model.ExpenseWorkflowRequest;
import com.zieta.tms.model.ProjectInfo;
import com.zieta.tms.model.StatusMaster;
import com.zieta.tms.repository.CountryMasterRepository;
import com.zieta.tms.repository.CurrencyMasterRepository;
import com.zieta.tms.repository.ExpenseEntriesRepository;
import com.zieta.tms.repository.ExpenseInfoRepository;
import com.zieta.tms.repository.ExpenseTypeMasterRepository;
import com.zieta.tms.repository.ExpenseWorkflowRepository;
import com.zieta.tms.repository.OrgInfoRepository;
import com.zieta.tms.repository.ProjectInfoRepository;
import com.zieta.tms.repository.StatusMasterRepository;
import com.zieta.tms.request.UpdateTimesheetByIdRequest;
import com.zieta.tms.response.ExpenseWFRDetailsForApprover;
import com.zieta.tms.service.ExpenseService;

import lombok.extern.slf4j.Slf4j;

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
	StatusMasterRepository statusMasterRepository;

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
	public List<ExpenseInfoDTO> findByClientIdAndUserId(Long clientId, Long userId) {

		short notDeleted = 0;
		List<ExpenseInfoDTO> expenseInfoList = new ArrayList<>();
		List<ExpenseInfo> expenseInfos = expenseInfoRepository.findByClientIdAndUserIdAndIsDelete(clientId, userId,
				notDeleted);
		ExpenseInfoDTO expenseInfoDTO = null;

//			List<StatusMaster> statusId =  statusMasterRepository
//					.findByClientIdAndStatusTypeAndStatusCodeNotAndIsDelete(clientId,
//							TMSConstants.EXPENSE, TMSConstants.EXPENSE_DRAFT, (short) 0);
//		//	System.out.println(+statusId);
//			List<ExpenseInfo> expenseInfos  = expenseInfoRepository.findByClientIdAndUserIdAndStatusIdInAndIsDelete(clientId, userId, statusId, notDeleted);
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

		List<ExpenseInfo> expenseInfoList = expenseInfoRepository.saveAll(expenseInfo);
//		 submitExpenses(expenseInfoList);
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
			for (ExpenseInfo expenseInfo : expenseInfoList) {

				ExpenseWorkflowRequest expenseWorkflowRequest = expenseWorkflowRepository
						.findByExpId(expenseInfo.getId());
				ExpenseInfo expenseInfoEntitiy = expenseInfoRepository.findById(expenseInfo.getId()).get();
				expenseInfoEntitiy.setExpPostingDate(new Date());

				Long statusId = statusMasterRepository
						.findByClientIdAndStatusTypeAndStatusCodeAndIsDelete(expenseInfo.getClientId(),
								TMSConstants.EXPENSE, TMSConstants.EXPENSE_SUBMITTED, (short) 0)
						.getId();
				expenseInfo.setStatusId(statusId);

				if (expenseWorkflowRequest == null) {
					log.info("Creating new expense WFR objects...");
					expenseWorkflowRequest = new ExpenseWorkflowRequest();
					expenseWorkflowRequest.setClientId(expenseInfo.getClientId());
					expenseWorkflowRequest.setProjectId(expenseInfo.getProjectId());
					expenseWorkflowRequest.setOrgUnitId(expenseInfo.getOrgUnitId());
					expenseWorkflowRequest.setExpId(expenseInfo.getId());
					expenseWorkflowRequest.setRequestorId(expenseInfo.getUserId());
					expenseWorkflowRequest.setRequestDate(new Date());
					long approverId = getApproverId(expenseInfo);
					expenseWorkflowRequest.setApproverId(approverId);
					expenseWorkflowRequest.setStateType(stateByName.get(TMSConstants.STATE_START));
					expenseWorkflowRequest.setActionType(actionTypeByName.get(TMSConstants.ACTION_NULL));
					expenseWorkflowRequestList.add(expenseWorkflowRequest);
				} else {
					// existing records came for revision
					log.info("Existing wfrequests came for revision..");
					expenseWorkflowRequest.setStateType(stateByName.get(TMSConstants.STATE_START));
					expenseWorkflowRequest.setActionType(actionTypeByName.get(TMSConstants.ACTION_NULL));
				}
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
		} catch (NoSuchElementException e) {
			log.error("Exception occured while fetching approverId", e);
		}

		return approverId;
	}

	@Override
	public List<ExpenseTypeMaster> getAllExpenseMastersByClient(Long clientId) {
		// TODO Auto-generated method stub
		List<ExpenseTypeMaster> expenseTypeMasterDetails = expenseTypeMasterRepository
				.findByClientId(clientId);
		return expenseTypeMasterDetails;
	}

}
