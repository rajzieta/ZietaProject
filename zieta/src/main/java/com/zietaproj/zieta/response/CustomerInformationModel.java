package com.zietaproj.zieta.response;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CustomerInformationModel {

    private Long id;

    private Long clientId;

    private String cust_name;
    
    private String cust_address;
    
    private String cust_details;
    
    private String cust_code;
    
    
	private String created_by;

    private Date created_date;

    private Date modified_date;
	
	private String modified_by;
	
	private boolean IS_DELETE;
	
	private String client_code;

	
	



}
