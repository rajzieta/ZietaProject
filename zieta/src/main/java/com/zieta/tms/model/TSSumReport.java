package com.zieta.tms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
@Data
@Entity
public class TSSumReport {
	@Id
    @Column(name = "row_num")
    private Integer id;
	public String emp_id ;
	public Integer is_active;
	public String team_id;
	public String team;
	public String employee_name;
	public String proj_id;
	public String project_name;
	public String tid;
	public String task_name;
	public String activity_desc;
	public String total_submitted_time;
	public String submitted_hrs;
	public String approved_hrs;
	public String total_planned_hrs;
	public String total_actual_hrs;
	
	
}
