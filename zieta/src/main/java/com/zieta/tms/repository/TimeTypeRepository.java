package com.zieta.tms.repository;

import java.util.List;

//import org.hibernate.type.TimeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.TimeType;

@Repository
public interface TimeTypeRepository extends JpaRepository<TimeType, Long> {
	
	List<TimeType> findByClientId(long client_id);

	List<TimeType> findByIsDelete(short notDeleted);

	List<TimeType> findByClientIdAndIsDelete(Long client_id, short notDeleted);

}
