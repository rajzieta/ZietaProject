package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.CustInfo;
import com.zieta.tms.model.UserInfo;


@Repository
public interface CustInfoRepository extends JpaRepository<CustInfo, Long> {
	
	List<CustInfo>  findByClientId(Long clientId);

	List<CustInfo> findByIsDelete(short notDeleted);

	List<CustInfo> findByClientIdAndIsDelete(Long clientId, short notDeleted);
	
	@Query(value="select * from cust_info where ext_id=?1", nativeQuery=true)
	CustInfo findByExtId(String extId);
	
	@Query(value="select * from cust_info where ext_id=?1 and client_id=?2", nativeQuery=true)
	CustInfo findByExtIdAndClientId(String extId, Long clientId);
	

}
