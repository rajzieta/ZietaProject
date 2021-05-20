package com.zieta.tms.dto;

import java.util.Date;

import com.zieta.tms.response.ActivitiesByClientProjectTaskResponse;
import com.zieta.tms.response.ActivitiesByClientProjectTaskResponse.AdditionalDetails;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDetailsDTO {

    private Long id;
	private Long clientId;
	private String userId;
	private String extId;
	private String location;
	private String designation;
	private String gender;
	private Date dob;
	private Date doj;
	private Date lwd;
	private String reasonLeaving;
	private String meritalStatus;
	private String fatherName;
	private String motherName;
	private String spouseName;
	private String bankName;
	private String bankAccName;
	private String bankAccNum;
	private String bankIfsc;	
	private String qualification;
	private String resumeFilename;
	private String resumeFilepath;
	private String panNumber;
	private String panFilepath;
	private String adharNumebr;
	private String adharFilename;
	private String adharFilepath;
	private String passportNumber;
	private String passportFilename;
	private String passportFilepath;	
	private String drivingLicense;
	private String DlFileName;
	private String dlFilepath;	
	private String permAddr;
	private String contactAdr;	
	private String emergencyNumber;
	private String emergencyName;
	private String emergencyRelationship;	
	private String bloodGroup;
	
	
	
}
