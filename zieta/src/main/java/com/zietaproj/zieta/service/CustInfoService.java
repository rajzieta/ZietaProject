package com.zietaproj.zieta.service;

import java.util.List;

import com.zietaproj.zieta.model.CustInfo;
import com.zietaproj.zieta.model.CustOrgNodeMapping;

public interface CustInfoService {

	public List<CustInfo> getAllCustomers();

	public void addCustInfo(CustInfo custInfo);

	public List<CustInfo> getAllCustomersByClient(Long clientId);

	public List<CustInfo> findByClientIdAndOrgNode(long clientId, long orgNode);
}
