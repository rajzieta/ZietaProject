package com.zietaproj.zieta.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.StatusMaster;


@Repository
public interface StatusMasterRepository extends JpaRepository<StatusMaster, Long>  {

	List<StatusMaster> findByClientIdAndStatusType(Long clientId, String statusType);

	
	

}
