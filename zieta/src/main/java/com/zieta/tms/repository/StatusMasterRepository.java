package com.zieta.tms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.dto.ExpenseEntriesDTO;
import com.zieta.tms.dto.ExpenseInfoDTO;
import com.zieta.tms.model.StatusMaster;


@Repository
public interface StatusMasterRepository extends JpaRepository<StatusMaster, Long>  {

	List<StatusMaster> findByClientIdAndStatusType(Long clientId, String statusType);

	List<StatusMaster> findByIsDelete(short notDeleted);

	List<StatusMaster> findByClientIdAndStatusTypeAndIsDelete(Long clientId, String statusType, short notDeleted);
	
	StatusMaster findByClientIdAndStatusTypeAndStatusCodeAndIsDelete(Long clientId, String statusType, String statusCode, short notDeleted);
	
	StatusMaster findByClientIdAndStatusTypeAndIsDefaultAndIsDelete(Long clientId, String statusType, boolean isDefault, short notDeleted);

	List<StatusMaster> findByClientIdAndIsDelete(Long clientId, short notDeleted);

	

}
