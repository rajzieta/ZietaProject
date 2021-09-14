package com.zieta.tms.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.EmployeeAdvance;
import com.zieta.tms.model.ExpenseEntries;
import com.zieta.tms.model.ExpenseInfo;
import com.zieta.tms.model.StatusMaster;

@Repository
public interface EmployeeAdvanceRepository extends JpaRepository<EmployeeAdvance, Long>{

	//void save(@Valid EmployeeAdvance employeeAdvance);

	List<EmployeeAdvance> findByIsDelete(short notDeleted);
	
	@Query(value ="select * from employee_advance ea where ea.client_id=?1 and ea.user_id= ?2 and ea.is_delete=?3 and ea.advance_req_date >= ?4 and ea.advance_req_date <= ?5", nativeQuery=true)
	List<EmployeeAdvance> findByClientIdAndUserIdAndIsDeleteAndStartDateBetween(Long clientId, Long userId,
			short notDeleted, String startDate, String endDate);

	/*List<ExpenseInfo> findByClientIdAndUserIdAndIsDelete(Long clientId, Long userId, short notDeleted);

	List<ExpenseInfo> findByClientIdAndUserIdAndStatusIdAndIsDelete(Long clientId, Long userId, long statusId,
			short notDeleted);

	List<ExpenseInfo> findByClientIdAndUserIdAndStatusIdInAndIsDelete(Long clientId, Long userId,
			List<StatusMaster> statusIds, short notDeleted);
	
	@Query(value ="select * from expense_info ei where ei.client_id=?1 and ei.user_id= ?2 and ei.is_delete=?3 and ei.status_id not in ?4 and ei.exp_start_date >= ?5 and ei.exp_start_date <= ?6", nativeQuery=true)
	List<ExpenseInfo> findByClientIdAndUserIdAndIsDeleteAndStatusIdNotInAndExpStartDateBetween(Long clientId, Long userId,
			short notDeleted, List<Long> statusIds,String startDate, String endDate);
	
	List<ExpenseInfo> findByClientIdAndUserIdAndStatusId(Long clientId, Long userId, Long statuses);*/

	
}
