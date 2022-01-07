package com.zieta.tms.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import lombok.Data;

/**
 * 
 * @author Raj
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "ActionCode",
    "ObjectNodeSenderTechnicalID",
    "EmployeeID",
    "ItemTypeCode",
    "DatePeriod",    
    "Duration",
    "ProjectElementID",
    "ServiceProductInternalID",
    "WorkDescriptionText"
    
})
@XmlRootElement(name = "EmployeeTime")
@Data
public class EmployeeTime {
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlElement(name = "ActionCode")
	protected String ActionCode;
	@XmlElement(name = "ObjectNodeSenderTechnicalID")
	protected String ObjectNodeSenderTechnicalID;
	@XmlElement(name = "EmployeeID")
	protected String EmployeeID;
	@XmlElement(name = "ItemTypeCode")
	protected String ItemTypeCode;
	@XmlElement(name = "DatePeriod")
	protected EmployeeTime.DatePeriod DatePeriod;
	//@XmlElement(name = "TimePeriod")
	//protected EmployeeTime.TimePeriod TimePeriod;	
	@XmlElement(name = "Duration")
	protected String Duration;
	//@XmlElement(name = "DifferentBillableTimeRecordedIndicator1")
	//protected boolean DifferentBillableTimeRecordedIndicator1;
	
	@XmlElement(name = "ProjectElementID")
	protected String ProjectElementID;
	
	@XmlElement(name = "ServiceProductInternalID")
	protected String ServiceProductInternalID;	
	
	@XmlElement(name = "WorkDescriptionText")
	protected String WorkDescriptionText;
	
	@Data
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class DatePeriod {
		@XmlElement(name = "StartDate")
		protected String StartDate;
		@XmlElement(name = "EndDate")
		protected String EndDate;
	}
	@Data
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class TimePeriod {
		@XmlElement(name = "StartTime")
		protected String StartTime;
		@XmlElement(name = "EndTime")
		protected String EndTime;
	}
	
	
	/*@Data
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class ProjectTaskConfirmation {
		@XmlElement(name = "ProjectElementID")
		protected String ProjectElementID;
		@XmlElement(name = "ServiceProductInternalID")
		protected String ServiceProductInternalID;
		@XmlElement(name = "CompletedIndicator")
		protected String CompletedIndicator;
	}*/
}
