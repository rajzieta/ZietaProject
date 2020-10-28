package com.zieta.tms.dto;

import java.util.List;

import com.zieta.tms.model.ExpenseEntries;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseEntryMetaData {
	
	ExpenseEntries expenseEntriesList;
	String expType;
	String expCurrency;
	String expCountry;

}
