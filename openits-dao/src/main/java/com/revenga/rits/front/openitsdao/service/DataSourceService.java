package com.revenga.rits.front.openitsdao.service;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.DataSource;

public interface DataSourceService {

	public List<DataSource> getAll();
	public DataSource getById(Integer id);
}
