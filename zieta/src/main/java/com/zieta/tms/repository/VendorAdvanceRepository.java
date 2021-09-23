package com.zieta.tms.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.ExpenseEntries;
import com.zieta.tms.model.ExpenseInfo;
import com.zieta.tms.model.ExpenseTemplate;
import com.zieta.tms.model.ScreenCategoryMaster;
import com.zieta.tms.model.StatusMaster;
import com.zieta.tms.model.VendorAdvance;
import com.zieta.tms.model.VendorInvoice;

@Repository
public interface VendorAdvanceRepository extends JpaRepository<VendorAdvance, Long>{

	List<VendorAdvance> findByClientIdAndIsDelete(Long clientId,Short notDeleted);
	
	List<VendorAdvance> findByIsDelete(Short notDeleted);
	
	VendorAdvance findVendorAdvanceByIdAndIsDelete(long id, short isDelete);
	
	@Query(value ="select * from vendor_advance where id=?1", nativeQuery=true)
	VendorAdvance findVendorAdvanceById(long id);
	
	@Query(value="select * from vendor_advance vndr where vndr.user_id= ?1 and vndr.is_delete=?2 and vndr.submit_date between ?3 and ?4", nativeQuery=true)	
	List<VendorAdvance> findByUserIdAndIsDeleteAndSubmitDateBetween(Long userId,Short notDeleted,Date startDate,Date endDate);
	
	
	@Query(value="select * from vendor_advance vndr where vndr.user_id= ?1 and vndr.is_delete=?2 and vndr.submit_date between ?3 and ?4", nativeQuery=true)	
	List<VendorAdvance> findByUserIdAndIsDeleteAndSubmitDateBetween(Long userId,Short notDeleted,String startDate,String endDate);

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
