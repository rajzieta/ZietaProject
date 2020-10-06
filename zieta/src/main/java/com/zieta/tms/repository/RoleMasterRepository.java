package com.zieta.tms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.RoleMaster;


@Repository
public interface RoleMasterRepository extends JpaRepository<RoleMaster, Long> {

	List<RoleMaster> findByClientId(long client_id);

	List<RoleMaster> findByIsDelete(short notDeleted);

	List<RoleMaster> findByClientIdAndIsDelete(Long clientId, short notDeleted);
}
