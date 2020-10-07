package com.zieta.tms.service;

import java.util.List;

import javax.validation.Valid;

import com.zieta.tms.dto.CustInfoDTO;
import com.zieta.tms.model.CustInfo;
import com.zieta.tms.response.CustomerInfoModel;
import com.zieta.tms.response.CustomerInformationModel;

public interface CustInfoService {

	public List<CustomerInformationModel> getAllCustomers();

	public void addCustInfo(CustInfo custInfo);

	public List<CustInfo> getAllCustomersByClient(Long clientId);

	public List<CustomerInfoModel> findByClientIdAndOrgNode(long clientId, long orgNode);

	public void editCustInfoById(@Valid CustInfoDTO custinfoDTO) throws Exception;

	public void deleteCustInfoById(Long id, String modifiedBy) throws Exception;
}
