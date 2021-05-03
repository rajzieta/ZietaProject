package com.zieta.tms.request;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionAnswerMasterEditRequest {

		private Long id;
		private Long clientId;
		private Long questionId;
		private String answer;		
		private String modifiedBy;
		private Date modifiedDate;
		
		
		
}
