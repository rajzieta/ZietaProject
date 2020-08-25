package com.zietaproj.zieta.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long>{
	
	UserInfo findByEmail(String email);
	
	List<UserInfo> findByClientIdAndIsDelete(Long client_id, short notDeleted);

	List<UserInfo> findByIsDelete(short notDeleted);


	
	

}
