package com.zietaproj.zieta.service;

import java.util.List;

import com.zietaproj.zieta.model.CustInfo;
import com.zietaproj.zieta.response.CustomerInfoModel;

public interface CustInfoService {

	public List<CustInfo> getAllCustomers();

	public void addCustInfo(CustInfo custInfo);

	public List<CustInfo> getAllCustomersByClient(Long clientId);

	public List<CustomerInfoModel> findByClientIdAndOrgNode(long clientId, long orgNode);
}
