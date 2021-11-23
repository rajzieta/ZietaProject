package com.zieta.tms.byd.serviceImpl;

import java.util.List;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.math3.util.Pair;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zieta.tms.byd.dto.EmployeeTime;
import com.zieta.tms.byd.service.EmployeeTimeService;
import com.zieta.tms.dto.UserInfoDTO;
import com.zieta.tms.model.ProjectInfo;
import com.zieta.tms.model.TSInfo;
import com.zieta.tms.model.UserInfo;
import com.zieta.tms.model.WorkFlowRequestComments;
import com.zieta.tms.service.ProjectMasterService;
import com.zieta.tms.service.UserInfoService;
import com.zieta.tms.service.WorkFlowRequestService;
import com.zieta.tms.serviceImpl.LeaveInfoServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeTimeServiceImpl implements EmployeeTimeService{

	@Autowired
	UserInfoService userInfoService;
	
	@Autowired
	ProjectMasterService projectMasterService;
	
	@Autowired
	WorkFlowRequestService workFlowRequestService;
	
	
	
	public List<Pair<Integer, String>> getEmployeeTimeDetails(String extId,String startDate, String duartion){
		EmployeeTime request = new EmployeeTime();
		request.setObjectNodeSenderTechnicalID("1");
		//System.out.println("extId===>"+extId);
		request.setEmployeeID(extId);
		request.setItemTypeCode("IN0010");
		EmployeeTime.DatePeriod datePeroid = new EmployeeTime.DatePeriod();
		datePeroid.setEndDate(startDate.toString());
		datePeroid.setStartDate(startDate.toString());
		request.setDatePeriod(datePeroid);
		EmployeeTime.TimePeriod timePeroid = new EmployeeTime.TimePeriod();
		timePeroid.setEndTime("09:00:00");
		timePeroid.setStartTime("17:00:00");
		request.setTimePeriod(timePeroid);		
		//String cusDuration ="PT7H00M";
		//converting duration to require format 
		String cusDuration = "PT"+duartion.substring(0, 2)+"H"+duartion.substring(2, 4)+"M";
		request.setDuration(cusDuration);
		request.setDifferentBillableTimeRecordedIndicator1(false);		
		List<Pair<Integer, String>> list = null;		

		JAXBContext context;
		try {
				context = JAXBContext.newInstance(EmployeeTime.class);
				Marshaller mar = context.createMarshaller();
				mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				StringWriter sw = new StringWriter();
				mar.marshal(request, sw);
				String result = sw.toString();
				String requestData = result.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");
				String data = requestData.replace("<EmployeeTime>", "<EmployeeTime actionCode=\"01\">");
	
				String finalString = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:glob=\"http://sap.com/xi/SAPGlobal20/Global\">\n"
						+ "<soapenv:Header/>\n" + "<soapenv:Body>\n" + "<glob:EmployeeTimeBundleMaintainRequest_sync>\n"
						+ "<BasicMessageHeader>\n" + "</BasicMessageHeader>" + data
						+ "</glob:EmployeeTimeBundleMaintainRequest_sync>\n" + "</soapenv:Body>\n" + "</soapenv:Envelope>";
				
				System.out.println("finalString" + finalString);			
				try {
					list = bydHttpRequest(finalString);
				} catch (IOException e) {				
					e.printStackTrace();
				}
			//System.out.println("HttpRequest Output" + list);		
			
		} catch (JAXBException e) {			
			e.printStackTrace();
		}	

		return list;
	}

	private List<Pair<Integer, String>> bydHttpRequest(String finalString) throws ClientProtocolException, IOException {
		String url = "https://my351070.sapbydesign.com/sap/bc/srt/scs/sap/manageemployeetimein";
		HttpPut httpPut = new HttpPut(url);

		httpPut.setHeader("content-type", "text/xml");
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("zietauser", "Welcome123");
		provider.setCredentials(AuthScope.ANY, credentials);
		HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
		StringEntity entity = new StringEntity(finalString);
		httpPut.setEntity(entity);

		HttpResponse resp = client.execute(httpPut);
		Integer httpStatusCd = resp.getStatusLine().getStatusCode();
		String respString = EntityUtils.toString(resp.getEntity());
		//log.info("Response Data from portal {} ", respString);
		List<Pair<Integer, String>> listOfPairs = new ArrayList<>();
		listOfPairs.add(new Pair<>(httpStatusCd, respString));
		//log.info("Status_Code and Response Binded to listOfPairs {} ", listOfPairs);
		return listOfPairs;
	}
	
	//SYNC TIMESHEETDATA

	@Override
	public void syncTimesheetData(TSInfo tsInfo) {
		
		UserInfoDTO userInfo = userInfoService.findByUserId(tsInfo.getUserId());
		EmployeeTime request = new EmployeeTime();
		request.setObjectNodeSenderTechnicalID("1");
		request.setEmployeeID(userInfo.getExtId());
		//System.out.println("===>"+userInfo.get);
		//request.setEmployeeID(extId);
		request.setItemTypeCode("IN0010");
		EmployeeTime.DatePeriod datePeroid = new EmployeeTime.DatePeriod();
		
		String dt[] = tsInfo.getTsDate().toString().split("\\s");
		datePeroid.setEndDate(dt[0]);
		datePeroid.setStartDate(dt[0]);
		request.setDatePeriod(datePeroid);
		EmployeeTime.TimePeriod timePeroid = new EmployeeTime.TimePeriod();
		///timePeroid.setEndTime("09:00:00");
		///timePeroid.setStartTime("17:00:00");
		request.setTimePeriod(timePeroid);		
		String duartion = tsInfo.getTsTotalSubmittedTime().toString();		
		//request.setDuration("PT17H00M");
		String[] parts = duartion.split("\\.");
		//converting duration to require format 
		String cusDuration = "PT"+parts[0]+"H"+parts[1]+"M";
		request.setDuration(cusDuration);
		request.setDifferentBillableTimeRecordedIndicator1(false);	
		
		ProjectInfo projectInfo = projectMasterService.findByProjectId(tsInfo.getProjectId());		
		EmployeeTime.ProjectTaskConfirmation projectTaskConfirmation = new EmployeeTime.ProjectTaskConfirmation();
		projectTaskConfirmation.setProjectElementID(projectInfo.getExtId());
		//projectTaskConfirmation.setServiceProductInternalID("20000005");//need to talk about this
		projectTaskConfirmation.setServiceProductInternalID(tsInfo.getActivityId().toString());
		
		List<WorkFlowRequestComments> workFlowRequestComments = workFlowRequestService.findByTsIdDesc(tsInfo.getId());
		
		
		System.out.println("test comment =====>"+workFlowRequestComments);
		request.setTimeSheetDescription(workFlowRequestComments.get(0).getComments());
		
		projectTaskConfirmation.setCompletedIndicator("false");		
		request.setProjectTaskConfirmation(projectTaskConfirmation);
		List<Pair<Integer, String>> list = null;		

		JAXBContext context;
		try {
			context = JAXBContext.newInstance(EmployeeTime.class);
			Marshaller mar = context.createMarshaller();
			mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			mar.marshal(request, sw);
			String result = sw.toString();
			String requestData = result.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");
			String data = requestData.replace("<EmployeeTime>", "<EmployeeTime actionCode=\"01\">");
			
			data = data.replace("<TimeSheetDescription>", "<a3x:TimeSheetDescription>");
			data = data.replace("</TimeSheetDescription>", "</a3x:TimeSheetDescription>");

			String finalString = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:glob=\"http://sap.com/xi/SAPGlobal20/Global\">\n"
					+ "<soapenv:Header/>\n" + "<soapenv:Body>\n" + "<glob:EmployeeTimeBundleMaintainRequest_sync>\n"
					+ "<BasicMessageHeader>\n" + "</BasicMessageHeader>" + data
					+ "</glob:EmployeeTimeBundleMaintainRequest_sync>\n" + "</soapenv:Body>\n" + "</soapenv:Envelope>";
			
			System.out.println("finalString" + finalString);			
			try {
				list = bydHttpRequest(finalString);
				//SET RESPONSE IN TRCKING TABLE				
				
			} catch (IOException e) {
				//SET RESPONSE IN TRCKING TABLE
				
				e.printStackTrace();
			}
			System.out.println("HttpRequest Output" + list);		
			
		} catch (JAXBException e) {
			
			e.printStackTrace();
		}	

		//return list;
		
		
	}

	


}
