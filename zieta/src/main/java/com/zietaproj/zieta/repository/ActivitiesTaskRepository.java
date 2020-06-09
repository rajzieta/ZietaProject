package com.zietaproj.zieta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zietaproj.zieta.model.TaskActivity;

public interface ActivitiesTaskRepository extends JpaRepository <TaskActivity, Long> {

	List<TaskActivity> findByTaskId(long taskId);
	
	List<TaskActivity> findByClientIdAndProjectIdAndTaskId(Long clientId, Long projectId, Long taskId);
}

