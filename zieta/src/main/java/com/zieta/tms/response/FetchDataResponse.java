package com.zieta.tms.response;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FetchDataResponse {

	private Date extFetchDate;
	private String extId;
}
