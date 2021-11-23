package com.zieta.tms.request;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionAnswerMasterBulkEditRequest {
		private Long id;
		private Long questionId;
		private Long clientId;
		private String answer;		
		private String modifiedBy;
		private Date modifiedDate;		
		
}
