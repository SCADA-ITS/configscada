// Uncomment following line for groovy file
package config.groovy.backoffice.smartgen.trigger;
// or Uncomment following line for java file
//package com.revenga.rits.front.openits.backoffice.groovy.trigger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revenga.rits.front.openits.backoffice.groovy.AbstractBackOfficeTrigger;
import com.revenga.rits.front.openits.backoffice.BackOfficeException;

public class OrderUtils {
	
	private OrderUtils() {
		
		throw new IllegalStateException("OrderUtils class");
	}

	public static Integer getAvailableStock(Integer productId, AbstractBackOfficeTrigger backOfficeTrigger) throws SQLException {
		
		Integer availableStock = null;
		final String qry = String.format("SELECT stock FROM %s.products WHERE product_id = ?", backOfficeTrigger.getSchema());	
		
		PreparedStatement stmt = null;
		
		try {
			
			stmt = backOfficeTrigger.getConn().prepareStatement(qry);
			stmt.setInt(1, productId);
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				
				availableStock = rs.getInt("stock");
			}
		}
		finally {
			
			if (stmt != null) {
				
				stmt.close();
			}
		}
		
		return availableStock;
	}
	
	public static void updateAvailableStock(Integer productId, Integer stock, AbstractBackOfficeTrigger backOfficeTrigger) throws SQLException, BackOfficeException {
		
		final String qry = String.format("UPDATE %s.products SET stock = ? WHERE product_id = ?", backOfficeTrigger.getSchema());	
		
		PreparedStatement stmt = null;
		
		try {
			
			stmt = backOfficeTrigger.getConn().prepareStatement(qry);
					
			stmt.setInt(1, stock);
			stmt.setInt(2, productId);
			
			backOfficeTrigger.traceSQL(stmt);
			
			int rowsAffected = stmt.executeUpdate();
			
			if (rowsAffected != 1) {
				
				throw new BackOfficeException(
						String.format("Can not update stock to %d units for product_id = %d", stock,
								productId));
			}
		}
		finally {
			
			if (stmt != null) {
				
				stmt.close();
			}
		}
	}
}

