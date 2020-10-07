package com.zieta.tms.service;

import java.util.List;

import javax.validation.Valid;

import com.zieta.tms.dto.RoleMasterDTO;
import com.zieta.tms.model.RoleMaster;
import com.zieta.tms.request.RoleMasterEditRequest;
import com.zieta.tms.response.RolesByClientResponse;

public interface RoleMasterService {

	public List<RoleMasterDTO> getAllRoles();

	public void addRolemaster(RoleMaster rolemaster);

	public List<RolesByClientResponse> getRolesByClient(Long client_id);

	public void editUserRolesById(@Valid RoleMasterEditRequest rolemastereditrequest) throws Exception;

	public void deleteUserRolesById(Long id, String modifiedBy) throws Exception;
}
