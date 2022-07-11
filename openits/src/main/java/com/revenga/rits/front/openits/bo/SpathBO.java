package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class SpathBO extends GenericBO {
	
	@PostConstruct
	private void initialize() {
		
		dataSourceList = VODataSourceAdapter.DATA_SOURCE_GET_SPATHS;
		dataSourceItem = VODataSourceAdapter.DATA_SOURCE_GET_SPATH;
	}
}
