package com.zieta.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.OrgUnitTypeMaster;


@Repository
public interface OrgUnitTypeRepository extends JpaRepository<OrgUnitTypeMaster, Long>{

}
