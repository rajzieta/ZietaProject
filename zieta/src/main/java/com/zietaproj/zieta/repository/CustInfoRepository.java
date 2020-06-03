package com.zietaproj.zieta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.CustInfo;


@Repository
public interface CustInfoRepository extends JpaRepository<CustInfo, Long> {
	
	List<CustInfo>  findByClientId(Long clientId);
	

}
