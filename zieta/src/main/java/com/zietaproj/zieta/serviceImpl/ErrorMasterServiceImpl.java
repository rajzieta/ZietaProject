package com.zietaproj.zieta.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.model.ErrorMaster;
import com.zietaproj.zieta.repository.ErrorMasterRepository;
import com.zietaproj.zieta.service.ErrorMasterService;

@Service
public class ErrorMasterServiceImpl implements ErrorMasterService {

	@Autowired
	ErrorMasterRepository erroMasterRepo;
	
	@Override
	public ErrorMaster findByErrorCode(String errorCode) {
		return erroMasterRepo.findByErrorCode(errorCode);
	}


	@Override
	public List<ErrorMaster> getAllErrors() {
		
		return erroMasterRepo.findAll();
	}


	@Override
	public void addError(ErrorMaster errorMaster) {
		erroMasterRepo.save(errorMaster);
	}


	

}
