package com.zietaproj.zieta.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.TSInfo;

@Repository
public interface TSInfoRepository extends JpaRepository<TSInfo, Long>{
	
	public List<TSInfo> findByClientIdAndUserIdAndTsDateBetweenOrderByTaskActivityIdAscTsInfoIdAsc(Long clientId, Long userId, Date startDate, Date endDate);

	
	public List<TSInfo> findByClientIdAndUserIdAndIsDeleteAndTsDateBetweenOrderByTaskActivityIdAscTsInfoIdAsc(
			Long clientId, Long userId, short notDeleted, Date startDate, Date endDate);

}
