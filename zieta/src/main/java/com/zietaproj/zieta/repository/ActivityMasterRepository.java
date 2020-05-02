package com.zietaproj.zieta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.ActivityMaster;

@Repository
public interface ActivityMasterRepository extends JpaRepository<ActivityMaster, Long>{

}
