package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.TaskTypeMaster;

@Repository
public interface TaskTypeMasterRepository extends JpaRepository<TaskTypeMaster, Long> {
	
	List<TaskTypeMaster> findByClientIdAndIsDelete(Long clientId, Short notDeleted);

	List<TaskTypeMaster> findByIsDelete(short notDeleted);

}
