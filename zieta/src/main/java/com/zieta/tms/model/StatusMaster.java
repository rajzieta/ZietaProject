package com.zieta.tms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "STATUS_MASTER")
@EqualsAndHashCode(callSuper=false)
@Data
public class StatusMaster extends BaseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "client_id")
	private Long clientId;

	@Column(name = "status_code")
	private String statusCode;

	@Column(name = "status_desc")
	private String statusDesc;

	@Column(name = "status_type")
	private String statusType;
	
	@Column(name = "IS_DEFAULT")
	private Boolean isDefault;

}
