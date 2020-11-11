package com.zieta.tms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.ExpenseEntries;
import com.zieta.tms.model.ExpenseInfo;

@Repository
public interface ExpenseInfoRepository extends JpaRepository<ExpenseInfo, Long>{

	List<ExpenseInfo> findByIsDelete(short notDeleted);

	List<ExpenseInfo> findByClientIdAndUserIdAndIsDelete(Long clientId, Long userId, short notDeleted);

	//List<ExpenseInfo> findByStatusIdAndIsDelete(long statusId, short notDeleted);

	List<ExpenseInfo> findByClientIdAndUserIdAndStatusIdAndIsDelete(Long clientId, Long userId, long statusId,
			short notDeleted);

	//List<ExpenseInfo> findByClientIdAndUserIdAndStatusIdAndIsDelete(Long clientId, Long userId, Long statusId,
		//	short notDeleted);

	//List<ExpenseInfo> findByClientIdAndUserIdAndActionTypeNotIn(Long clientId, Long userId, List<Long> actionTypes);

	

	
}
