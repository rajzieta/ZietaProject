package com.zieta.tms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "TS_TIMEENTRIES")
@Data
@EqualsAndHashCode(callSuper=false)
public class TSTimeEntries extends BaseEntity implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name="ts_id")
    private Long tsId;

	@Column(name="time_type")
    private Long timeType;
    
	@Column(name="status_id")
    private Long statusId;
    
	@Column(name="TS_START_TIME")
    private  float tsStartTime;
    
	@Column(name="TS_END_TIME")
    private  float tsEndTime;
    
	@Column(name="TS_DURATION")
    private  float tsDuration;
    
	@Column(name="TIME_DESC")
    private  String timeDesc;
	
}
