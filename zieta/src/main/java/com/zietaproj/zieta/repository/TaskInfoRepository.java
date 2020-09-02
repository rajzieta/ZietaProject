package com.zietaproj.zieta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zietaproj.zieta.model.TaskInfo;

public interface TaskInfoRepository extends JpaRepository<TaskInfo, Long> {
	

	List<TaskInfo> findByClientIdAndProjectId(Long clientId, Long projectId);
	List<TaskInfo> findByClientIdAndProjectIdAndIsDelete(Long clientId, Long projectId,Short isDelete);
	List<TaskInfo> findByProjectIdAndIsDelete(Long projectId,Short isDelete);

}
