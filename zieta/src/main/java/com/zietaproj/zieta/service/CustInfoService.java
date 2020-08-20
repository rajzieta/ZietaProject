package com.zietaproj.zieta.service;

import java.util.List;

import javax.validation.Valid;

import com.zietaproj.zieta.dto.CustInfoDTO;
import com.zietaproj.zieta.model.CustInfo;
import com.zietaproj.zieta.response.CustomerInfoModel;
import com.zietaproj.zieta.response.CustomerInformationModel;

public interface CustInfoService {

	public List<CustomerInformationModel> getAllCustomers();

	public void addCustInfo(CustInfo custInfo);

	public List<CustInfo> getAllCustomersByClient(Long clientId);

	public List<CustomerInfoModel> findByClientIdAndOrgNode(long clientId, long orgNode);

	public void editCustInfoById(@Valid CustInfoDTO custinfoDTO) throws Exception;

	public void deleteCustInfoById(Long id, String modifiedBy) throws Exception;
}
