package com.zietaproj.zieta.dto;

import com.zietaproj.zieta.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleMasterDTO extends BaseEntity {

	private Long id;
    private Long client_id;
    private String user_role;
//    private String created_by;
//    private String modified_by;
//    private boolean IS_DELETE;
    private String clientCode;
    private String clientName;
}


