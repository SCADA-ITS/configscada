// Uncomment following line for groovy file
package config.groovy.backoffice.smartgen.trigger;
// or Uncomment following line for java file
//package com.revenga.rits.front.openits.backoffice.groovy.trigger;

import org.apache.commons.lang3.math.NumberUtils;

import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeTrigger;
import com.revenga.rits.front.openits.backoffice.BackOfficeException;

public class OrderDetailTrigger extends AbstractBackOfficeTrigger {

	public OrderDetailTrigger(org.apache.logging.log4j.Logger log) {
		super(log);
	}
	
	@Override
	public boolean onAfterInsert() throws Exception {
		
		changeRecord();
		
		return true;
	}

	@Override
	public boolean onAfterUpdate() throws Exception {
		
		changeRecord();
		
		return true;
	}

	@Override
	public boolean onBeforeDelete() throws Exception {
		
		changeRecord();
		
		return true;
	}
	
	private void changeRecord() throws Exception {
		
		Integer previousQty = NumberUtils.toInt(getFieldValue(this.oldRow, "qty"));
		Integer previousProductId = NumberUtils.toInt(getFieldValue(this.oldRow, "product_id"));
		Integer currentQty = NumberUtils.toInt(getFieldValue(this.newRow, "qty"));
		Integer currentProductId = NumberUtils.toInt(getFieldValue(this.newRow, "product_id"));
		
		if (previousProductId.equals(currentProductId)) {
			
			if (!previousQty.equals(currentQty) && currentQty >= 0) {
				
				updateProductStock(currentProductId, previousQty - currentQty);
			}
		}
		else {
			
			updateProductStock(previousProductId, previousQty);
			
			if (currentProductId > 0) {
				
				updateProductStock(currentProductId, 0 - currentQty);
			}
		}
	}
	
	private void updateProductStock(Integer productId, Integer diffStock) throws Exception {
			
		if (diffStock != 0) {
			
			Integer availableStock = null;
					
			if (productId != null) {
				
				availableStock = OrderUtils.getAvailableStock(productId, this);
				
				if (availableStock + diffStock >= 0) {
					
					OrderUtils.updateAvailableStock(productId, availableStock + diffStock, this);
				}
				else {
					
					throw new BackOfficeException(String.format(
							"Insufficient stock to allocate %d units. %d are available for productId = %d", diffStock,
							availableStock, productId));
				}
			}
		}
	}
}
