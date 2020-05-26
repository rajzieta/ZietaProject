package com.zietaproj.zieta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long>{
	
	UserInfo findByEmail(String email);
	
	

}
