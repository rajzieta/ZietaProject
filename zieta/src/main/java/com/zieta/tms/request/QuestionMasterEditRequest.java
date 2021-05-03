package com.zieta.tms.request;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionMasterEditRequest {

		private Long id;		
		private String question;		
		private String modifiedBy;
		private Date modifiedDate;
		
		
		
}
