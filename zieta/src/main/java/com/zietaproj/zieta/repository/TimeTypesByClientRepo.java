package com.zietaproj.zieta.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.zietaproj.zieta.model.TimeTypesbyClient;

public interface TimeTypesByClientRepo extends JpaRepository<TimeTypesbyClient, Long> {

	//Object findByClient_id(Long client_id);

}
