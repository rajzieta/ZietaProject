package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.LeaveInfo;

@Repository
public interface LeaveInfoRepository extends JpaRepository<LeaveInfo, Long> {

	public List<LeaveInfo> findByIsDelete(short notDeleted);

	
	
}
