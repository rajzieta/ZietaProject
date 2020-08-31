package com.zietaproj.zieta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.ActionTypeMaster;

@Repository
public interface ActionTypeMasterRepository extends JpaRepository<ActionTypeMaster, Long> {
	
	public ActionTypeMaster findByActionName(String actionName);

}
