package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.LeaveTypeMaster;

@Repository
public interface LeaveMasterRepository extends JpaRepository<LeaveTypeMaster, Long> {

	public List<LeaveTypeMaster> findByIsDelete(short notDeleted);

}
