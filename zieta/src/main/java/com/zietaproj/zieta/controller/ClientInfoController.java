package com.zietaproj.zieta.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zietaproj.zieta.dto.ClientInfoDTO;
import com.zietaproj.zieta.request.ClientInfoAddRequest;
import com.zietaproj.zieta.request.ClientInfoEditRequest;
import com.zietaproj.zieta.service.ClientInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags= "Client Information API")
public class ClientInfoController {

	@Autowired
	ClientInfoService clientinfoService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ClientInfoController.class);

	@RequestMapping(value = "getAllClients", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ClientInfoDTO> getAllClients() {
		List<ClientInfoDTO> clientinfos = null;
		try {
			clientinfos = clientinfoService.getAllClients();
		} catch (Exception e) {
			LOGGER.error("Error Occured in ClientInfoController#getAllInfo",e);

		}
		return clientinfos;
	}

	
	@RequestMapping(value = "addClientInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addClientinfo(@Valid @RequestBody ClientInfoAddRequest clientinfo) {
		clientinfoService.addClientInfo(clientinfo);

	}

	@RequestMapping(value = "updateClientInfo", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateClientinfo(@Valid @RequestBody ClientInfoEditRequest clientinfoedit) throws Exception {
		clientinfoService.editClientInfo(clientinfoedit);

	}
	
	
	@ApiOperation(value = "Deletes entries from client_info based on Id", notes = "Table reference: client_info")
	@RequestMapping(value = "deleteClientInfoById", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteClientInfoById(@RequestParam(required=true) Long id, @RequestParam(required=true) String modifiedBy) throws Exception {
		clientinfoService.deleteClientInfoById(id, modifiedBy);
	}
	

}
