// Uncomment following line for groovy file
package config.groovy.backoffice.smartgen.trigger;
// or Uncomment following line for java file
//package com.revenga.rits.front.openits.backoffice.groovy.trigger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeTrigger;
import com.revenga.rits.front.openits.backoffice.BackOfficeException;

public class OrderTrigger extends AbstractBackOfficeTrigger {

	public OrderTrigger(org.apache.logging.log4j.Logger log) {
		super(log);
	}
	
	@Override
	public boolean onBeforeDelete() throws Exception {
		
		deleteRecord();
		
		return true;
	}
	
	private void deleteRecord() throws Exception {
		
		if (this.id != null) {
			
			updateProductStock();
		}
	}

	private void updateProductStock() throws Exception {
		
		Integer orderId = this.id;
		
		Map<Integer, Integer> productStocks = new HashMap<>();
		
		final String qry = String.format("SELECT product_id, qty FROM %s.order_details WHERE order_id = ?", this.schema);	
		
		if (orderId != null) {
			
			PreparedStatement stmt = null;
			
			try {
				
				stmt = this.conn.prepareStatement(qry);
				stmt.setInt(1, orderId);
				
				ResultSet rs = stmt.executeQuery();
				
				while (rs.next()) {
					
					Integer productId = rs.getInt("product_id");
					Integer qty = rs.getInt("qty");
					
					Integer sumProductStock = productStocks.get(productId) != null ? productStocks.get(productId) : 0;
					
					productStocks.put(productId, sumProductStock + qty);
				}
				
				for (Map.Entry<Integer, Integer> entry : productStocks.entrySet()) {
					
					Integer productId = entry.getKey();
					Integer diffStock = entry.getValue();
					
					Integer availableStock = OrderUtils.getAvailableStock(productId, this);
					
					if (availableStock + diffStock >= 0) {
						
						OrderUtils.updateAvailableStock(productId, availableStock + diffStock, this);
					}
					else {
						
						throw new BackOfficeException(String.format(
								"Insufficient stock to allocate %d units. %d are available for product_id = %d",
								diffStock, availableStock, productId));
					}
				}
			}
			finally {
				
				if (stmt != null) {
					
					stmt.close();
				}
			}
		}
	}
}

