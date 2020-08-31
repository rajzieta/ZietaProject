package com.zietaproj.zieta.response;

import com.zietaproj.zieta.model.TSTimeEntries;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WFTSTimeEntries{
	TSTimeEntries tsTimeEntries;
	String timeType;
}

