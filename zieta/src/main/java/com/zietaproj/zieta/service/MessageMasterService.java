package com.zietaproj.zieta.service;

import java.util.List;

import com.zietaproj.zieta.model.MessageMaster;

public interface MessageMasterService {
	
	MessageMaster findByMsgCode(String msgCode);
	
	List<MessageMaster> getAllMsgs();
	
	void addMsg(MessageMaster msgMaster);
	
	void deleteMsg(String msgCode);
	
	

}
