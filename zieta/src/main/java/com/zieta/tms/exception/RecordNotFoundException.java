package com.zieta.tms.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RecordNotFoundException {

	private static final long serialVersionUID = 1L;

    public RecordNotFoundException(String message){
    	 System.out.println(message);
    }
    
    public void RecordNotFoundException(String message,String msg){
        System.out.println(message+" "+msg);
    }
}
