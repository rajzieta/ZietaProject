package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.ProjectMaster;
import com.zieta.tms.model.RoleMaster;
import com.zieta.tms.model.StatusMaster;


@Repository
public interface ProjectMasterRepository extends JpaRepository<ProjectMaster, Long>{

	List<ProjectMaster> findByClientId(long client_id);

	List<ProjectMaster> findByClientIdAndIsDelete(Long clientId, short notDeleted);

	List<ProjectMaster> findByIsDelete(short notDeleted);
	
	@Query(value="select * from project_type_master where ext_id=?1", nativeQuery=true)
	ProjectMaster findByExtId(String extId);
	
	@Query(value="select * from project_type_master where ext_id=?1 and client_id=?2", nativeQuery=true)
	ProjectMaster findByExtIdAndClientId(String extId, Long clientId);
	
}
