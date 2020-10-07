package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.ScreensMaster;

@Repository
public interface ScreensMasterRepository extends JpaRepository<ScreensMaster, Long> {

	List<ScreensMaster> findByScreenCategory(String screenCategory);




}
