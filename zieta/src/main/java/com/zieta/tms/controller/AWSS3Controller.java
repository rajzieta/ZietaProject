package com.zieta.tms.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.zieta.tms.service.AWSS3Service;
import com.zieta.tms.service.ExpenseService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;



@RestController
@RequestMapping(value= "/api")
@Api(tags = "AWSS3Controller")
@Slf4j
public class AWSS3Controller{

	@Autowired
	private AWSS3Service service;
	
	@Autowired
	ExpenseService expenseService;

	@PostMapping(value= "/s3/upload")
	public ResponseEntity<String> uploadFile(@RequestPart(value = "multipartFile") MultipartFile multipartFile,
			@RequestParam("multipartFile-data") String key) {
		key = key.replace("+", " ");
		
		try {
			String attachmentPath = service.uploadFile(multipartFile, key);

			final String response = "[" + attachmentPath + "] uploaded successfully.";
			expenseService.updateFileDetails(attachmentPath);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception from uploadFile: ",e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value= "/s3/download")
	public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam(value= "fileName") final String keyName) {
			
		final byte[] data = service.downloadFile(keyName.trim());
		final ByteArrayResource resource = new ByteArrayResource(data);
		
		String tokens[] = keyName.split("/");
		String fileName = StringUtils.EMPTY;
		if (tokens != null && tokens.length > 0) {

			fileName = tokens[tokens.length - 1];
		}
		
		return ResponseEntity
				.ok()
				.contentLength(data.length)
				.header("Content-type", "application/octet-stream")
				.header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
				.body(resource);
	}
	
}
