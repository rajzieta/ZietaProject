package com.zieta.tms.serviceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.zieta.tms.model.TimeSheetReport;
import com.zieta.tms.repository.TimeSheetReportRepository;
import com.zieta.tms.service.TimeSheetReportService;
import com.zieta.tms.util.CurrentWeekUtil;
import com.zieta.tms.util.ReportUtil;
import com.zieta.tms.util.TSMUtil;

@Service
public class TimeSheetReportServiceImpl implements TimeSheetReportService {
	
	@Autowired
	private TimeSheetReportRepository timeSheetReportRepository;

	

	@Override
	public Page<TimeSheetReport> findAll(long clientId, long projectId,
			String empId, String stateName, Date startDate, Date endDate, Integer pageNo, Integer pageSize) {

		boolean isDatesValid = TSMUtil.validateDates(startDate, endDate);

		// defaulting to the current week date range, when there is no date range
		// mentioned from front end.
		if (!isDatesValid) {
			CurrentWeekUtil currentWeek = new CurrentWeekUtil(new Locale("en", "IN"));
			startDate = currentWeek.getFirstDay();
			endDate = currentWeek.getLastDay();
		}else {
			startDate = TSMUtil.getFormattedDate(startDate);
			endDate =  TSMUtil.getFormattedDate(endDate);
			Calendar c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, 1);
			endDate = c.getTime();
		}
		
		return findAll(startDate, endDate, stateName, empId,clientId, pageNo, pageSize);
	}


	@Override
	public ByteArrayInputStream downloadTimeSheetReport(HttpServletResponse response,long clientId,
			long projectId, String stateName, String empId, Date startDate, Date endDate ) throws IOException {
		ReportUtil report = new ReportUtil();
		
		List<TimeSheetReport> timeSheetReportList = downloadAll(startDate, endDate, stateName, empId, clientId);
		return report.downloadReport(response, timeSheetReportList);
		
	}
	
	public Page<TimeSheetReport> findAll(Date startDate, Date endDate,String stateName,String empId,long clientId, 
			Integer pageNo, Integer pageSize){
		
		Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<TimeSheetReport> page = timeSheetReportRepository.findAll(new Specification<TimeSheetReport>() {
        	
            @Override
            public Predicate toPredicate(Root<TimeSheetReport> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(stateName!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("stateName"), "%" + stateName + "%")));
                }
                if(startDate!= null && endDate!=null){
                    predicates.add(criteriaBuilder.between(root.get("requestDate"),startDate,endDate));
                }
                if(empId!= null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("empId"), empId)));
                }
                if(clientId!=0) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("clientId"), clientId)));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        },pageable);
        
        return page;
    }
	

	public List<TimeSheetReport> downloadAll(Date startDate, Date endDate,String stateName,String empId,long clientId){
		
		List<TimeSheetReport> downloadableReportList = timeSheetReportRepository.findAll(new Specification<TimeSheetReport>() {
        	
            @Override
            public Predicate toPredicate(Root<TimeSheetReport> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(stateName!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("stateName"), "%" + stateName + "%")));
                }
                if(startDate!= null && endDate!=null){
                    predicates.add(criteriaBuilder.between(root.get("requestDate"),startDate,endDate));
                }
                if(empId!= null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("empId"), empId)));
                }
                if(clientId!=0) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("clientId"), clientId)));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
        
        return downloadableReportList;
    }
	
}
