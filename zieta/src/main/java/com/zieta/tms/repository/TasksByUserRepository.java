package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.TasksByUser;


@Repository
public interface TasksByUserRepository extends JpaRepository<TasksByUser, Long> {
	
	List<TasksByUser> findByUserId(Long userId);
	
	List<TasksByUser> findByClientIdAndUserId(Long clientId, Long userId);

	List<TasksByUser> findByClientIdAndUserIdAndIsDelete(Long clientId, Long userId, short notDeleted);
	
}
