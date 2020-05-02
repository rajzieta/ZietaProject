package com.zietaproj.zieta.repository;

//import org.hibernate.type.TimeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.TimeType;

@Repository
public interface TimeTypeRepository extends JpaRepository<TimeType, Long> {

}
