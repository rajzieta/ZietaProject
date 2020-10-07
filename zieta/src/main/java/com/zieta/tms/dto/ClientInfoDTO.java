package com.zieta.tms.dto;

import com.zieta.tms.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientInfoDTO extends BaseEntity {

	private Long id;
    private String clientCode;
    private String clientName;
    private Long clientStatus;
    private String clientComments;
    private short superAdmin;
    
}
