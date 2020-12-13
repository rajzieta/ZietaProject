package com.zieta.tms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeSheetReportDTO {
	
	public String emp_id ;
	public String team;
	public String emp_name;
	public String project_name;
	public String task_name;
	public String activity_desc;
	public String planned_hours;
	public String actual_hours;
	public String ts_date;
	public String submit_date;
	public String submitted_hours;
	public String approved_hours;

}
