package com.zieta.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.MessageMaster;


@Repository
public interface MessageMasterRepository extends JpaRepository<MessageMaster, Long> {
	
	MessageMaster findByMsgCode(String msgCode);
	long deleteByMsgCode(String msgCode);
	
	
	

}
