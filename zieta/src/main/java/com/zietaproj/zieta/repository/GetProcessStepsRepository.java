package com.zietaproj.zieta.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.ProcessSteps;

@Repository
public interface GetProcessStepsRepository extends PagingAndSortingRepository<ProcessSteps, Long> {

}
