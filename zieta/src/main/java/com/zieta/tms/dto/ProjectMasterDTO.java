package com.zieta.tms.dto;

import javax.persistence.Column;

import com.zieta.tms.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ProjectMasterDTO extends BaseEntity {
	private Long projectTypeId;
    private Long clientId;
  //  private String project_type;
//    private String created_by;
//    private String modified_by;
//    private boolean IS_DELETE;
    private String extId;
    private String clientCode;
    private Long clientStatus;
    private String clientDescription;
 //  private String projectCode;
    private String typeName;
    private Boolean custMandatory;
    private Boolean isEditable;
}
