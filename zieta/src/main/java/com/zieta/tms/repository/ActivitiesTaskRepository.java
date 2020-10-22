package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zieta.tms.model.TaskActivity;

public interface ActivitiesTaskRepository extends JpaRepository <TaskActivity, Long> {

	List<TaskActivity> findByTaskId(long taskId);
	
	List<TaskActivity> findByClientIdAndProjectIdAndTaskId(Long clientId, Long projectId, Long taskId);
	
	List<TaskActivity> findByClientIdAndProjectIdAndTaskIdAndIsDelete(Long clientId, Long projectId, Long taskId, Short notDeleted);
	
	List<TaskActivity> findByClientIdAndUserIdAndIsDelete(Long clientId, Long userId, Short notDeleted);
	
	TaskActivity findByTaskActivityIdAndUserId(Long id, Long userId);
}

