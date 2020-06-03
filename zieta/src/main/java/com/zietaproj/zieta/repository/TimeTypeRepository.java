package com.zietaproj.zieta.repository;

import java.util.List;

//import org.hibernate.type.TimeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.TimeType;

@Repository
public interface TimeTypeRepository extends JpaRepository<TimeType, Long> {
	
	List<TimeType> findByClientId(long client_id);

}
