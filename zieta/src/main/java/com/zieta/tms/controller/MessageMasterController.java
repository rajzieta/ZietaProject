package com.zieta.tms.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zieta.tms.model.MessageMaster;
import com.zieta.tms.service.MessageMasterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags= "Message Details API")
public class MessageMasterController {

	@Autowired
	MessageMasterService messageMasterService;

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageMasterController.class);

	@RequestMapping(value = "getAllMsgs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<MessageMaster> getAllMsgs() {
		List<MessageMaster> msgList = null;
		try {
			msgList = messageMasterService.getAllMsgs();
		} catch (Exception e) {
			LOGGER.error("Error Occured in MessageMasterController#getAllErrors",e);

		}
		return msgList;
	}

	
	@RequestMapping(value = "addMsgDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addMsgDetails(@Valid @RequestBody MessageMaster msgMaster) {
		messageMasterService.addMsg(msgMaster);

	}

	@RequestMapping(value = "updateMsgDetails", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateMsgDetails(@Valid @RequestBody MessageMaster messageMaster) {
		messageMasterService.addMsg(messageMaster);

	}
	
	@RequestMapping(value = "deleteMessages", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteMessage(@RequestParam(required = true) String msgCode) {
		messageMasterService.deleteMsg(msgCode);

	}
	
	@GetMapping("/getMsgByCode")
	@ApiOperation(value = "Message details based on the msg code", notes = "Table reference: msg_master")
	public ResponseEntity<MessageMaster> getErrorByCode(
			@RequestParam(required = true) String msgCode) {
		try {
			MessageMaster errorDetails = messageMasterService.findByMsgCode(msgCode);
			return new ResponseEntity<MessageMaster>(errorDetails, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<MessageMaster>(HttpStatus.NOT_FOUND);
		}
	}


}
