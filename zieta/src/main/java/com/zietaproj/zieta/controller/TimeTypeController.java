package com.zietaproj.zieta.controller;

import java.util.List;

import javax.validation.Valid;

//import org.hibernate.type.TimeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zietaproj.zieta.dto.TimeTypeDTO;
import com.zietaproj.zieta.model.TaskMaster;
import com.zietaproj.zieta.model.TimeType;
import com.zietaproj.zieta.repository.TimeTypeRepository;
import com.zietaproj.zieta.service.TimeTypeService;

@RestController
@RequestMapping("/api")
public class TimeTypeController {

	@Autowired
	TimeTypeService timetypeService;
	
	// Get All Timetype
		@GetMapping("/getAllTimetypes")
        public String getAllTimetypes() {
			String response="";
			try {
				List<TimeTypeDTO> timetypes= timetypeService.getAllTimetypes();
				System.out.println("timetypes size=>"+timetypes.size());
				ObjectMapper mapper = new ObjectMapper();
				response = mapper.writeValueAsString(timetypes);
			}catch(Exception e) {
				e.printStackTrace();
			}
			return response;
		}	
		
		@PostMapping("/addTimetypemaster") 
		@RequestMapping(value = "addTimetypemaster", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)

		  public void addTimetypemaster(@Valid @RequestBody TimeType timetype) { 
			timetypeService.addTimetypemaster(timetype);
		   }

		

}
