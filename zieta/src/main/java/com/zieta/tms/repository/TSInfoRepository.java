package com.zieta.tms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.AccessTypeMaster;
import com.zieta.tms.model.TSInfo;
import com.zieta.tms.model.TaskInfo;


@Repository
public interface TSInfoRepository extends JpaRepository<TSInfo, Long>{
	
	public List<TSInfo> findByClientIdAndUserIdAndTsDateBetweenOrderByTaskActivityIdAscIdAsc(Long clientId, Long userId, Date startDate, Date endDate);

	
	public List<TSInfo> findByClientIdAndUserIdAndIsDeleteAndTsDateBetweenOrderByTaskActivityIdAscIdAsc(
			Long clientId, Long userId, short notDeleted, Date startDate, Date endDate);
	
	@Query(value="select * from task_info where ext_id=?1", nativeQuery=true)
	TaskInfo findByExtId(String extId);
}
	
