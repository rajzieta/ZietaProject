package com.zieta.tms.model;

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

@Entity
@Table(name = "TEAM_MASTER")
@EntityListeners(AuditingEntityListener.class)
@Data
public class TeamMaster extends BaseEntity implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   
    @Column(name = "client_id")
    private Long clientId;
    
  
    @Column(name = "team_name")
    private String teamName;
    
    
    @Column(name = "team_desc")
    private String teamDesc;
}
