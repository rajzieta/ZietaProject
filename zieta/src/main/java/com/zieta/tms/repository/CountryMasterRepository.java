package com.zieta.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.CountryMaster;

@Repository
public interface CountryMasterRepository extends JpaRepository<CountryMaster, Long>{

	
	
}
