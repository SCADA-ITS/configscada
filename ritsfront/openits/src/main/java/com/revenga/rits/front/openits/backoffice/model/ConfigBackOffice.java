package com.revenga.rits.front.openits.backoffice.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.revenga.rits.front.openits.backoffice.BackOfficeUtil.DBType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
public class ConfigBackOffice implements Serializable{

	private static final long serialVersionUID = 998332538394682399L;
	

	@Data
	public static class Dml implements Serializable {
		
		private static final long serialVersionUID = 7152801229151566162L;
	
		private DBType dbType;
		private String qryReferenceTables;
		private String qryReferencedByTable;
		private String qryColumnsTable;
	}
	
	private String jdbc;
	private String user;
	private String password;
	private DBType dbType;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Dml> dmls = new ArrayList<>();
	
	public Dml getDml() {
		
		return this.dmls.stream().filter(e -> e.getDbType().equals(this.getDbType())).findFirst().orElse(null);
	}
	
	public DBType getDbType() {
		
		if (!StringUtils.isEmpty(jdbc)) {
			
			if (StringUtils.contains(jdbc, DBType.POSTGRESQL.toString())) {
				
				return DBType.POSTGRESQL;
			}
			
			if (StringUtils.contains(jdbc, DBType.MYSQL.toString())) {
				
				return DBType.MYSQL;
			}
			
			if (StringUtils.contains(jdbc, DBType.MSSQL.toString())) {
				
				return DBType.MSSQL;
			}
			
			if (StringUtils.contains(jdbc, DBType.ORACLE.toString())) {
				
				return DBType.ORACLE;
			}
		}
		
		return dbType;
	}
}
