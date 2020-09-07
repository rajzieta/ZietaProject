package com.zietaproj.zieta.request;

import com.zietaproj.zieta.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectTypeEditRequest extends BaseEntity {

	
	private Long projectTypeId;
    private Long clientId;
    private String typeName;
}
