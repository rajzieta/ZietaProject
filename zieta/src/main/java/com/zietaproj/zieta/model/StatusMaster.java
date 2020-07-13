package com.zietaproj.zieta.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name = "STATUS_MASTER")
@Data
public class StatusMaster extends BaseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "client_id")
	private Long clientId;

	
	private String status;

	//@NotBlank
	@Column(name = "status_type")
	private String statusType;
	
	@Column(name = "is_default")
	private Boolean isDefault;

}
