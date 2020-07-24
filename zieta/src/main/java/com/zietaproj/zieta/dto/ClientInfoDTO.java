package com.zietaproj.zieta.dto;

import java.util.Date;

import com.zietaproj.zieta.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientInfoDTO extends BaseEntity {

	private Long id;
    private String clientCode;
    private String clientName;
    private Long clientStatus;
//    private Date createdDate;
//    private String created_by;
//    private String modified_by;
//    private Date modified_date;
  //  private boolean IS_DELETE;
    private String clientComments;
    
}
