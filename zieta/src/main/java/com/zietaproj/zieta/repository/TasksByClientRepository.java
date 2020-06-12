package com.zietaproj.zieta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.TasksByClient;


@Repository
public interface TasksByClientRepository extends JpaRepository<TasksByClient, Long>{
	
	List<TasksByClient> findByClientId(Long client_id);
}
