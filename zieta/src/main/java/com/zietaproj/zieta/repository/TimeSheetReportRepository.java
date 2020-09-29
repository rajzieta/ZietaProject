package com.zietaproj.zieta.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.zietaproj.zieta.model.TimeSheetReport;

@Repository
@Transactional
public interface TimeSheetReportRepository extends PagingAndSortingRepository<TimeSheetReport, String> {

}
