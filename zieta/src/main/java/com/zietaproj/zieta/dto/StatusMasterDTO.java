package com.zietaproj.zieta.dto;

import java.util.Date;

import com.zietaproj.zieta.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusMasterDTO extends BaseEntity {

	private Long id;
	private Long clientId;
	private String clientCode;
    private String status;
    private String statusType;
//    private String created_by;
//    private Date   created_date;
//    private String modified_by;
//    private Date modified_date;
//    private boolean IS_DELETE;

}
