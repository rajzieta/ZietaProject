package com.zieta.tms.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;
@Getter
@Setter
public class QuestionMasterDTO {
	
	private Long id;
	private String question;	
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private short isDelete;
}
