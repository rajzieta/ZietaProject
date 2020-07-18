package com.zietaproj.zieta.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserInfoDTO {

    private Long id;
	private Long clientId;
	private String user_fname;
	private String user_mname;
	private String user_lname;
	private String email;
	private String phone_no;
	private short is_active;
	private String created_by;
	private Date created_date;
	private String modified_by;
	private Date modified_date;
	private short is_delete;
	private String password;
	private String clientCode;
	private String ClientDescription;
	

}
