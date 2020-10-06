package com.zieta.tms.service;

import java.util.List;

import com.zieta.tms.model.MessageMaster;

public interface MessageMasterService {
	
	MessageMaster findByMsgCode(String msgCode);
	
	List<MessageMaster> getAllMsgs();
	
	void addMsg(MessageMaster msgMaster);
	
	void deleteMsg(String msgCode);
	
	

}
