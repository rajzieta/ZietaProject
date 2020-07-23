package com.zietaproj.zieta.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkflowDTO {

	private Long id;
	private Long tsId;
	private Long workflowStepId;
	private Long statusId;
	private String statusComment;
	private Long CommentedBy;
	
}
