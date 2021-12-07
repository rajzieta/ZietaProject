package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.ClientInfo;


@Repository
public interface ClientInfoRepository extends JpaRepository<ClientInfo, Long> {
	
	// @Query( "select o from ClientInfo o where o.IS_DELETE= :isDelete" )
	  List<ClientInfo> findByIsDelete(Short notDeleted);
	  
	  @Query( value ="select * from client_info where id=?1 and is_delete=?2",nativeQuery=true)
	  ClientInfo findByClientIdAndIsDelete(long id,short notDeleted );

}
