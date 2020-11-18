package com.zieta.tms.dto;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillsetUserMappingDTO {

	private Long id;
    private Long clientId;
    private Long userId;
    private Long skillsetId;
    private Long skillLevel;
    private String comments;
    
    private String ClientCode;
    private String ClientDescription;
}
