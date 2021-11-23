package com.zieta.tms.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.ExpenseEntries;
import com.zieta.tms.model.ExpenseInfo;
import com.zieta.tms.model.ExpenseTemplate;
import com.zieta.tms.model.ScreenCategoryMaster;
import com.zieta.tms.model.StatusMaster;
import com.zieta.tms.model.UserDetails;

@Repository
public interface ScreenCategoryMasterRepository extends JpaRepository<ScreenCategoryMaster, Long>{

	@Query(value ="select * from screens_category_master", nativeQuery=true)
	List<ScreenCategoryMaster> findAllScreenCategoryMaster();
	
	ScreenCategoryMaster findScreenCategoryMasterById(long id);
	
}
