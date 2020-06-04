package com.zietaproj.zieta.service;

import java.util.List;

import com.zietaproj.zieta.model.ErrorMaster;

public interface ErrorMasterService {
	
	ErrorMaster findByErrorCode(String errorCode);
	
	List<ErrorMaster> getAllErrors();
	
	void addError(ErrorMaster errorMaster);
	
	

}
