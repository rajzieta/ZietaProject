package com.zieta.tms.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zieta.tms.model.BaseEntity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@JsonIgnoreProperties({ "parent" })
public class OrgInfoDTO extends BaseEntity {

	private Long orgUnitId;
    private Long clientId;
    private String orgNodeCode;
    private String orgNodeName;
    private Long orgType;
    private Long orgManager;
    private Long orgParentId;
    private Long orgStatus;
    
    //additional fields
    private String clientCode;
    private String clientDescription;
    private Long clientStatus;
    private String orgUnitTypeDescription;
    private String orgManagerName;
    
    
    @JsonIgnore
	OrgInfoDTO parent;
	List<OrgInfoDTO> children;
	
	public OrgInfoDTO() {
		super();
		this.children = new ArrayList <>();
	}
	
	
	
	public void addChild(OrgInfoDTO child) {
        if (!this.children.contains(child) && child != null)
            this.children.add(child);
    }
 
}
