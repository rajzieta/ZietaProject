package com.zieta.tms.service;
import java.util.List;
import javax.validation.Valid;
import com.zieta.tms.dto.UserRoleMasterDTO;
import com.zieta.tms.model.UserRoleMaster;

public interface UserRoleMasterService {
	
     public void addUserRoleMaster(@Valid UserRoleMaster userRoleMaster);

     public List<UserRoleMasterDTO> getAllUserRoleMaster();
     
     public void deleteUserRoleMasterById(Long id) throws Exception;
  
     public void editUserRoleMasterById(@Valid UserRoleMasterDTO userRoleMasterDTO) throws Exception;
	
}
