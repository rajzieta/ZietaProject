package com.zieta.tms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.LeaveInfo;

@Repository
public interface LeaveInfoRepository extends JpaRepository<LeaveInfo, Long> {

	public List<LeaveInfo> findByIsDelete(short notDeleted);

	public List<LeaveInfo> findByClientIdAndIsDelete(Long clientId, short notDeleted);

	public List<LeaveInfo> findByClientIdAndUserIdAndIsDelete(Long clientId, Long userId, short notDeleted);

	public List<LeaveInfo> findByUserIdAndIsDeleteAndLeaveStartDateBetween(Long userId,short notDeleted, Date startDate, Date endDate);
	
	public Page<LeaveInfo> findByUserIdAndIsDeleteAndLeaveStartDateBetween(Long userId,short notDeleted, Date startDate, Date endDate,Pageable paging);

	public List<LeaveInfo> findByClientIdAndApproverIdAndStatusIdAndIsDelete(Long clientId, Long approverId,
			long statusId, short notDeleted);

	
	
}
