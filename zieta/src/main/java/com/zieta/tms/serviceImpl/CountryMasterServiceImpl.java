package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zieta.tms.dto.CountryMasterDTO;
import com.zieta.tms.dto.CurrencyMasterDTO;
import com.zieta.tms.model.CountryMaster;
import com.zieta.tms.model.CurrencyMaster;
import com.zieta.tms.repository.CountryMasterRepository;
import com.zieta.tms.repository.CurrencyMasterRepository;
import com.zieta.tms.service.CountryMasterService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class CountryMasterServiceImpl implements CountryMasterService {

	@Autowired
	CountryMasterRepository countryMasterRepository;
	
	@Autowired
	CurrencyMasterRepository currencyMasterRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<CountryMasterDTO> getAllCountryMaster() {
		List<CountryMaster> countryInfos= countryMasterRepository.findAll();
		List<CountryMasterDTO> countryInfoDTOs = new ArrayList<CountryMasterDTO>();
		CountryMasterDTO countryInfoDTO = null;
		for (CountryMaster countryInfo : countryInfos) {
			countryInfoDTO = modelMapper.map(countryInfo, CountryMasterDTO.class);
			countryInfoDTOs.add(countryInfoDTO);
		}
		return countryInfoDTOs;
}
	
	
	@Override
	public List<CurrencyMasterDTO> getAllCurrencyMaster() {
		List<CurrencyMaster> currencyInfos= currencyMasterRepository.findAll();
		List<CurrencyMasterDTO> currencyInfoDTOs = new ArrayList<CurrencyMasterDTO>();
		CurrencyMasterDTO currencyMasterDTO = null;
		for (CurrencyMaster currencyInfo : currencyInfos) {
			currencyMasterDTO = modelMapper.map(currencyInfo, CurrencyMasterDTO.class);
			currencyInfoDTOs.add(currencyMasterDTO);
		}
		return currencyInfoDTOs;
}
	
}
