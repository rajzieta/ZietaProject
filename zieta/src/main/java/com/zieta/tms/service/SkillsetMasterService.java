package com.zieta.tms.service;

import java.util.List;

import javax.validation.Valid;

import com.zieta.tms.dto.SkillsetMasterDTO;
import com.zieta.tms.model.SkillsetMaster;
import com.zieta.tms.model.StatusMaster;

public interface SkillsetMasterService {



public void addSkillmaster(@Valid SkillsetMaster skillmaster);

public List<SkillsetMasterDTO> getAllSkillset();

public void deleteSkillsetById(Long id) throws Exception;

	
}