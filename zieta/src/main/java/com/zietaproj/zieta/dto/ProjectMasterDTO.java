package com.zietaproj.zieta.dto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ProjectMasterDTO {
	private Long id;
    private Long client_id;
    private String project_type;
    private String created_by;
    private String modified_by;
    private boolean IS_DELETE;
    private String client_code;
    private String project_code;
    
}
