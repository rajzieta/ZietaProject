package com.zietaproj.zieta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.ClientInfo;


@Repository
public interface ClientInfoRepository extends JpaRepository<ClientInfo, Long> {
	
	 @Query( "select o from ClientInfo o where o.IS_DELETE= :isDelete" )
	  List<ClientInfo> findAllWithDeleteFlag(@Param("isDelete") boolean isDelete);

}
