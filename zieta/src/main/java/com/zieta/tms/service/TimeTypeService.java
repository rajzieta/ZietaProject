package com.zieta.tms.service;

import java.util.List;

import javax.validation.Valid;

import com.zieta.tms.dto.TimeTypeDTO;
import com.zieta.tms.model.TSInfo;
import com.zieta.tms.model.TimeType;
import com.zieta.tms.request.TimeTypeEditRequest;
import com.zieta.tms.response.TimeTypesByClientResponse;

public interface TimeTypeService {

	public List<TimeTypeDTO> getAllTimetypes();

	public void addTimetypemaster(TimeType timetype);
	
	public List<TimeTypesByClientResponse> getAllTimeTypesByClient(Long client_id);

	public void editTimeTypesById(@Valid TimeTypeEditRequest timetypeEditRequest) throws Exception;

	public void deleteTimeTypesById(Long id, String modifiedBy) throws Exception;

}
