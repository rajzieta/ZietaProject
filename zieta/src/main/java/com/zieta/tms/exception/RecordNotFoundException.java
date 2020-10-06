package com.zieta.tms.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RecordNotFoundException {

	private static final long serialVersionUID = 1L;

    public RecordNotFoundException(String message){
        
    }
}
