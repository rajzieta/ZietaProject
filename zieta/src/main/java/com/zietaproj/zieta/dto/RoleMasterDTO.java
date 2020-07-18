package com.zietaproj.zieta.dto;

import com.zietaproj.zieta.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleMasterDTO extends BaseEntity {

	private Long id;
    private Long clientId;
    private String userRole;
//    private String created_by;
//    private String modified_by;
//    private boolean IS_DELETE;
    private String clientCode;
    private String clientDescription;
}


