package com.zieta.tms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity
@Table(name = "TASK_TYPE_MASTER")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(value = {"createdDate", "modifiedDate"}, 
allowGetters = true)
public class TaskTypeMaster extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long taskTypeId;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "type_name")
    private String taskTypeDescription;

	
}

