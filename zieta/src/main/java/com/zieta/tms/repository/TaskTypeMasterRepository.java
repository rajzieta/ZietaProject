package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.TaskTypeMaster;
import com.zieta.tms.model.UserInfo;

@Repository
public interface TaskTypeMasterRepository extends JpaRepository<TaskTypeMaster, Long> {
	
	List<TaskTypeMaster> findByClientIdAndIsDelete(Long clientId, Short notDeleted);

	List<TaskTypeMaster> findByIsDelete(short notDeleted);

	@Query(value="select * from task_type_master where ext_id=?1 and client_id=?2", nativeQuery=true)
	TaskTypeMaster findByExtIdAndClientId(String extId, Long clientId);
}
