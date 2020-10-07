package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.ProjectMaster;
import com.zieta.tms.model.RoleMaster;


@Repository
public interface ProjectMasterRepository extends JpaRepository<ProjectMaster, Long>{

	List<ProjectMaster> findByClientId(long client_id);

	List<ProjectMaster> findByClientIdAndIsDelete(Long clientId, short notDeleted);

	List<ProjectMaster> findByIsDelete(short notDeleted);
	
}
