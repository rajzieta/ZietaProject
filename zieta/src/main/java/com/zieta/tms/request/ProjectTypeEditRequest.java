package com.zieta.tms.request;

import com.zieta.tms.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectTypeEditRequest extends BaseEntity {

	
	private Long projectTypeId;
    private Long clientId;
    private String typeName;
}
