package com.zieta.tms.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Table(name = "user_details")
//@Unique(columns = { @UniqueColumn(fields= "email_id")})
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"created_date", "modified_date"}, 
        allowGetters = true)
@Data
public class UserDetails extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "client_id")
	private Long clientId;
	
	@Column(name = "EXT_ID")
	private String extId;
	

	@Column(name = "LOCATION")
	private String location;
	
	@Column(name = "DESIGNATION")
	private String designation;
	
	
	@Column(name = "GENDER")
	private String gender;
	
	@Column(name = "DOB")
	private Date dob;
	
	@Column(name = "DOJ")
	private Date doj;
	
	@Column(name = "LWD")
	private Date lwd;
	
	@Column(name = "REASON_LEAVING")
	private String reaosnLeaving;
	
	@Column(name = "MARITAL_STATUS")
	private String meritalStatus;
	
	
	@Column(name = "FATHER_NAME")
	private String fatherName;
	
	@Column(name = "MOTHER_NAME")
	private String motherName;
	
	@Column(name = "SPOUSE_NAME")
	private String spouseName;
	
	@Column(name = "BANK_NAME")
	private String bankName;
	
	@Column(name = "BANK_ACC_NAME")
	private String bankAccName;
	
	@Column(name = "BANK_ACC_NUM")
	private String bankAccNum;
	
	@Column(name = "BANK_IFSC")
	private String bankIfsc;
	
	//@Column(name = "QUALIFICATION")
	//private String qualification;
	
	@Column(name = "RESUME_FILENAME")
	private String resumeFileName;
	
	@Column(name = "RESUME_FILEPATH")
	private String resumeFilePath;
	
	@Column(name = "PAN_NUMBER")
	private String panNumber;
	
	@Column(name = "PAN_FILENAME")
	private String panFileName;
	
	@Column(name = "PAN_FILEPATH")
	private String panFilePath;
	
	@Column(name = "AADHAR_NUMBER")
	private String adharNumber;
	
	@Column(name = "AADHAR_FILENAME")
	private String adharFilename;
	
	@Column(name = "AADHAR_FILEPATH")
	private String adharFilePath;
	
	@Column(name = "PASSPORT_NUMBER")
	private String passportNumber;
	
	@Column(name = "PASSPORT_FILENAME")
	private String passportFilename;
	
	@Column(name = "PASSPORT_FILEPATH")
	private String passportFilePath;
	
	@Column(name = "DRIVING_LICENSE")
	private String drivingLicense;
	
	@Column(name = "DL_FILENAME")
	private String dlFilename;
	
	@Column(name = "DL_FILEPATH")
	private String dlFilePath;
	
	@Column(name = "PERM_ADDR")
	private String permAddress;
	
	@Column(name = "CONTACT_ADDR")
	private String contactAddress;
	
	@Column(name = "EMERGENCY_NUMBER")
	private String emergencyNumber;
	
	@Column(name = "EMERGENCY_NAME")
	private String emergencyName;
	
	@Column(name = "EMERGENCY_RELATIONSHIP")
	private String emergencyRelationship;
	
	@Column(name = "BLOOD_GRP")
	private String bloodGrp;

}
