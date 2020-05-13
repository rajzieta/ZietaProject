package com.zietaproj.zieta.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zietaproj.zieta.dto.ClientInfoDTO;
import com.zietaproj.zieta.model.ClientInfo;
import com.zietaproj.zieta.service.ClientInfoService;


@RestController
@RequestMapping("/api")
public class ClientInfoController {

	
	@Autowired
	ClientInfoService clientinfoService;
	// Get All Tasks
	@GetMapping("/getAllInfo")
	public String getAllInfo() {
		String response="";
		try {
			List<ClientInfoDTO> clientinfos= clientinfoService.getAllInfo();
			System.out.println("ClienInfo size=>"+clientinfos.size());
			ObjectMapper mapper = new ObjectMapper();
			response = mapper.writeValueAsString(clientinfos);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	// Create a new Task
	  
	
	// @PostMapping("/addclientinfo")
	@RequestMapping(value = "addClientInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)

	  public void addClientinfo(@Valid @RequestBody ClientInfo clientinfo) { 
		 clientinfoService.addClientInfo(clientinfo);
	  
	  }
	 
	
}
