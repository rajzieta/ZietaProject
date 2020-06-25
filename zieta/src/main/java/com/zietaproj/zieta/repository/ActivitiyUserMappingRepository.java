package com.zietaproj.zieta.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zietaproj.zieta.model.ActivityUserMapping;

public interface ActivitiyUserMappingRepository extends JpaRepository <ActivityUserMapping, Long> {

	List<ActivityUserMapping> findByUserId(long userId);
	Optional<ActivityUserMapping> findByActivityId(long userId);
}

