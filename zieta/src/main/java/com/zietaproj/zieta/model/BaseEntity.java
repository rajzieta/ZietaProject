package com.zietaproj.zieta.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;
	
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
	private Date modifiedDate;
	
	@Column(name = "IS_DELETE")
	private short isDelete;

}