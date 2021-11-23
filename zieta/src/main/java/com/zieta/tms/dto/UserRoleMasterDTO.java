package com.zieta.tms.dto;
import com.zieta.tms.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRoleMasterDTO extends BaseEntity {
	  private Long id;
	  private Long client_id;
	  private String user_role; 
	  private Double base_cost;
	  private Double addl_cost1;
	  private Double add_cost2;
	  private Double total_cost;
	//private String created_by;
	//private String modified_by;
	  
	 //private String clientCode;
	 //private String clientName;
	 //private Long clientStatus;
	 //private String clientComments;
	 //private short superAdmin;
}
