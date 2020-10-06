package com.zieta.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.StateTypeMaster;

@Repository
public interface StateTypeMasterRepository extends JpaRepository<StateTypeMaster, Long> {

	public StateTypeMaster findByStateName(String stateName);
}
