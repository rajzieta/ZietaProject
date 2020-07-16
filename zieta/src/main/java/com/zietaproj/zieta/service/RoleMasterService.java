package com.zietaproj.zieta.service;

import java.util.List;

import javax.validation.Valid;

import com.zietaproj.zieta.dto.RoleMasterDTO;
import com.zietaproj.zieta.model.RoleMaster;
import com.zietaproj.zieta.request.RoleMasterEditRequest;
import com.zietaproj.zieta.response.RolesByClientResponse;

public interface RoleMasterService {

	public List<RoleMasterDTO> getAllRoles();

	public void addRolemaster(RoleMaster rolemaster);

	public List<RolesByClientResponse> getRolesByClient(Long client_id);

	public void editUserRolesById(@Valid RoleMasterEditRequest rolemastereditrequest) throws Exception;

	public void deleteUserRolesById(Long id, String modifiedBy) throws Exception;
}
