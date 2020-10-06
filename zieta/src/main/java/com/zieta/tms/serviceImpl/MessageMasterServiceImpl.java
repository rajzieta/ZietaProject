package com.zieta.tms.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zieta.tms.model.MessageMaster;
import com.zieta.tms.repository.MessageMasterRepository;
import com.zieta.tms.service.MessageMasterService;

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
