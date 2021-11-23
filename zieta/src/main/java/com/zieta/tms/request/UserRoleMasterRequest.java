package com.zieta.tms.request;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRoleMasterRequest {

   
	private Long Id;
	private Long Client_Id;
	private Long User_Id;
	private Long Role_Id;
	private String Created_By;
	private Date Created_Date;
	private String Modified_By;
	private Date Modified_Date;
	private short Is_Delete;
}
