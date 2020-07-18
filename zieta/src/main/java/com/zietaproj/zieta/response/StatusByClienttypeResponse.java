package com.zietaproj.zieta.response;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusByClienttypeResponse {

	
	private Long id;
	private Long clientId;
    private String status;
    private String status_type;
    private String created_by;
    private Date   created_date;
    private String modified_by;
    private Date modified_date;
    private short isDelete;
    private Boolean isDefault;
	private String clientCode;
	private String clientDescription;
		
	
}
