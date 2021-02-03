package com.zieta.tms.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
@Data
@Entity
public class ExpenseSummaryReport {
	 @Id
	 public String exp_id;
	 public String user_id;
	 public String employee_name;
	 public String exp_category;
	 public String project_id;
	 public String project_name;
	 public String orgunit_id;
	 public String org_node_name;
	 public String exp_heading;
	 public String exp_start_date;
	 public String exp_end_date;
	 public String exp_posting_date;
	 public String exp_amount;
	 public String approved_amt;
	 public String approver_id;
	 public String approver_name;
	 public String status_id;
	 public String status_desc;
	 public String emp_id;
	 
	 
	 
}
