package com.zietaproj.zieta.service;

import java.util.List;

import javax.validation.Valid;


import com.zietaproj.zieta.dto.TimeTypeDTO;
import com.zietaproj.zieta.model.TimeType;

public interface TimeTypeService {

	public List<TimeTypeDTO> getAllTimetypes();

	public void addTimetypemaster(@Valid TimeType timetype);

//	public TimeType save(@Valid TimeType timetype);

}
