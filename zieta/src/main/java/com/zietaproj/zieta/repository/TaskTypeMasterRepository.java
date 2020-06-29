package com.zietaproj.zieta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.TaskTypeMaster;

@Repository
public interface TaskTypeMasterRepository extends JpaRepository<TaskTypeMaster, Long> {
	
	List<TaskTypeMaster> findByClientId(Long clientId);

}
