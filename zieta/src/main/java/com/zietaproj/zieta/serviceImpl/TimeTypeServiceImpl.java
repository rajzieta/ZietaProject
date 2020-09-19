package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.zietaproj.zieta.request.TimeTypeEditRequest;
import com.zietaproj.zieta.response.TimeTypesByClientResponse;
import com.zietaproj.zieta.service.TimeTypeService;

import lombok.extern.slf4j.Slf4j;

import com.zietaproj.zieta.model.RoleMaster;
import com.zietaproj.zieta.model.TSInfo;
import com.zietaproj.zieta.model.TimeType;



@Service
@Slf4j
public class TimeTypeServiceImpl implements TimeTypeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TimeTypeServiceImpl.class);
	
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
			timetypeDTO.setClientStatus(clientInfoRepository.findById(timetype.getClientId()).get().getClientStatus());

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
	
	public void editTimeTypesById(@Valid TimeTypeEditRequest timetypeEditRequest) throws Exception {
		
		Optional<TimeType> timeTypeEntity = timetypeRepository.findById(timetypeEditRequest.getId());
		if(timeTypeEntity.isPresent()) {
			TimeType timetype = modelMapper.map(timetypeEditRequest, TimeType.class);
			timetypeRepository.save(timetype);
			
		}else {
			throw new Exception("Timetype not found with the provided ID : "+timetypeEditRequest.getId());
		}
		
		
	}

	public void deleteTimeTypesById(Long id, String modifiedBy) throws Exception {
		
		
		Optional<TimeType> timetype = timetypeRepository.findById(id);
		if (timetype.isPresent()) {
			TimeType timetypeEntitiy = timetype.get();
			short delete = 1;
			timetypeEntitiy.setIsDelete(delete);
			timetypeEntitiy.setModifiedBy(modifiedBy);
			timetypeRepository.save(timetypeEntitiy);

		}else {
			log.info("No TimeType found with the provided ID{} in the DB",id);
			throw new Exception("No TimeType found with the provided ID in the DB :"+id);
		}
	}
	
	
	
	
}
