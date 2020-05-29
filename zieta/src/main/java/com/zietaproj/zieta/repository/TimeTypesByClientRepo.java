package com.zietaproj.zieta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zietaproj.zieta.model.TimeTypesByClient;

public interface TimeTypesByClientRepo extends JpaRepository<TimeTypesByClient, Long> {

	//Object findByClient_id(Long client_id);
	
	List<TimeTypesByClient> findByClientId(long client_id);

}
