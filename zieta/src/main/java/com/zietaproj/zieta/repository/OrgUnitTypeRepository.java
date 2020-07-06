package com.zietaproj.zieta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.OrgUnitTypeMaster;


@Repository
public interface OrgUnitTypeRepository extends JpaRepository<OrgUnitTypeMaster, Long>{

}
