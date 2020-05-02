package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

//import org.hibernate.type.TimeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;


import com.zietaproj.zieta.dto.TimeTypeDTO;
import com.zietaproj.zieta.repository.TimeTypeRepository;
import com.zietaproj.zieta.service.TimeTypeService;



import com.zietaproj.zieta.model.TimeType;



@Service
public class TimeTypeServiceImpl implements TimeTypeService {

	@Autowired
	TimeTypeRepository timetypeRepository;
	 private JdbcTemplate jdbcTemplate;
	@Override
	public List<TimeTypeDTO> getAllTimetypes() {
		List<TimeType> timetypes = timetypeRepository.findAll();
		List<TimeTypeDTO> timetypeDTOs = new ArrayList<TimeTypeDTO>();
		TimeTypeDTO timetypeDTO = null;
		for (TimeType timetype : timetypes) {
			timetypeDTO = new TimeTypeDTO();
			timetypeDTO.setId(timetype.getId());
			timetypeDTO.setTime_type(timetype.getTime_type());
			timetypeDTO.setClient_id(timetype.getClient_id());
		timetypeDTOs.add(timetypeDTO);
		}
		return timetypeDTOs;
	}
	/*
	 * @Override public TimeType save(@Valid TimeType timetype) { // TODO
	 * Auto-generated method stub return null; }
	 */
	/*
	 * public void save() {
	 * 
	 * SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
	 * insertActor.withTableName("TIME_TYPE_MASTER").usingColumns("id", "Client_id",
	 * "time_type", "created_date", "modified_by", "modified_date", "is_delete");
	 * 
	 * BeanPropertySqlParameterSource param = new
	 * BeanPropertySqlParameterSource(timetypeRepository);
	 * insertActor.execute(param); }
	 */
	
	
	@Override
	public void addTimetypemaster(TimeType timetype)
	{
		timetypeRepository.save(timetype);
	}
}
