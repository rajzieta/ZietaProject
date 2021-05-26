package com.zieta.tms.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.LeaveInfo;

@Repository
public interface LeaveInfoRepository extends JpaRepository<LeaveInfo, Long> {

	public List<LeaveInfo> findByIsDelete(short notDeleted);

	public List<LeaveInfo> findByClientIdAndIsDelete(Long clientId, short notDeleted);

	public List<LeaveInfo> findByClientIdAndUserIdAndIsDelete(Long clientId, Long userId, short notDeleted);
	
	@Query(value ="select * from leave_info ln where ln.client_id=?1 and ln.is_delete= ?2 and ln.leave_start_date >= ?3 and ln.leave_start_date <= ?4", nativeQuery=true)
	public List<LeaveInfo> findByClientIdAndIsDeleteAndLeaveStartDateBetween(Long clientId,short notDeleted, String startDate, String endDate);
	
	@Query(value ="select * from leave_info ln where ln.client_id=?1 and ln.is_delete= ?2 and ln.leave_start_date >= ?3 and ln.leave_start_date <= ?4", nativeQuery=true)
	public Page<LeaveInfo> findByClientIdAndIsDeleteAndLeaveStartDateBetween(Long clientId,short notDeleted, String startDate, String endDate,Pageable paging);

	public List<LeaveInfo> findByClientIdAndApproverIdAndStatusIdAndIsDelete(Long clientId, Long approverId,
			long statusId, short notDeleted);
	@Query(value ="select * from leave_info ln where ln.approver_id=?1 and ln.status_id in ?2 and ln.leave_start_date >= ?3 and ln.leave_start_date <= ?4", nativeQuery=true)
	public List<LeaveInfo> findByApproverIdAndStatusIdInAndLeaveStartDateBetween(Long approverId,
			Collection<Long> statusId, String startDate, String endDate);
	
	@Query(value ="select * from leave_info ln where ln.client_id=?1 and ln.user_id=?2 and ln.is_delete= ?3 and ln.leave_start_date >= ?4 and ln.leave_start_date <= ?5", nativeQuery=true)
	public List<LeaveInfo> findByClientIdAndUserIdAndLeaveStartDateBetweenAndIsDelete(Long clientId, Long userId, short notDeleted, String startDate, String endDate);
	//
	@Query(value ="select * from leave_info ln where ln.client_id=?1 and ln.is_delete= ?2 and ln.leave_start_date >= ?3 and ln.leave_start_date <= ?4", nativeQuery=true)
	public List<LeaveInfo> findByClientIdAndLeaveStartDateBetweenAndIsDelete(Long clientId, short notDeleted, String startDate, String endDate);

	
	
}
