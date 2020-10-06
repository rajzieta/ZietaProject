package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.TSTimeEntries;

@Repository
public interface TSTimeEntriesRepository extends JpaRepository<TSTimeEntries, Long> {

	List<TSTimeEntries> findByTsId(Long tsId);

	List<TSTimeEntries> findByTsIdAndIsDelete(Long tsId, short notDeleted);
	
	List<TSTimeEntries> findByTsIdAndStatusIdAndIsDelete(Long tsId, long statusId, short notDeleted);

	
}
