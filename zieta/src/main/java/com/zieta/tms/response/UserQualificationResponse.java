package com.zieta.tms.response;

import java.io.Serializable;
import java.util.List;

import com.zieta.tms.model.ScreensMaster;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserQualificationResponse implements Serializable {
	
	private long clientId;	
	private long userId;
	private Long qualificationDesc;
	private String qualFileName;
	private String qualFilePath;
	
	

}
