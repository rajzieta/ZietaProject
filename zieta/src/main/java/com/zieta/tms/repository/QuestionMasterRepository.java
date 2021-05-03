package com.zieta.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.QuestionMaster;

@Repository
public interface QuestionMasterRepository extends JpaRepository<QuestionMaster, Long>{

	
	
}
