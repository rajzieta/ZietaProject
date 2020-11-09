package com.zieta.tms.response;

import java.util.Date;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccesstypesByClientResponse {

	private Long id;
	private Long clientId;
	private String clientCode;
	private String clientDescription;
    private String accessType;
    private String accessDesc;
	private String createdBy;
   	private Date createdDate;
    private Date modifiedDate;
	private String modifiedBy;
	private boolean isDelete;
	
}
