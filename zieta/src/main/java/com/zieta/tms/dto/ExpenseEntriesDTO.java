package com.zieta.tms.dto;

import java.util.Date;

import com.zieta.tms.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseEntriesDTO extends BaseEntity {

	
	private Long id;
	private Long expId;
	private Date expDate;
	private Long expType;
	private float expAmount;
	private String expCity;
	private Long expCountry;
	private Long expCurrency;
	private float exchangeRate;
	private float expAmtInr;
	private String expDesc;
	private String fileName;
	private String filePath;

	private String expenseType;
	private String expCountryName;
	private String expCountryCode;
	private String expCurrencyType;
	private String expCurrencyCode;
}
