package com.zietaproj.zieta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.TaskMaster;

@Repository
public interface TaskMasterRepository extends JpaRepository<TaskMaster, Long> {
	
	List<TaskMaster> findByClientId(Long client_id);

}
