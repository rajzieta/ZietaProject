package com.zieta.tms.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import com.zieta.tms.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ProjectInfoDTO extends BaseEntity {
	
    
    
    private Long projectInfoId;      
    
       
    private Long clientId;           
    
    private String projectCode;      
                                     
          
    private String extId;            
                                     
    
    private String projectName;      
                                     
   
    private Long projectType;        
                                     
    
    private Long projectOrgNode;     
                                     
   
    private Long projectManager;     
                                     
      
    private Long templateId;         
                                     
   
    private Long directApprover;     
                                     
   
    private short allowUnplanned;    
                                     
    
    private Long custId;             
                                     
    
    private Long projectStatus;      
                                     
   
    private Date extFetchDate;       
    
    
    
    
    
}
