package com.zietaproj.zieta.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.sun.istack.NotNull;

import lombok.Data;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;



@Entity
@Table(name = "ACTIVITY_MASTER")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"created_date", "modified_date"}, 
        allowGetters = true)
@Data
public class ActivityMaster implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name="client_id")
    private Long clientId;

    
    @NotBlank
    private String activity_code;
    
    @NotBlank
    private String activity_desc;
    
	@NotBlank
	private String created_by;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date created_date;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date modified_date;
	
	@NotBlank
	private String modified_by;
	
	private boolean IS_DELETE;
	
	private short is_active;
	
}
