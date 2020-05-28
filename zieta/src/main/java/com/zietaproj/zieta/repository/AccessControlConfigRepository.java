package com.zietaproj.zieta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.AccessControlConfig;

@Repository
public interface AccessControlConfigRepository extends JpaRepository<AccessControlConfig, Long> {
	
	  @Query( "select o.screenId from AccessControlConfig o where o.clientId= :clientId AND o.accessTypeId in :accessIds" )
	  List<Long> findByClientIdANDAccessTypeId(@Param("clientId") Long clientId,
			  @Param("accessIds") List<Long> accessIdList);

}
