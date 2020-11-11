package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.SkillsetMaster;


@Repository
public interface SkillsetMasterRepository extends JpaRepository<SkillsetMaster, Long>{

	List<SkillsetMaster> findByClientId(Long clientId);




}
