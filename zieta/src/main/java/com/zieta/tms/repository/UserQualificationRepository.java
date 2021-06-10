package com.zieta.tms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zieta.tms.dto.UserQualificationDTO;
import com.zieta.tms.model.OrgUnitUserMapping;
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.model.UserQualification;

@Repository
public interface UserQualificationRepository extends JpaRepository<UserQualification, Long>{
	
	List<Long> findByClientId(Long clientId);
	List<UserQualification> findByUserId(Long userId);
	@Query(value="select * from user_qualifications where user_id=?1 ", nativeQuery=true)
	Optional<UserQualification> findByUId(Long userId);
	
	
	

}
