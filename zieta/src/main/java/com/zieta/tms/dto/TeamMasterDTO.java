package com.zieta.tms.dto;

import javax.persistence.Column;

import com.zieta.tms.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TeamMasterDTO extends BaseEntity {

	private Long id;
    private Long clientId;
    private String teamName;
    private String teamDesc;
    
    private String clientCode;
    private String clientDescription;
}
