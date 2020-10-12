package com.zieta.tms.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zieta.tms.dto.CountryMasterDTO;
import com.zieta.tms.dto.CurrencyMasterDTO;
import com.zieta.tms.service.CountryMasterService;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(tags = "Country Master API")
public class CountryMasterController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CountryMasterController.class);

	@Autowired
	CountryMasterService countryMasterService;
	
	@ApiOperation(value = "List Country Info", notes = "Table reference:country_master")
	@RequestMapping(value = "getAllCountryMaster", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CountryMasterDTO> getAllCountryMaster() {
		List<CountryMasterDTO> countryInfos = null;
		try {
			countryInfos = countryMasterService.getAllCountryMaster();

		} catch (Exception e) {
			LOGGER.error("Error Occured in CountryMasterController#getAllCountryMaster", e);
		}
		return countryInfos;
	}
	
	
	@ApiOperation(value = "List Currency Info", notes = "Table reference:currency_master")
	@RequestMapping(value = "getAllCurrencyMaster", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CurrencyMasterDTO> getAllCurrencyMaster() {
		List<CurrencyMasterDTO> currencyInfos = null;
		try {
			currencyInfos = countryMasterService.getAllCurrencyMaster();

		} catch (Exception e) {
			LOGGER.error("Error Occured in CurrencyMasterController#getAllCurrencyMaster", e);
		}
		return currencyInfos;
	}
}
