package com.zietaproj.zieta.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zietaproj.zieta.dto.ScreensMasterDTO;
import com.zietaproj.zieta.dto.TaskMasterDTO;
import com.zietaproj.zieta.model.ScreensMaster;
import com.zietaproj.zieta.model.TaskMaster;
import com.zietaproj.zieta.repository.ScreensMasterRepository;
import com.zietaproj.zieta.repository.TaskMasterRepository;
import com.zietaproj.zieta.service.ScreensMasterService;

@Service
public class ScreensMasterServiceImpl implements ScreensMasterService {

	@Autowired
	ScreensMasterRepository screensMasterRepository;
	
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
			screensMasterDTOs.add(screensMasterDTO);
		}
		return screensMasterDTOs;
	}
}
