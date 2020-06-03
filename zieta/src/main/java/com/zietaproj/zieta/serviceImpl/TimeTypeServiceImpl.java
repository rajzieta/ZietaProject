package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
//import org.hibernate.type.TimeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;


import com.zietaproj.zieta.dto.TimeTypeDTO;
import com.zietaproj.zieta.repository.TimeTypeRepository;
import com.zietaproj.zieta.response.TimeTypesByClientResponse;
import com.zietaproj.zieta.service.TimeTypeService;



import com.zietaproj.zieta.model.TimeType;



@Service
public class TimeTypeServiceImpl implements TimeTypeService {

	@Autowired
	TimeTypeRepository timetypeRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<TimeTypeDTO> getAllTimetypes() {
		List<TimeType> timetypes = timetypeRepository.findAll();
		List<TimeTypeDTO> timetypeDTOs = new ArrayList<TimeTypeDTO>();
		TimeTypeDTO timetypeDTO = null;
		for (TimeType timetype : timetypes) {
			timetypeDTO = new TimeTypeDTO();
			timetypeDTO.setId(timetype.getId());
			timetypeDTO.setTime_type(timetype.getTime_type());
			timetypeDTO.setClient_id(timetype.getClientId());
			timetypeDTO.setModified_by(timetype.getModified_by());
		timetypeDTOs.add(timetypeDTO);
		}
		return timetypeDTOs;
	}
	
	
	@Override
	public void addTimetypemaster(TimeType timetype)
	{
		timetypeRepository.save(timetype);
	}
	
	
	@Override
	public List<TimeTypesByClientResponse> getAllTimeTypesByClient(Long client_id) {

		List<TimeType> timeTypesByClientList = timetypeRepository.findByClientId(client_id);
		List<TimeTypesByClientResponse> timeTypesByClientResponseList = new ArrayList<>();
		TimeTypesByClientResponse timeTypesByClientResponse = null;
		for (TimeType timeTypesByClient : timeTypesByClientList) {
			timeTypesByClientResponse = modelMapper.map(timeTypesByClient, 
					TimeTypesByClientResponse.class);
			timeTypesByClientResponseList.add(timeTypesByClientResponse);
		}

		return timeTypesByClientResponseList;
	}
}
