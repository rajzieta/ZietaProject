package com.zieta.tms.controller;

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

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value= "/api")
@Api(tags = "AWSS3Controller")
@Slf4j
public class AWSS3Controller{

	@Autowired
	private AWSS3Service service;

	@PostMapping(value= "/s3/upload")
	public ResponseEntity<String> uploadFile(@RequestPart(value= "multipartFile") MultipartFile multipartFile, @RequestParam String key) {
	
		try {
			String attachmentPath = service.uploadFile(multipartFile, key);

			final String response = "[" + attachmentPath + "] uploaded successfully.";
			return new ResponseEntity<>(response, HttpStatus.OK);
		} 
		catch (final AmazonServiceException ex) {
			if (ex.getStatusCode() == HttpStatus.FORBIDDEN.value()) {
				return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
			}
			log.info("File upload is failed.");
			log.error("Error= {} while uploading file.", ex.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value= "/s3/download")
	public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam(value= "fileName") final String keyName) {
		final byte[] data = service.downloadFile(keyName);
		final ByteArrayResource resource = new ByteArrayResource(data);
		return ResponseEntity
				.ok()
				.contentLength(data.length)
				.header("Content-type", "application/octet-stream")
				.header("Content-disposition", "attachment; filename=\"" + keyName + "\"")
				.body(resource);
	}
}
