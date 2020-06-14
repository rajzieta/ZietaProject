package com.zietaproj.zieta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zietaproj.zieta.model.ActivityUserMapping;
import com.zietaproj.zieta.model.TaskActivity;

public interface ActivitiyUserMappingRepository extends JpaRepository <ActivityUserMapping, Long> {

	List<ActivityUserMapping> findByUserId(long userId);
	
}

