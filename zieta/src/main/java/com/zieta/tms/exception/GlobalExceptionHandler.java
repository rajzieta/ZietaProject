package com.zieta.tms.exception;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import com.amazonaws.AmazonServiceException;

import lombok.extern.slf4j.Slf4j;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MultipartException.class)
    public ResponseEntity<String>  handleMultiPartException(MultipartException e) {
		return error(INTERNAL_SERVER_ERROR,e);

    }
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String>  handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
		return error(INTERNAL_SERVER_ERROR,e);

    }
	
	@ExceptionHandler(AmazonServiceException.class)
    public ResponseEntity<String>  handleAmzaonServiceException(AmazonServiceException ex) {
		if (ex.getStatusCode() == HttpStatus.FORBIDDEN.value()) {
			return error(FORBIDDEN,ex);
		}else {
			log.info("File upload is failed.");
			log.error("Error= {} while uploading file.", ex.getMessage());
			return error(INTERNAL_SERVER_ERROR,ex);
		}
		

    }
	
	private ResponseEntity<String> error(HttpStatus status, Exception e){
		log.error("Exception: ",e);
		return  ResponseEntity.status(status).body(e.getMessage());
	}

	
	
}
