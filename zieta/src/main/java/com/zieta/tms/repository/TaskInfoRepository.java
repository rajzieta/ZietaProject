package com.zieta.tms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zieta.tms.model.ClientInfo;
import com.zieta.tms.model.TaskActivity;
import com.zieta.tms.model.TaskInfo;

public interface TaskInfoRepository extends JpaRepository<TaskInfo, Long> {
	

	List<TaskInfo> findByClientIdAndProjectId(Long clientId, Long projectId);
	List<TaskInfo> findByClientIdAndProjectIdAndIsDelete(Long clientId, Long projectId,Short isDelete);
	List<TaskInfo> findByProjectIdAndIsDelete(Long projectId,Short isDelete);
	//Optional<TaskActivity> findByIdAndIsDelete(Long taskId, short notDeleted);
	List<TaskInfo> findByIsDelete(short notDeleted);
	
	@Query(value="select * from task_info where ext_id=?1 and client_id=?2", nativeQuery=true)
	TaskInfo findByExtIdAndClientId(String extId, Long clientId);

}
