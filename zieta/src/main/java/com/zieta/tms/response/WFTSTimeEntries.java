package com.zieta.tms.response;

import com.zieta.tms.model.TSTimeEntries;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WFTSTimeEntries{
	TSTimeEntries tsTimeEntries;
	String timeType;
}

