package com.revenga.rits.back.data.core;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.revenga.rits.back.data.core.dao.impl.ObjectDaoImpl;
import com.revenga.rits.back.data.core.dao.manager.DataSourceConnection;
import com.revenga.rits.back.data.core.model.Element;

public class DbTest {
	
	private Element getElement() {
		
		Element element = new Element();
		
		element.setAlias("UTEST_ELEMENT");
		element.setDescription("Unit test element");
		element.setElementTypeId(33L);
		element.setEnabled(true);
		element.setVisible(true);
		
		return element;
	}
	
	private Connection getConnection() {
		
		Connection connection = null;
		
		String driver = "org.postgresql.Driver";
		String url = "jdbc:postgresql://192.168.88.194:5430/rits";
		String username = "rits";
		String password = "rits";
		
		try {

			connection = DataSourceConnection.getInstance(driver, url, username, password).getConnection();
			connection.setAutoCommit(false);
		
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		return connection;
	}

	@Test
	public void query() {
		
		ObjectDaoImpl objectDao = null;
		List<Object> listData = null;

		objectDao = new ObjectDaoImpl(Element.class);

		if (objectDao != null) {

			if (objectDao.load(getConnection())) {

				listData = objectDao.getList();
				
				assertTrue(!listData.isEmpty());
			}
		}
	}
	
	@Test
	public void persist() {
		
		Connection connection = null;
		Element element = null;
		ObjectDaoImpl objectDao = null;
		Element elementInserted = null;
		Element elementUpdated = null;
		Element elementDeleted = null;

		objectDao = new ObjectDaoImpl(Element.class);

		try {
			
			connection = getConnection();
			element = getElement();
		
			if (objectDao != null) {
							
				// Insert
				elementInserted = (Element) objectDao.insert(connection, element);
				
				if (elementInserted == null) throw new Exception("Insert fails");

				assertNotNull(elementInserted);
				
				connection.commit();
				
				// Update
				elementInserted.setDescription("Unit test element (updated");
				
				elementUpdated = (Element) objectDao.update(connection, elementInserted);
				
				if (elementUpdated == null) throw new Exception("Update fails");
				
				assertNotNull(elementUpdated);
				
				connection.commit();
				
				// Delete
				elementDeleted = (Element) objectDao.delete(connection, elementUpdated);

				if (elementDeleted == null) throw new Exception("Delete fails");
				
				assertNotNull(elementDeleted);
				
				connection.commit();
				
				
			}
		}
		catch (Exception e) {
			
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		finally {
			
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
