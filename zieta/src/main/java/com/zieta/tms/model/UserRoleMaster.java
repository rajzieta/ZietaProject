package com.zieta.tms.model;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name="user_role_master")
@Data
public class UserRoleMaster extends BaseEntity implements Serializable {
  
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

      @Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY)
	  @Column(name="ID")
	  private Long id;

	  @Column(name="CLIENT_ID")
	  private Long client_id;
	  
	  @Column(name="USER_ROLE")
	  private String user_role;
	  
	  @Column(name="BASE_COST")
	  private Double base_cost;
	  
	  @Column(name="ADDL_COST1")
	  private Double addl_cost1;
	  
	  @Column(name="ADDL_COST2")
	  private Double add_cost2;
	  
	  @Column(name="TOTAL_COST")
	  private Double total_cost;
	  
	//  @Column(name="CREATED_BY")
	//  private String created_by;
	  
	//  @Column(name="MODIFIED_BY")
	//  private String modified_by;

}
