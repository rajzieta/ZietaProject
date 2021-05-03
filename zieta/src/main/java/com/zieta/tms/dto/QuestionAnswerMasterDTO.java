package com.zieta.tms.dto;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionAnswerMasterDTO {
	
	private Long id;
	private Long clientId;
	private Long questionId;
	private String answer;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private short isDelete;	
	//other details
	private String clientName;
	private String question;
}
