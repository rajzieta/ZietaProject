package com.zietaproj.zieta.request;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeTypeEditRequest {

	 private Long id;
	 private Long clientId;
	 private String timeType;
	 private String modifiedby;
	 private short isDelete;

}
