package com.zieta.tms.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.UserRoleMaster;

@Repository
public interface UserRoleMasterRepository extends JpaRepository<UserRoleMaster, Long>{



}
