package com.zietaproj.zieta.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "WF_ACTION_TYPE_MASTER")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper=false)
//@JsonIgnoreProperties(value = {"created_date", "modified_date"}, 
  //      allowGetters = true)
@Data
public class ActionTypeMaster implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   
    @Column(name = "action_name")
    private String actionName;
	
	
}
