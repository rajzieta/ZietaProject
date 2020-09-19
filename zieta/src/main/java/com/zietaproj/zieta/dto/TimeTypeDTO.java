package com.zietaproj.zieta.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.zietaproj.zieta.model.BaseEntity;

import lombok.Data;

@Data
public class TimeTypeDTO extends BaseEntity {

	private Long id;
    private Long clientId;
    private String timeType;
	private String clientCode;
	private String clientDescription;
	private Long clientStatus;
    
	
}
