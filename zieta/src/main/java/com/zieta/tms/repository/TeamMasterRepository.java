package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.SkillsetMaster;
import com.zieta.tms.model.TeamMaster;

@Repository
public interface TeamMasterRepository extends JpaRepository<TeamMaster, Long> {

	List<TeamMaster> findByClientIdAndIsDelete(Long clientId, short notDeleted);

	
}
