package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.CustInfo;


@Repository
public interface CustInfoRepository extends JpaRepository<CustInfo, Long> {
	
	List<CustInfo>  findByClientId(Long clientId);

	List<CustInfo> findByIsDelete(short notDeleted);

	List<CustInfo> findByClientIdAndIsDelete(Long clientId, short notDeleted);
	

}
