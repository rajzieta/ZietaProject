package com.zietaproj.zieta.service;

import java.util.List;

import javax.validation.Valid;


import com.zietaproj.zieta.dto.TimeTypeDTO;
import com.zietaproj.zieta.model.TSInfo;
import com.zietaproj.zieta.model.TimeType;
import com.zietaproj.zieta.request.TimeTypeEditRequest;
import com.zietaproj.zieta.response.TimeTypesByClientResponse;

public interface TimeTypeService {

	public List<TimeTypeDTO> getAllTimetypes();

	public void addTimetypemaster(TimeType timetype);
	
	public List<TimeTypesByClientResponse> getAllTimeTypesByClient(Long client_id);

	public void editTimeTypesById(@Valid TimeTypeEditRequest timetypeEditRequest) throws Exception;

	public void deleteTimeTypesById(Long id, String modifiedBy) throws Exception;

}
