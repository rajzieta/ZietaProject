package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.zietaproj.zieta.dto.ScreensMasterDTO;
import com.zietaproj.zieta.dto.TaskMasterDTO;
import com.zietaproj.zieta.model.ProjectMaster;
import com.zietaproj.zieta.model.ScreensMaster;
import com.zietaproj.zieta.model.TaskTypeMaster;
import com.zietaproj.zieta.repository.ScreensMasterRepository;
import com.zietaproj.zieta.repository.TaskTypeMasterRepository;
import com.zietaproj.zieta.request.ScreensMasterAddRequest;
import com.zietaproj.zieta.request.ScreensMasterEditRequest;
import com.zietaproj.zieta.service.ScreensMasterService;

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
			screensMasterDTO.setScreen_code(screensMaster.getScreen_code());
			screensMasterDTO.setScreen_category(screensMaster.getScreen_category());
			screensMasterDTO.setScreen_desc(screensMaster.getScreen_desc());
			screensMasterDTO.setScreen_title(screensMaster.getScreen_title());
			screensMasterDTOs.add(screensMasterDTO);
		}
		return screensMasterDTOs;
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

