package com.zietaproj.zieta.service;

import java.util.List;
import com.zietaproj.zieta.dto.RoleMasterDTO;
import com.zietaproj.zieta.model.RoleMaster;
import com.zietaproj.zieta.response.RolesByClientResponse;

public interface RoleMasterService {

	public List<RoleMasterDTO> getAllRoles();

	public void addRolemaster(RoleMaster rolemaster);

	public List<RolesByClientResponse> getRolesByClient(Long client_id);
}
