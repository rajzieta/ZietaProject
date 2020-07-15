package com.zietaproj.zieta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.TSTimeentries;

@Repository
public interface TSTimeEntriesRepository extends JpaRepository<TSTimeentries, Long> {

	List<TSTimeentries> findByTsId(Long tsId);

	
	
}
