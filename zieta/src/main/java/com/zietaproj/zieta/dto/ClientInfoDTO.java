package com.zietaproj.zieta.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientInfoDTO {

	private Long id;
    private String client_code;
    private String client_name;
    private Long client_status;
    private Date created_date;
    private String created_by;
    private String modified_by;
    private Date modified_date;
    private boolean IS_DELETE;
    private String client_comments;
    
}
