package com.zieta.tms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.ConnectionMasterInfo;
import com.zieta.tms.model.OrgUnitUserMapping;
import com.zieta.tms.model.UserInfo;

@Repository
public interface ConnectionMasterInfoRepository extends JpaRepository<ConnectionMasterInfo, Long>{
	
	//ConnectionMasterInfo findByEmail(String email);
	
	//ConnectionMasterInfo findByClientAndIsDelete(Long clientId, short notDeleted);
	
	List<ConnectionMasterInfo> findByClientIdAndIsDelete(Long client_id, short notDeleted);

	List<ConnectionMasterInfo> findByIsDelete(short notDeleted);

	List<Long> findByClientId(Long clientId);
	
	@Query(value="select * from connection_master where client_id=?1 and connection_name=?2 and is_delete=?3", nativeQuery=true)
	List<ConnectionMasterInfo> findByClientIdAndConnectionNameAndNotDeleted(Long clientId,String connName,short notDeleted);

	/*@Query(value="select * from user_info where id=?1", nativeQuery=true)
	UserInfo findByUserId(long userId);
	
	UserInfo findByExpTemplateId(Long expTemplateId);*/

	


	
	

}
