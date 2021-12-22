package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.zieta.tms.model.ProjectMilestone;

@Repository(value = "ProjectMilestoneRepository")
@Transactional
public interface ProjectMilestoneRepository extends JpaRepository<ProjectMilestone, Long> {

	List<ProjectMilestone> findByClientIdAndIsDelete(Long client_id, short notDeleted);

	@Modifying
	@Query("UPDATE ProjectMilestone SET isDelete=1,modifiedBy=:modifiedBy WHERE id=:id ")
	public void inactiveByProjectMilestone(@Param("id") Long id, @Param("modifiedBy") String modifiedBy);
}
