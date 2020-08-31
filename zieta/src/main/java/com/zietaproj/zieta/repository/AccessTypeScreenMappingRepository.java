package com.zietaproj.zieta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.AccessTypeScreenMapping;

@Repository
public interface AccessTypeScreenMappingRepository extends JpaRepository<AccessTypeScreenMapping, Long> {
	
	  @Query( "select o.screenId from AccessTypeScreenMapping o where o.clientId= :clientId AND o.accessTypeId= :accessTypeId")
	  List<Long> findByClientIdANDAccessTypeId(@Param("clientId") Long clientId,
			  @Param("accessTypeId") Long accessTypeId);

	List<AccessTypeScreenMapping> findByIsDelete(short notDeleted);
	  
	  

}
