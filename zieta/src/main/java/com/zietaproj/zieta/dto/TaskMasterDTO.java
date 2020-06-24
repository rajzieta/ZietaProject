package com.zietaproj.zieta.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskMasterDTO {
	private Long id;
    private Long client_id;
    private String task_type;
    private String modified_by;
    private String created_by;
    private boolean IS_DELETE;
    private String client_code;
   
	
}
