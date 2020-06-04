package com.zietaproj.zieta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.ScreensMaster;

@Repository
public interface ScreensMasterRepository extends JpaRepository<ScreensMaster, Long> {

	//public void deleteByScreencod(String screen_code);

}
