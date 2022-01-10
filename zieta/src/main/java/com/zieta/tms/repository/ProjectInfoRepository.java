package com.zieta.tms.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zieta.tms.dto.ProjectMasterDTO;
import com.zieta.tms.model.ClientInfo;
import com.zieta.tms.model.ProjectInfo;

public interface ProjectInfoRepository extends JpaRepository<ProjectInfo, Long> {
	
	List<ProjectInfo> findByClientId(long clientId);
	
	List<ProjectInfo> findByProjectManager(long projectManager);

	List<ProjectInfo> findByIsDelete(short notDeleted);

	List<ProjectInfo> findByProjectManagerAndIsDelete(long projectManagerId, short notDeleted);

	List<ProjectInfo> findByClientIdAndIsDelete(Long clientId, short notDeleted);

	
	@Query(value="select * from  project_info  WHERE id=?1 AND is_delete=?2", nativeQuery=true)
	ProjectInfo findByProjectInfoIdAndIsDelete(Long projectId, short notDeleted);
	
	//Optional<ProjectInfo> findByIdAndIsDelete(Long Id, short notDeleted);
	
	ProjectInfo findByExtId(String extId);
	
	//ProjectInfo findById(Long id);
	
	ProjectInfo findByExtIdAndClientId(String extId, Long clientId);

	ProjectInfo findByProjectInfoIdAndClientIdAndIsDelete(Long projectInfoId, Long clientId, short notDeleted);

	@Transactional
	@Modifying
	@Query("UPDATE ProjectInfo SET extFetchDate=:extFetchDate WHERE projectInfoId=:projectInfoId AND clientId=:clientId ")
	void updateExtFetchDateByProjectInfoIdAndClientId(@Param("projectInfoId") Long projectInfoId,
			@Param("clientId") Long clientId,@Param("extFetchDate") Date extFetchDate);
}
