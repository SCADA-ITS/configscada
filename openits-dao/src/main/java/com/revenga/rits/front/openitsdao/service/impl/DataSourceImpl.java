package com.revenga.rits.front.openitsdao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openitsdao.model.DataSource;
import com.revenga.rits.front.openitsdao.repository.DataSourceRepository;
import com.revenga.rits.front.openitsdao.service.DataSourceService;

@Service
public class DataSourceImpl implements DataSourceService {

	@Autowired
	DataSourceRepository dataSourceRepository;
	
	@Override
	public List<DataSource> getAll() {

		return dataSourceRepository.findAll();
	}

	@Override
	public DataSource getById(Integer id) {
		
		return dataSourceRepository.findById(id);
	}
}
