package com.revenga.rits.front.openitsdao.service;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.DataSource;

public interface DataSourceService {

	public static final String DATASOURCE_FOLDER= "graphql";
	public static final Integer DATASOURCE_TYPE_QUERY = 1;
	public static final Integer DATASOURCE_TYPE_MUTATION = 3;
	
	public static final Integer DATASOURCE_TYPE_QUERY_CONTENT = 4;
	public static final Integer DATASOURCE_TYPE_MUTATION_CONTENT= 12;
	
	public List<DataSource> getAll();
	public DataSource getById(Integer id);
}
