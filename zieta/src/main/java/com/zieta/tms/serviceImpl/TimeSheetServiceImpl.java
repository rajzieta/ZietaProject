package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import com.zieta.tms.dto.WorkflowDTO;
import com.zieta.tms.model.ActivityMaster;
import com.zieta.tms.model.ProcessSteps;
import com.zieta.tms.model.RoleMaster;
import com.zieta.tms.model.TSInfo;
import com.zieta.tms.model.TSTimeEntries;
import com.zieta.tms.model.TSWorkflow;
import com.zieta.tms.model.TaskInfo;
import com.zieta.tms.model.WorkflowRequest;
import com.zieta.tms.repository.ActivityMasterRepository;
import com.zieta.tms.repository.ClientInfoRepository;
import com.zieta.tms.repository.ProcessStepsRepository;
import com.zieta.tms.repository.ProjectInfoRepository;
import com.zieta.tms.repository.StatusMasterRepository;
import com.zieta.tms.repository.TSInfoRepository;
import com.zieta.tms.repository.TSTimeEntriesRepository;
import com.zieta.tms.repository.TaskInfoRepository;
import com.zieta.tms.repository.WorkflowRepository;
import com.zieta.tms.repository.WorkflowRequestRepository;
import com.zieta.tms.request.TimeEntriesByTsIdRequest;
import com.zieta.tms.request.UpdateTimesheetByIdRequest;
import com.zieta.tms.response.TSInfoModel;
import com.zieta.tms.response.TimeEntriesByTimesheetIDResponse;
import com.zieta.tms.service.TimeSheetService;
import com.zieta.tms.util.CurrentWeekUtil;
import com.zieta.tms.util.TSMUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class TimeSheetServiceImpl implements TimeSheetService {
	
	@Autowired
	TSInfoRepository tSInfoRepository;
	
	@Autowired
	ActivityMasterRepository  activityMasterRepository;
	
	@Autowired
	TaskInfoRepository taskInfoRepository;
	
	@Autowired
	ProjectInfoRepository  projectInfoRepository;
	
	@Autowired
	TSTimeEntriesRepository tstimeentriesRepository;
	
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
		
		boolean isDatesValid = TSMUtil.validateDates(startDate,endDate);
		if(!isDatesValid) {
			CurrentWeekUtil currentWeek = new CurrentWeekUtil(new Locale("en","IN"));
			startDate =currentWeek.getFirstDay();
			endDate = currentWeek.getLastDay();
		}
		List<TSInfo> tsInfoList = tSInfoRepository.findByClientIdAndUserIdAndIsDeleteAndTsDateBetweenOrderByTaskActivityIdAscIdAsc(clientId, 
				userId, notDeleted, startDate, endDate);
		for (TSInfo tsInfo : tsInfoList) {
			TSInfoModel taskInfoModel = new TSInfoModel();
			taskInfoModel.setTsInfo(tsInfo);
			List<TSTimeEntries> timeEntries = tstimeentriesRepository.findByTsIdAndIsDelete(tsInfo.getId(), notDeleted);
			taskInfoModel.setTimeEntries(timeEntries);
			if(tsInfo.getActivityId() != null && tsInfo.getActivityId() !=0) {
				ActivityMaster activityEntity = activityMasterRepository.findById(tsInfo.getActivityId()).get();
				taskInfoModel.setActivityCode(activityEntity.getActivityCode());
				taskInfoModel.setActivityDescription(activityEntity.getActivityDesc());
			}else {
				taskInfoModel.setActivityCode(null);
				taskInfoModel.setActivityDescription(StringUtils.EMPTY);
			}
			
			if(tsInfo.getProjectId() !=null && tsInfo.getProjectId() !=0) {
				taskInfoModel.setProjectCode(projectInfoRepository.findById(tsInfo.getProjectId()).get().getProjectCode());
			}else {
				taskInfoModel.setProjectCode(null);
			}
			
			if(tsInfo.getTaskId() !=null && tsInfo.getTaskId() !=0) {
				TaskInfo taskInfoEntity =taskInfoRepository.findById(tsInfo.getTaskId()).get();
				taskInfoModel.setTaskCode(taskInfoEntity.getTaskCode());
				taskInfoModel.setTaskDescription(taskInfoEntity.getTaskDescription());
			}else {
				taskInfoModel.setTaskCode(null);
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

		
		boolean statusTypesLoad = Boolean.FALSE;
		for (TSInfo tsInfo : tsInfoList) {
			
			//Setting the statusId which is marked as default in the DB for the corresponding the statustype, doing
			// as per change request.
			Long statuId = statusMasterRepository.findByClientIdAndStatusTypeAndIsDefaultAndIsDelete(tsInfo.getClientId(),
					TMSConstants.TIMESHEET, Boolean.TRUE, (short)0).getId();
			tsInfo.setStatusId(statuId);
		}
		
		List<TSInfo> tsinfoEntites = tSInfoRepository.saveAll(tsInfoList);
//		enable for testing
//		submitTimeSheet(tsinfoEntites); 

		return tsinfoEntites;
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
				tsInfo.setStatusId(statusId);
				tSInfoRepository.save(tsInfo);
				if (workflowRequestList.size() == 0) {
					// get the approverid from the process_step based on the clientId, projectId and taskId
					List<ProcessSteps> processStepsList = processStepsRepository
							.findByClientIdAndProjectIdAndProjectTaskIdOrderByStepId(tsInfo.getClientId(),
									tsInfo.getProjectId(), tsInfo.getTaskId());

					if (processStepsList != null && processStepsList.size() == 1
							&& processStepsList.get(0).getApproverId() == null) {

						statusId = statusMasterRepository
								.findByClientIdAndStatusTypeAndStatusCodeAndIsDelete(tsInfo.getClientId(),
										TMSConstants.TIMESHEET, TMSConstants.TIMESHEET_APPROVED, (short) 0)
								.getId();
						// set the status as approved and there are no actions on the workflow, so move
						// to next TSInfo item.
						tsInfo.setStatusId(statusId);
						tSInfoRepository.save(tsInfo);
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
							buildWFRForSubmission(workflowRequest, tsInfo, approverIds[j]);
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
		Optional<TSTimeEntries> TsTimeEntity = tstimeentriesRepository.findById(timeentriesByTsIdRequest.getId());
		if(TsTimeEntity.isPresent()) {
			TSTimeEntries tsTimeSave = TsTimeEntity.get();
			TSTimeEntries tstimeentry= modelMapper.map(timeentriesByTsIdRequest, TSTimeEntries.class);
			
			tstimeentriesRepository.save(tstimeentry);
		}
	
		else {
			throw new Exception("Timeentry not found with the provided ID : "+timeentriesByTsIdRequest.getId());
		}
	}
	
	
	@Override
	@Transactional
	public void updateTimeEntriesByIds(@Valid List<TimeEntriesByTsIdRequest> timeentriesByTsIdRequest) throws Exception {
		
		for (TimeEntriesByTsIdRequest updateRequest : timeentriesByTsIdRequest) {
			updateTimeEntriesByID(updateRequest);
		}
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
	
	
}
