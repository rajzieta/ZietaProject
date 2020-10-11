package com.zieta.tms.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name = "EXPENSE_ENTRIES")
@Data
public class ExpenseEntries extends BaseEntity implements Serializable {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name="exp_id")
	private Long expId;
	
	@Column(name="exp_date")
	private Date expDate;
	
	@Column(name="exp_type")
	private Long expType;
	
	@Column(name="exp_amount")
	private float expAmount;
	
	@Column(name="exp_city")
	private String expCity;
	
	@Column(name="exp_country")
	private Long expCountry;
	
	@Column(name="exp_currency")
	private Long expCurrency;
	
	@Column(name="exchange_rate")
	private float exchangeRate;
	
	@Column(name="exp_amt_inr")
	private float expAmtInr;
	
	@Column(name="exp_desc")
	private String expDesc;
	
	@Column(name="file_name")
	private String fileName;
	
	@Column(name="file_path")
	private String filePath;
	
}
