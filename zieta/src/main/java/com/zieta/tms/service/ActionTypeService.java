package com.zieta.tms.service;

import java.util.List;

import javax.validation.Valid;

import com.zieta.tms.dto.ActionTypeDTO;
import com.zieta.tms.model.ActionTypeMaster;

public interface ActionTypeService {

	public List<ActionTypeDTO> getAllActionTypes();

	public void addActionTypes(@Valid ActionTypeMaster actionTypemaster);

	public void editActionTypesById(@Valid ActionTypeDTO actiontypeDTO) throws Exception;

	public void deleteActionTypesById(Long id) throws Exception;

	
}
