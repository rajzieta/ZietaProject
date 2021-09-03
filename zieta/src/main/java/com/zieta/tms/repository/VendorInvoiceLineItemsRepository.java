package com.zieta.tms.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zieta.tms.model.ExpenseEntries;
import com.zieta.tms.model.ExpenseInfo;
import com.zieta.tms.model.ExpenseTemplate;
import com.zieta.tms.model.ScreenCategoryMaster;
import com.zieta.tms.model.StatusMaster;
import com.zieta.tms.model.VendorInvoice;
import com.zieta.tms.model.VendorInvoicelineItems;

@Repository
public interface VendorInvoiceLineItemsRepository extends JpaRepository<VendorInvoicelineItems, Long>{

	List<VendorInvoicelineItems> findByIsDelete(Short notDeleted);
	
	@Query(value ="select * from vendor_invoice_lineitems where inv_id=?1 and is_delete=?2", nativeQuery=true)
	List<VendorInvoicelineItems> findVendorInvoiceLineItemsByVendorInvoiceId(Long vendorInvId,Short notDeleted);
	
	@Query(value ="select * from vendor_invoice_lineitems where inv_id=?1 ", nativeQuery=true)
	List<VendorInvoicelineItems> findByVendorInvoiceId(Long vendorInvoiceId);
	
	VendorInvoicelineItems findVendorInvoiceLineItemsById(Long clientId);
	
	@Query(value ="select * from vendor_invoice where id=?1", nativeQuery=true)
	VendorInvoicelineItems findVendorInvoiceLineItemsById(long id);
	
}
