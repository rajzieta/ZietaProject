package com.zieta.tms.response;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerInfoModel {
	
    private Long custInfoId;
    private Long clientId;
    private String custName;
    private String custAddress;
    private String custDetails;
 //   private String custCode;
	private String createdBy;
    private Date createdDate;
    private Date modifiedDate;
	private String modifiedBy;
	private boolean isDelete;
	private  long orgNode;
	private String clientCode;
	private String clientDescription;
	

	
	



}
