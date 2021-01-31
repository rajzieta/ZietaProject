package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.StatusMaster;


@Repository
public interface StatusMasterRepository extends JpaRepository<StatusMaster, Long>  {

	List<StatusMaster> findByClientIdAndStatusType(Long clientId, String statusType);

	List<StatusMaster> findByIsDelete(short notDeleted);

	List<StatusMaster> findByClientIdAndStatusTypeAndIsDelete(Long clientId, String statusType, short notDeleted);
	
	StatusMaster findByClientIdAndStatusTypeAndStatusCodeAndIsDelete(Long clientId, String statusType, String statusCode, short notDeleted);
	
	StatusMaster findByClientIdAndStatusTypeAndIsDefaultAndIsDelete(Long clientId, String statusType, boolean isDefault, short notDeleted);

	List<StatusMaster> findByClientIdAndIsDelete(Long clientId, short notDeleted);

	List<Long> findByClientIdAndStatusTypeAndStatusCodeNotAndIsDelete(Long clientId, String statusType,
			String statusCode, short notDeleted);
	
	
	@Query("select id from StatusMaster where clientId=?1 and statusCode in ?2 and statusType=?3 ")
	List<Long> getStatusIdByClientByCodeByType(Long clientId, List<String> statusCode, String statustype);



	

}
