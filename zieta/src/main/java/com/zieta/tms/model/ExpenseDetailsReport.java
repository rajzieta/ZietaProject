package com.zieta.tms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
@Data
@Entity
public class ExpenseDetailsReport {
	 @Id
	 @Column(name="row_num")
	 public Long id;
	 public String  exp_id;
	 public String exp_entry_id;
	 public String exp_date;
	 public String exp_type;
	 public String expense_type;
	 public String exp_currency;
	 
	 public String currency_code;
	 public String exp_amount;
	 public String exp_country;
	 public String country_name;
	 public String exp_city;
	 public String exchange_rate;
	 
	 public String exp_amt_inr;
	 public String exp_desc;
	 public String status_id;
	 public String status_desc;
	 public String file_name;
	 public String file_path ;
	 public String user_id;
	 public String emp_id;
	 public String emp_name;
	 public String exp_category;
	 public String project_id;
	 public String project_name;
	 public String orgunit_id;
	 public String org_node_name;
	 public String exp_heading;
	
}
