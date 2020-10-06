package com.zieta.tms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "accesstype_screen_mapping")
@Data
@EqualsAndHashCode(callSuper=false)
public class AccessTypeScreenMapping extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "CLIENT_ID")
	private Long clientId;
	
	@Column( name = "SCREEN_ID")
	private Long screenId;
	
	@Column( name = "ACCESS_TYPE_ID")
	private Long accessTypeId;
	
}
