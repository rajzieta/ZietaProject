package com.zietaproj.zieta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zietaproj.zieta.model.TaskInfo;

public interface TaskInfoRepository extends JpaRepository<TaskInfo, Long> {

	

}
