package com.zieta.tms.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.zieta.tms.dto.ScreensMasterDTO;
import com.zieta.tms.model.ScreensMaster;
import com.zieta.tms.repository.ScreensMasterRepository;
import com.zieta.tms.request.ScreensMasterAddRequest;
import com.zieta.tms.request.ScreensMasterEditRequest;
import com.zieta.tms.service.ScreensMasterService;

@Service
@Transactional
public class ScreensMasterServiceImpl implements ScreensMasterService {

	@Autowired
	ScreensMasterRepository screensMasterRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<ScreensMasterDTO> getAllScreens() {
		List<ScreensMaster> screensMasters= screensMasterRepository.findAll();
		List<ScreensMasterDTO> screensMasterDTOs = new ArrayList<ScreensMasterDTO>();
		ScreensMasterDTO screensMasterDTO = null;
		for (ScreensMaster screensMaster : screensMasters) {
			screensMasterDTO = new ScreensMasterDTO();
			screensMasterDTO.setId(screensMaster.getId());
			screensMasterDTO.setScreenCode(screensMaster.getScreenCode());
			screensMasterDTO.setScreenCategory(screensMaster.getScreenCategory());
			screensMasterDTO.setScreenDesc(screensMaster.getScreenDesc());
			screensMasterDTO.setScreenTitle(screensMaster.getScreenTitle());
			screensMasterDTOs.add(screensMasterDTO);
		}
		return screensMasterDTOs;
	}
	
	
	
	@Override
	public List<ScreensMasterDTO> getAllScreensByCategory(String screenCategory) {
		List<ScreensMaster> screensByCategoryList = screensMasterRepository.findByScreenCategory(screenCategory);
		List<ScreensMasterDTO> screensByCategoryResponseList = new ArrayList<>();
		ScreensMasterDTO screensByCategoryResponse = null;
		for (ScreensMaster screensByCategory : screensByCategoryList) {
			screensByCategoryResponse = modelMapper.map(screensByCategory, 
					ScreensMasterDTO.class);
			screensByCategoryResponseList.add(screensByCategoryResponse);
		}

		return screensByCategoryResponseList;
		
		
	}
	
	@Override
	public void addScreensmaster(ScreensMasterAddRequest screensMasterParam)
	{
		ScreensMaster screensmaster = modelMapper.map(screensMasterParam, ScreensMaster.class);
		screensMasterRepository.save(screensmaster);
	}
	
	@Override
	public void updateScreensmaster(ScreensMasterEditRequest screensMasterParam)
	{
		ScreensMaster screensmaster = modelMapper.map(screensMasterParam, ScreensMaster.class);
		screensMasterRepository.save(screensmaster);
	}
	
	@Override
	public void deleteById(Long id) {
		screensMasterRepository.deleteById(id);
	}

	@Override
	public List<ScreensMaster> getScreensByIds(List<Long> ids){
		
		return screensMasterRepository.findAllById(ids);
		
	}
	
}

