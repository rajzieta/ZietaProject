package com.zieta.tms.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zieta.tms.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@JsonIgnoreProperties({ "parent" })
public class OrgNodesByClientResponse {

	
	Long orgUnitId;
	long clientId;
	 String clientCode;
     String clientDescription;
	 String orgNodeCode;
	 String orgNodeName;
	 Long orgType;
	 Long orgManager;
	 Long orgParentId;
	 Long orgStatus;
	 String orgUnitTypeDescription;
     String orgManagerName;
	
	
	@JsonIgnore
	OrgNodesByClientResponse parent;
	List<OrgNodesByClientResponse> children;
	
	public OrgNodesByClientResponse() {
		super();
		this.children = new ArrayList <>();
	}
	
	
	
	public void addChild(OrgNodesByClientResponse child) {
        if (!this.children.contains(child) && child != null)
            this.children.add(child);
    }
 
	
	
	
}
