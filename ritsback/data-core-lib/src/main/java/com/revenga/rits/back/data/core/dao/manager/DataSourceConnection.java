package com.revenga.rits.back.data.core.dao.manager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.revenga.rits.back.data.core.util.ConfigProperty;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DataSourceConnection {
	
	static DataSourceConnection instance;
	DataSource ds = null;

	public static DataSourceConnection getInstance() throws SQLException, IOException {

		if (instance == null) {
			instance = new DataSourceConnection();
		}

		return instance;
	}

	public static DataSourceConnection getInstance(String driverClassName, String url, String userName, String password)
			throws SQLException, IOException {

		if (instance == null) {
			instance = new DataSourceConnection(driverClassName, url, userName, password);
		}

		return instance;
	}

	private DataSourceConnection() throws SQLException, IOException {

		Connection connection = null;

		try {

			ds = getDataSource();

			connection = getConnection();
			connection.close();
			
		} catch (SQLException | IOException e) {
			
			throw e;
		} 	
	}

	private DataSourceConnection(String driverClassName, String url, String userName, String password)
			throws SQLException, IOException {

		Connection connection = null;

		try {

			ds = getDataSource(driverClassName, url, userName, password);

			connection = getConnection();
			connection.close();
			
		} catch (SQLException | IOException e) {
			
			throw e;
		} 	
	}

	public Connection getConnection() throws SQLException {

		return ((BasicDataSource) ds).getConnection();
	}

	public Connection newConnection() {

		BasicDataSource bds = null;
		Connection conn = null;

		try {

			bds = getDataSource();

			conn = DriverManager.getConnection(bds.getUrl(), bds.getUsername(), bds.getPassword());

		} catch (SQLException | IOException e) {
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		} 

		return conn;
	}

	public void disconnect() throws Exception {

		if (ds != null) {

			((BasicDataSource) ds).close();
		}
	}

	private BasicDataSource getDataSource() throws IOException {

		BasicDataSource bds = null;

		bds = new BasicDataSource();

		String driverClassName = ConfigProperty.getInstance().getProperty("jdbc.properties",
				"jdbc.driver-class-name");
		String url = ConfigProperty.getInstance().getProperty("jdbc.properties", "jdbc.url");
		String userName = ConfigProperty.getInstance().getProperty("jdbc.properties", "jdbc.username");
		String password = ConfigProperty.getInstance().getProperty("jdbc.properties", "jdbc.password");

		bds.setDriverClassName(driverClassName);
		bds.setUrl(url);
		if (!StringUtils.isEmpty(userName))	bds.setUsername(userName);
		if (!StringUtils.isEmpty(password))	bds.setPassword(password);

		return bds;
	}

	private BasicDataSource getDataSource(String driverClassName, String url, String userName, String password)
			throws IOException {

		BasicDataSource bds = new BasicDataSource();

		bds.setDriverClassName(driverClassName);
		bds.setUrl(url);
		if (!StringUtils.isEmpty(userName)) bds.setUsername(userName);
		if (!StringUtils.isEmpty(password))	bds.setPassword(password);

		return bds;
	}
}
