package com.zieta.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.zieta.tms.model.ErrorLog;

@Repository
public interface ErrorLogMasterRepository extends JpaRepository<ErrorLog, Long> {
	
	ErrorLog findByLogSource(String errorSource);
	
	
	
	

}
