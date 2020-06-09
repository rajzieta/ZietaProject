package com.zietaproj.zieta.response;

public class ProjectsByClientResponse {

	Long id;
	//private Long clientId;
	private String type_name;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
//	public Long getClientId() {
//		return clientId;
//	}
//	public void setClientId(Long clientId) {
//		this.clientId = clientId;
//	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	
	
}
