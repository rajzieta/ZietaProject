package com.zietaproj.zieta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.ClientInfo;


@Repository
public interface ClientInfoRepository extends JpaRepository<ClientInfo, Long> {

}
