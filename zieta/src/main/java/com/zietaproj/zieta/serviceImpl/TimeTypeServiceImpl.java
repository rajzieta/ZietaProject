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
import com.zietaproj.zieta.repository.ClientInfoRepository;
import com.zietaproj.zieta.repository.TSInfoRepository;
import com.zietaproj.zieta.repository.TimeTypeRepository;
import com.zietaproj.zieta.response.TimeTypesByClientResponse;
import com.zietaproj.zieta.service.TimeTypeService;
import com.zietaproj.zieta.model.TSInfo;
import com.zietaproj.zieta.model.TimeType;



@Service
public class TimeTypeServiceImpl implements TimeTypeService {

	@Autowired
	TimeTypeRepository timetypeRepository;
	
	
	@Autowired
	ClientInfoRepository clientInfoRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<TimeTypeDTO> getAllTimetypes() {
		short notDeleted=0;
		List<TimeType> timetypes = timetypeRepository.findByIsDelete(notDeleted);
		List<TimeTypeDTO> timetypeDTOs = new ArrayList<TimeTypeDTO>();
		TimeTypeDTO timetypeDTO = null;
		for (TimeType timetype : timetypes) {
			timetypeDTO = new TimeTypeDTO();
			timetypeDTO.setId(timetype.getId());
			timetypeDTO.setTimeType(timetype.getTimeType());
			timetypeDTO.setClientId(timetype.getClientId());
			timetypeDTO.setClientCode(clientInfoRepository.findById(timetype.getClientId()).get().getClientCode());
			timetypeDTO.setClientDescription(clientInfoRepository.findById(timetype.getClientId()).get().getClientName());
			
			timetypeDTO.setModifiedBy(timetype.getModifiedBy());
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
		short notDeleted=0;
		List<TimeType> timeTypesByClientList = timetypeRepository.findByClientIdAndIsDelete(client_id, notDeleted);
		List<TimeTypesByClientResponse> timeTypesByClientResponseList = new ArrayList<>();
		TimeTypesByClientResponse timeTypesByClientResponse = null;
		for (TimeType timeTypesByClient : timeTypesByClientList) {
			timeTypesByClientResponse = modelMapper.map(timeTypesByClient, 
					TimeTypesByClientResponse.class);
			timeTypesByClientResponse.setClientCode(clientInfoRepository.findById(timeTypesByClient.getClientId()).get().getClientCode());
			timeTypesByClientResponse.setClientDescription(clientInfoRepository.findById(timeTypesByClient.getClientId()).get().getClientName());
			
			timeTypesByClientResponseList.add(timeTypesByClientResponse);
		}

		return timeTypesByClientResponseList;

	}
	
}
