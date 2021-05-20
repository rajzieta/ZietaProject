package com.zieta.tms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.OrgUnitUserMapping;
import com.zieta.tms.model.UserDetails;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long>{
	
	List<UserDetails> findByClientIdAndUserId(Long client_id, short notDeleted);
	
	Optional<UserDetails> findByUserId(long usreId);
	
	UserDetails findUserDetailsByUserId(long usreId);	
	

}
