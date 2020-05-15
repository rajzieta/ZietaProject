package com.zietaproj.zieta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.AccessTypeMaster;

@Repository
public interface AccessTypeMasterRepository extends JpaRepository<AccessTypeMaster, Long> {

}
