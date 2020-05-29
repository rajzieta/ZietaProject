package com.zietaproj.zieta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zietaproj.zieta.model.OrgInfo;
import com.zietaproj.zieta.model.TimeTypesByClient;

public interface OrgInfoRepository extends JpaRepository<OrgInfo, Long> {

	

}
