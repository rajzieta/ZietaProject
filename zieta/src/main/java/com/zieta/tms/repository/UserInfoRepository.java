package com.zieta.tms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.OrgUnitUserMapping;
import com.zieta.tms.model.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long>{
	
	UserInfo findByEmail(String email);
	
	UserInfo findByEmailAndIsDelete(String email, short notDeleted);
	
	List<UserInfo> findByClientIdAndIsDelete(Long client_id, short notDeleted);

	List<UserInfo> findByIsDelete(short notDeleted);

	List<Long> findByClientId(Long clientId);

	@Query(value="select * from user_info where id=?1", nativeQuery=true)
	UserInfo findByUserId(long userId);
	
	@Query(value="select * from user_info where ext_id=?1", nativeQuery=true)
	UserInfo findByExtId(String extId);
	
	@Query(value="select * from user_info where ext_id=?1 and client_id=?2", nativeQuery=true)
	UserInfo findByExtIdAndClientId(String extId, Long clientId);
	
	//UserInfo findByExpTemplateId(Long expTemplateId);

	


	
	

}
