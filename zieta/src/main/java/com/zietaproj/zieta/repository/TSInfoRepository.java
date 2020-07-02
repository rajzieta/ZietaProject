package com.zietaproj.zieta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.TSInfo;

@Repository
public interface TSInfoRepository extends JpaRepository<TSInfo, Long>{

}
