package com.zietaproj.zieta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.StateTypeMaster;

@Repository
public interface StateTypeMasterRepository extends JpaRepository<StateTypeMaster, Long> {

	public StateTypeMaster findByStateName(String stateName);
}
