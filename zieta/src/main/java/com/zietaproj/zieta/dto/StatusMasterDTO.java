package com.zietaproj.zieta.dto;

import java.util.Date;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusMasterDTO {

	private Long id;
	private Long clientId;
	private String clientCode;
    private String status;
    private String status_type;
    private String created_by;
    private Date   created_date;
    private String modified_by;
    private Date modified_date;
    private boolean IS_DELETE;

}
