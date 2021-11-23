package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.util.Locale;
import java.time.LocalDate;
import java.text.SimpleDateFormat;  
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.zieta.tms.common.TMSConstants;
import com.zieta.tms.dto.DateRange;
import com.zieta.tms.dto.WorkflowDTO;
import com.zieta.tms.model.ActivityMaster;
import com.zieta.tms.model.ProcessSteps;
import com.zieta.tms.model.TSInfo;
import com.zieta.tms.dto.TSInfoDTO;
import com.zieta.tms.model.TSTimeEntries;
import com.zieta.tms.model.TSWorkflow;
import com.zieta.tms.model.TaskInfo;
import com.zieta.tms.model.ProjectInfo;
import com.zieta.tms.model.TimeType;
import com.zieta.tms.model.UserConfig;
import com.zieta.tms.model.WorkflowRequest;
import com.zieta.tms.repository.ActivityMasterRepository;
import com.zieta.tms.repository.ClientInfoRepository;
import com.zieta.tms.repository.ProcessStepsRepository;
import com.zieta.tms.repository.ProjectInfoRepository;
import com.zieta.tms.repository.StatusMasterRepository;
import com.zieta.tms.repository.TSInfoRepository;
import com.zieta.tms.repository.TSTimeEntriesRepository;
import com.zieta.tms.repository.TaskInfoRepository;
import com.zieta.tms.repository.TimeTypeRepository;
import com.zieta.tms.repository.UserConfigRepository;
import com.zieta.tms.repository.UserInfoRepository;
import com.zieta.tms.repository.WorkflowRepository;
import com.zieta.tms.repository.WorkflowRequestRepository;
import com.zieta.tms.request.TimeEntriesByTsIdRequest;
import com.zieta.tms.request.UpdateTimesheetByIdRequest;
import com.zieta.tms.response.TSInfoModel;
import com.zieta.tms.response.TimeEntriesByTimesheetIDResponse;
import com.zieta.tms.service.TimeSheetService;
import com.zieta.tms.util.TSMUtil;

import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
@Service
@Transactional
@Slf4j
public class TimeSheetServiceImpl implements TimeSheetService {
	
	@Autowired
	TSInfoRepository tSInfoRepository;
	
	@Autowired
	UserConfigRepository userConfigRepository;
	
	@Autowired
	ActivityMasterRepository  activityMasterRepository;
	
	@Autowired
	TaskInfoRepository taskInfoRepository;
	
	@Autowired
	ProjectInfoRepository  projectInfoRepository;
	
	@Autowired
	TSTimeEntriesRepository tstimeentriesRepository;
	
	@Autowired
	TimeTypeRepository timeTypeRepository;
	
	@Autowired
	ClientInfoRepository  clientInfoRepository;
	
	@Autowired
	WorkflowRepository workflowRepository;
	
	@Autowired
	WorkflowRequestRepository workflowRequestRepository;
	
	@Autowired
	ProcessStepsRepository processStepsRepository;
	
	@Autowired
	StatusMasterRepository statusMasterRepository;
	
	@Autowired
	UserInfoRepository userInfoRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	@Qualifier("stateByName")
	Map<String, Long> stateByName;
	
	@Autowired
	@Qualifier("actionTypeByName")
	Map<String, Long> actionTypeByName;
	
	
	
	/**
	 *  This methods returns ts_info entries and its associated tstimeentries based on the date range of ts_date column 
	 *  and filters based on the userid and clientid
	 */
	@Override
	public List<TSInfoModel> getTimeEntriesByUserDates(Long clientId, Long userId, Date startDate, Date endDate) {
		short notDeleted=0;
		List<TSInfoModel> tsInfoModelList = new ArrayList<TSInfoModel>();		
		
		DateRange dateRange = TSMUtil.getFilledDateRange(startDate, endDate, false);		
		
		List<TSInfo> tsInfoList = tSInfoRepository.findByClientIdAndUserIdAndIsDeleteAndTsDateBetweenOrderByTaskActivityIdAscIdAsc(clientId, 
				userId, notDeleted, dateRange.getStartDate(), dateRange.getEndDate());		
		
		for (TSInfo tsInfo : tsInfoList) {
			
			TSInfoDTO tsInfoDTO = new TSInfoDTO();
			//for (TSInfo tsInfo : tsInfoList) {
			boolean isProjectTaskActivityDeleted = false;
			TSInfoModel taskInfoModel = new TSInfoModel();
			
			String strDate = null;	
			String sbmtDate = null;
			//NEW IMPLEMENTION TO SET DIFFERENT DATE TYPE
			if(tsInfo.getTsDate()!=null)
				strDate = new SimpleDateFormat("yyyy-MM-dd").format(tsInfo.getTsDate());	
			 
			 if(tsInfo.getSubmitDate()!=null)
				 sbmtDate = new SimpleDateFormat("yyyy-MM-dd").format(tsInfo.getSubmitDate());			
			
			tsInfoDTO.setCreatedBy(tsInfo.getCreatedBy());
			tsInfoDTO.setModifiedBy(tsInfo.getModifiedBy());
			tsInfoDTO.setIsDelete(tsInfo.getIsDelete());
			tsInfoDTO.setId(tsInfo.getId());//clientId
			tsInfoDTO.setClientId(tsInfo.getClientId());
			tsInfoDTO.setProjectId(tsInfo.getProjectId());
			tsInfoDTO.setActivityId(tsInfo.getActivityId());
			tsInfoDTO.setTaskActivityId(tsInfo.getTaskActivityId());
			tsInfoDTO.setUserId(tsInfo.getUserId());
			tsInfoDTO.setTaskId(tsInfo.getTaskId());
			tsInfoDTO.setStatusId(tsInfo.getStatusId());
			tsInfoDTO.setPlannedActivity(tsInfo.isPlannedActivity());
			tsInfoDTO.setTsDate(strDate);//Change the format
			tsInfoDTO.setSubmitDate(sbmtDate);
			if(tsInfo.getTsTotalSubmittedTime()!=null)
				tsInfoDTO.setTsTotalSubmittedTime(tsInfo.getTsTotalSubmittedTime());
			
			if(tsInfo.getTsTotalApprovedTime()!=null)
				tsInfoDTO.setTsTotalApprovedTime(tsInfo.getTsTotalApprovedTime());
			
			taskInfoModel.setTsInfo(tsInfoDTO);
			//END NEW IMPLEMENTATION
			//taskInfoModel.setTsInfo(tsInfo);//PREV CONCEPT
			List<TSTimeEntries> timeEntries = tstimeentriesRepository.findByTsIdAndIsDelete(tsInfo.getId(), notDeleted);
			
			for (TSTimeEntries timetp : timeEntries) {
				taskInfoModel.setTimeTypeDesc(timeTypeRepository.findById(timetp.getTimeType()).get().getTimeType());
			}
			taskInfoModel.setTimeEntries(timeEntries);			
			
			if(tsInfo.getActivityId() != null && tsInfo.getActivityId() !=0) {
				ActivityMaster activityEntity = activityMasterRepository.findById(tsInfo.getActivityId()).get();
					taskInfoModel.setActivityDescription(activityEntity.getActivityDesc());
					taskInfoModel.setIsDeleteActivity(activityEntity.getIsDelete());
					taskInfoModel.setActivityStatus(activityEntity.isActive());
					
			}else {
				taskInfoModel.setActivityDescription(StringUtils.EMPTY);
			}
			
			if (tsInfo.getProjectId() != null && tsInfo.getProjectId() != 0) {
				ProjectInfo projectEntity = projectInfoRepository.findById(tsInfo.getProjectId()).get();
				taskInfoModel.setProjectDescription(projectEntity.getProjectName());
				taskInfoModel.setIsDeleteProject(projectEntity.getIsDelete());
			}else {
				taskInfoModel.setProjectDescription(StringUtils.EMPTY);
			}
			
			if(tsInfo.getTaskId() !=null && tsInfo.getTaskId() !=0) {
				TaskInfo taskInfoEntity =taskInfoRepository.findById(tsInfo.getTaskId()).get();
					taskInfoModel.setTaskDescription(taskInfoEntity.getTaskDescription());
					taskInfoModel.setIsDeleteTask(taskInfoEntity.getIsDelete());
					
			}else {
				taskInfoModel.setTaskDescription(StringUtils.EMPTY);
			}
			
			taskInfoModel.setClientCode(clientInfoRepository.findById(tsInfo.getClientId()).get().getClientCode());
			taskInfoModel.setClientDescription(clientInfoRepository.findById(tsInfo.getClientId()).get().getClientName());
			
			tsInfoModelList.add(taskInfoModel);
			
		}

		return tsInfoModelList;
	}
	
	
	@Transactional
	public List<TSInfo> addTimeSheet(@Valid List<TSInfo> tsInfoList) {

		
		for (TSInfo tsInfo : tsInfoList) {
			
			//Setting the statusId which is marked as default in the DB for the corresponding the statustype, doing
			// as per change request.
			Long statuId = statusMasterRepository.findByClientIdAndStatusTypeAndIsDefaultAndIsDelete(tsInfo.getClientId(),
					TMSConstants.TIMESHEET, Boolean.TRUE, (short)0).getId();
			tsInfo.setStatusId(statuId);
		}
		
		return tSInfoRepository.saveAll(tsInfoList);
	}
	
	
	@Transactional
	public boolean submitTimeSheet(@Valid List<TSInfo> tsInfoList) {
		// call to save workflow_request
		try {
			
				List<WorkflowRequest> workflowRequestList = new ArrayList<WorkflowRequest>();
				WorkflowRequest workflowRequest = null;
				for (TSInfo tsInfo : tsInfoList) {
	
					workflowRequestList = workflowRequestRepository.findByTsIdOrderByStepId(tsInfo.getId());
					Long statusId = statusMasterRepository
							.findByClientIdAndStatusTypeAndStatusCodeAndIsDelete(tsInfo.getClientId(),
									TMSConstants.TIMESHEET, TMSConstants.TIMESHEET_SUBMITTED, (short) 0)
							.getId();
					tsInfo.setSubmitDate(new Date());
					tsInfo.setStatusId(statusId);
					tSInfoRepository.save(tsInfo);
					if (workflowRequestList.isEmpty()) {
						// get the approverid from the process_step based on the clientId, projectId and taskId
						List<ProcessSteps> processStepsList = processStepsRepository
								.findByClientIdAndProjectIdAndProjectTaskIdOrderByStepId(tsInfo.getClientId(),
										tsInfo.getProjectId(), tsInfo.getTaskId());
						
						// approver id will be null only incase of template-id=1(no approval)
						if (processStepsList != null && processStepsList.size() == 1
								&& processStepsList.get(0).getApproverId() == null) {
	
							statusId = statusMasterRepository
									.findByClientIdAndStatusTypeAndStatusCodeAndIsDelete(tsInfo.getClientId(),
											TMSConstants.TIMESHEET, TMSConstants.TIMESHEET_APPROVED, (short) 0)
									.getId();
							// set the status as approved and there are no actions on the workflow, so move
							// to next TSInfo item.
							tsInfo.setStatusId(statusId);
							tsInfo.setTsTotalApprovedTime(tsInfo.getTsTotalSubmittedTime());
							tSInfoRepository.save(tsInfo);
							
							//change the status of the ts entriess also, when ts is approved.
							List<TSTimeEntries> tsEntiresList = tstimeentriesRepository.findByTsId(tsInfo.getId());
							
							Long tsEntryStatusId = statusMasterRepository
									.findByClientIdAndStatusTypeAndStatusCodeAndIsDelete(tsInfo.getClientId(),
											TMSConstants.TIMEENTRY, TMSConstants.TIMEENTRY_APPROVED, (short) 0)
									.getId();
							for (TSTimeEntries tsTimeEntries : tsEntiresList) {
								tsTimeEntries.setStatusId(tsEntryStatusId);
							}
							tstimeentriesRepository.saveAll(tsEntiresList);						
							
							continue;
						}
	
						for (int i = 0; i < processStepsList.size(); i++) {
	
							String approverIds[] = null;
							if (processStepsList.get(i).getApproverId() != null
									&& !processStepsList.get(i).getApproverId().isEmpty()) {
	
								approverIds = processStepsList.get(i).getApproverId().split("\\|");
							}
							for (int j = 0; j < approverIds.length; j++) {
	
								workflowRequest = new WorkflowRequest();
								workflowRequest.setStepId(processStepsList.get(i).getStepId());
								String approverId = approverIds[j];
								
								short isDelete = 0;
								UserConfig userConfig = userConfigRepository.findUserConfigByIdAndIsDelete(tsInfo.getUserId(), isDelete);
								
								
								
								if(tsInfo.getUserId().toString().equals(approverIds[j])) {
									//Long rmId = userInfoRepository.findById(tsInfo.getUserId()).get().getReportingMgr();//DUE TO TABLE SPLIT
									
									Long rmId = userConfig.getReportingMgr();
									if(rmId != null){
										approverId=  rmId.toString();
									}
								}
								buildWFRForSubmission(workflowRequest, tsInfo, approverId);
								if (processStepsList.get(i).getStepId() == 1) {
									// considering this as the first step
									workflowRequest.setCurrentStep(1L);
									
									workflowRequest.setStateType(stateByName.get(TMSConstants.STATE_START));
								}
								workflowRequestList.add(workflowRequest);
							}
	
						}
						workflowRequestRepository.saveAll(workflowRequestList);
					} else {
						// old workflow objects came for revision
						for(WorkflowRequest oldWorkflowRequest: workflowRequestList ) {
	
							oldWorkflowRequest.setActionType(actionTypeByName.get(TMSConstants.ACTION_NULL));
							oldWorkflowRequest.setRequestDate(new Date());
							oldWorkflowRequest.setActionDate(null);
							// rest of other attributes will be retained from the previous WFR phases.
							
	
							if (oldWorkflowRequest.getStepId() == 1) {
								// considering this as the first step
								oldWorkflowRequest.setCurrentStep(1L);
								oldWorkflowRequest.setStateType(stateByName.get(TMSConstants.STATE_START));
							} else {
								oldWorkflowRequest.setCurrentStep(0L);
								oldWorkflowRequest.setStateType(stateByName.get(TMSConstants.STATE_OPEN));
							}
	
	
						}
	
					}
	
				}

		} catch (Exception e) {
			log.error("Exception occured while populating workflow request", e);
			return false;
		}
		return true;
	}


	private void buildWFRForSubmission( WorkflowRequest workflowRequest, TSInfo tsInfo,
			String approverId) {
		workflowRequest.setActionType(actionTypeByName.get(TMSConstants.ACTION_NULL));
		workflowRequest.setApproverId(Long.valueOf(approverId));
		workflowRequest.setStateType(stateByName.get(TMSConstants.STATE_OPEN));

		workflowRequest.setClientId(tsInfo.getClientId());
		// there will be no action date while submitting the WF request
		workflowRequest.setActionDate(null);

		workflowRequest.setCurrentStep(0L);
		workflowRequest.setProjectId(tsInfo.getProjectId());
		workflowRequest.setProjectTaskId(tsInfo.getTaskId());
		workflowRequest.setRequestorId(tsInfo.getUserId());
		workflowRequest.setRequestDate(new Date());
		workflowRequest
				.setTemplateId(projectInfoRepository.findById(tsInfo.getProjectId()).get().getTemplateId());
		workflowRequest.setTsId(tsInfo.getId());
	}
	
	
	@Override
	public List<TimeEntriesByTimesheetIDResponse> getTimeEntriesByTsID(Long tsId) {
		short notDeleted=0;
		List<TSTimeEntries> timeentriesByTsidList = tstimeentriesRepository.findByTsIdAndIsDelete(tsId, notDeleted);
		List<TimeEntriesByTimesheetIDResponse> timeentriesBytsIdResponseList = new ArrayList<>();
		TimeEntriesByTimesheetIDResponse timeentriesByTsIdResponse = null;
		for (TSTimeEntries timeentriesByTsId : timeentriesByTsidList) {
			timeentriesByTsIdResponse = modelMapper.map(timeentriesByTsId, 
					TimeEntriesByTimesheetIDResponse.class);
			timeentriesBytsIdResponseList.add(timeentriesByTsIdResponse);
		}

		return timeentriesBytsIdResponseList;
		
	}
	
	@Override
	@Transactional
	public void addTimeEntriesByTsId(@Valid List<TimeEntriesByTsIdRequest> timeentriesbytsidRequestList) throws Exception {
			for (TimeEntriesByTsIdRequest timeEntriesByTsIdRequest : timeentriesbytsidRequestList) {
				TSTimeEntries tstimeentries = modelMapper.map(timeEntriesByTsIdRequest, TSTimeEntries.class);
				tstimeentriesRepository.save(tstimeentries);
			}

	}
	
	@Override
	public void updateTimeSheetById(@Valid UpdateTimesheetByIdRequest updatetimesheetRequest) throws Exception {
		Optional<TSInfo> TsInfoEntity = tSInfoRepository.findById(updatetimesheetRequest.getId());
		if (TsInfoEntity.isPresent()) {
			TSInfo tsinfo = modelMapper.map(updatetimesheetRequest, TSInfo.class);

			tSInfoRepository.save(tsinfo);
		}

		else {
			throw new Exception("Timesheet not found with the provided ID : " + updatetimesheetRequest.getId());
		}
	}	
	
	@Override
	@Transactional
	public void updateTimeSheetByIds(@Valid List<UpdateTimesheetByIdRequest> updatetimesheetRequest) throws Exception {

		for (UpdateTimesheetByIdRequest updateRequest : updatetimesheetRequest) {
			updateTimeSheetById(updateRequest);
		}
	}	
	
	public List<WorkflowDTO> getAllWorkflowDetails() {

		List<TSWorkflow> workflows = workflowRepository.findAll();
		List<WorkflowDTO> workflowDTOs = new ArrayList<WorkflowDTO>();
		WorkflowDTO workflowDTO = null;
		for (TSWorkflow workflow : workflows) {
			workflowDTO = modelMapper.map(workflow, WorkflowDTO.class);

			workflowDTOs.add(workflowDTO);
		}
		return workflowDTOs;

	}
	
	
	@Override
	public void updateTimeEntriesByID(@Valid TimeEntriesByTsIdRequest timeentriesByTsIdRequest) throws Exception {
		// todo remove find by and use existsbyid
		Optional<TSTimeEntries> tsTimeEntity = tstimeentriesRepository.findById(timeentriesByTsIdRequest.getId());
		if(tsTimeEntity.isPresent()) {			
			TSTimeEntries tstimeentry= modelMapper.map(timeentriesByTsIdRequest, TSTimeEntries.class);			
			tstimeentriesRepository.save(tstimeentry);
		}else{
			throw new Exception("Timeentry not found with the provided ID : "+timeentriesByTsIdRequest.getId());
		}
	}
	
	
	@Override
	@Transactional
	public void updateTimeEntriesByIds(@Valid List<TimeEntriesByTsIdRequest> timeentriesByTsIdRequest) throws Exception {
		
		for (TimeEntriesByTsIdRequest updateRequest : timeentriesByTsIdRequest) {
			updateTimeEntriesByID(updateRequest);
		}
		
		log.info("Updated time entries ");
	}
	
	
	@Override
public void deleteTimeEntriesById(Long id, String modifiedBy) throws Exception {
		
		Optional<TSTimeEntries> timeEntries = tstimeentriesRepository.findById(id);
		if (timeEntries.isPresent()) {
			TSTimeEntries timeEntriesEntitiy = timeEntries.get();
			short delete = 1;
			timeEntriesEntitiy.setIsDelete(delete);
			timeEntriesEntitiy.setModifiedBy(modifiedBy);
			tstimeentriesRepository.save(timeEntriesEntitiy);

		}else {
			log.info("No timeEntries found with the provided ID{} in the DB",id);
			throw new Exception("No timeEntries found with the provided ID in the DB :"+id);
		}
		
		
	}
	
	
	public void deleteTsInfoById(Long id, String modifiedBy) throws Exception {
		
		Optional<TSInfo> tsInfo = tSInfoRepository.findById(id);
		if (tsInfo.isPresent()) {
			TSInfo tsInfoEntitiy = tsInfo.get();
			short delete = 1;
			tsInfoEntitiy.setIsDelete(delete);
			tsInfoEntitiy.setModifiedBy(modifiedBy);
			tSInfoRepository.save(tsInfoEntitiy);

		}else {
			log.info("No tsInfo found with the provided ID{} in the DB",id);
			throw new Exception("No tsInfo found with the provided ID in the DB :"+id);
		}
		
		
	}
	
}
