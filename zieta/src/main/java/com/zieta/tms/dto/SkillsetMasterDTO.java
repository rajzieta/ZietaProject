package com.zieta.tms.dto;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillsetMasterDTO {

	 private Long id;
	 private Long clientId;
	 private String skillName;
	 private String skillCategory;
	 private  String clientCode;
	 private  String clientDescription;
}
