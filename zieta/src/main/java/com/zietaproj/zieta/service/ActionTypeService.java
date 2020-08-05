package com.zietaproj.zieta.service;

import java.util.List;

import javax.validation.Valid;

import com.zietaproj.zieta.dto.ActionTypeDTO;
import com.zietaproj.zieta.model.ActionTypeMaster;

public interface ActionTypeService {

	public List<ActionTypeDTO> getAllActionTypes();

	public void addActionTypes(@Valid ActionTypeMaster actionTypemaster);

	public void editActionTypesById(@Valid ActionTypeDTO actiontypeDTO) throws Exception;

	public void deleteActionTypesById(Long id) throws Exception;

	
}
