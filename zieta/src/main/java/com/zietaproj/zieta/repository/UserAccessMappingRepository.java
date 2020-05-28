package com.zietaproj.zieta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.UserAccessType;

@Repository
public interface UserAccessMappingRepository extends JpaRepository<UserAccessType, Long>{
	
	List<UserAccessType> findByClientIdAndUserId(Long clientId, Long userId);
	

}
