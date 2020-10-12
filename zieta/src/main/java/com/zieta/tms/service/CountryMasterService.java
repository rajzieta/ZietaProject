package com.zieta.tms.service;

import java.util.List;

import com.zieta.tms.dto.CountryMasterDTO;
import com.zieta.tms.dto.CurrencyMasterDTO;


public interface CountryMasterService {

	 public List<CountryMasterDTO> getAllCountryMaster();

	public List<CurrencyMasterDTO> getAllCurrencyMaster();

}
