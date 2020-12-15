package com.zieta.tms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class TSReport {
	@Id
    @Column(name = "row_num")
    private Integer id;
	public String emp_id ;
	public String team_id;
	public String team;
	public String emp_name;
	public String project_id;
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
