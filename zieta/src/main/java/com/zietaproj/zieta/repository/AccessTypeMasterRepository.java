package com.zietaproj.zieta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.AccessTypeMaster;

@Repository
public interface AccessTypeMasterRepository extends JpaRepository<AccessTypeMaster, Long> {
	
	
	 @Query( "select o.accessType from AccessTypeMaster o where o.clientId= :clientId AND o.id= :id")
	  List<String> findByClientIdANDAccessTypeId(@Param("clientId") Long clientId,
			  @Param("id") Long id);

	List<AccessTypeMaster> findByClientId(Long clientId);

	List<AccessTypeMaster> findByIsDelete(short notDeleted);

	List<AccessTypeMaster> findByClientIdAndIsDelete(Long clientId, short notDeleted);

	//List<String> findByClientIdANDAccessTypeId(Long clientId, Long accessTypeId);

	//List<String> findByClientIdAndAccessTypeIdAndIsDelete(Long clientId, List<Long> accessIdList, short notDeleted);

}
