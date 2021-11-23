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
import com.zieta.tms.model.VendorAdvanceLineItems;
import com.zieta.tms.model.VendorInvoice;
import com.zieta.tms.model.VendorInvoicelineItems;

@Repository
public interface VendorAdvanceLineItemsRepository extends JpaRepository<VendorAdvanceLineItems, Long>{

	List<VendorAdvanceLineItems> findByIsDelete(Short notDeleted);
	
	@Query(value ="select * from vendor_adv_lineitems where vendor_adv_id=?1 and is_delete=?2", nativeQuery=true)
	List<VendorAdvanceLineItems> findVendorAdvanceLineItemsByVendorInvoiceId(Long vendorAdvId,Short notDeleted);
	
	@Query(value ="select * from vendor_adv_lineitems where vendor_adv_id=?1 ", nativeQuery=true)
	List<VendorAdvanceLineItems> findByVendorAdvanceId(Long vendorAdvanceId);
	
	VendorAdvanceLineItems findVendorAdvanceLineItemsById(Long clientId);
	
	@Query(value ="select * from vendor_adv_lineitems where id=?1 and is_delete=?2", nativeQuery=true)
	VendorAdvanceLineItems findVendorAdvanceLineItemsById(long id,Short notDeleted);
	
}
