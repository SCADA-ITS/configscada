package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.GraphqlAdapter;
import com.revenga.rits.front.openits.adapter.impl.VO2UIAdapter;
import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class GenericDataBO extends GenericBO {
	
	@Autowired
	GraphqlAdapter graphqlAdapter;
	
	@Autowired 
	VO2UIAdapter vo2uiAdapter;
	
	@Autowired
	VODataSourceAdapter voDataSourceAdapter;

	@Override
	public List<Map<String, Object>> getList() {
		
		List<Map<String, Object>> genericDatas = voDataSourceAdapter.getList(voDataSourceAdapter.getDataSourceGetGenericDatas(), null);
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetGenericDatas()).getFieldId(), genericDatas);
	}

	@Override
	public Map<String, Object> getItem(String id) {
		
		Map<String, Object> genericData = voDataSourceAdapter.get(voDataSourceAdapter.getDataSourceGetGenericData(), new Object[] { id });
		
		return vo2uiAdapter.convert(voDataSourceAdapter.getDataSourceService()
				.getById(voDataSourceAdapter.getDataSourceGetGenericData()).getFieldId(), genericData);
	}
}
