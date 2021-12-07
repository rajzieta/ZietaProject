package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.ActivityMaster;

@Repository
public interface ActivityMasterRepository extends JpaRepository<ActivityMaster, Long>{

	List<ActivityMaster> findByClientId(long client_id);

	List<ActivityMaster> findByClientIdAndIsDelete(Long clientId, short notDeleted);

	List<ActivityMaster> findByIsDelete(short notDeleted);
	
	@Query(value ="select * from activity_master where id=?1", nativeQuery=true)
	ActivityMaster findByActivityId(Long id);
}
