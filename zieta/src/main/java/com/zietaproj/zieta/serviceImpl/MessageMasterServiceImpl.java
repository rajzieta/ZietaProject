package com.zietaproj.zieta.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.model.MessageMaster;
import com.zietaproj.zieta.repository.MessageMasterRepository;
import com.zietaproj.zieta.service.MessageMasterService;

@Service
public class MessageMasterServiceImpl implements MessageMasterService {

	@Autowired
	MessageMasterRepository erroMasterRepo;
	
	@Override
	public MessageMaster findByMsgCode(String msgCode) {
		return erroMasterRepo.findByMsgCode(msgCode);
	}


	@Override
	public List<MessageMaster> getAllMsgs() {
		
		return erroMasterRepo.findAll();
	}


	@Override
	public void addMsg(MessageMaster errorMaster) {
		erroMasterRepo.save(errorMaster);
	}


	@Override
	@Transactional
	public void deleteMsg(String errorCode) {
		erroMasterRepo.deleteByMsgCode(errorCode);
		
	}


	

}
