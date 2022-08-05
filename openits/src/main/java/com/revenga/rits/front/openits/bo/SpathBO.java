package com.revenga.rits.front.openits.bo;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.revenga.rits.front.openits.adapter.impl.VODataSourceAdapter;

@Service
public class SpathBO extends GenericBO {
	

	public List<Map<String, Object>> getSpaths(List<String> ids, String stretchId) {

		List<Map<String, Object>> spaths = voDataSourceAdapter
				.getList(VODataSourceAdapter.DATA_SOURCE_GET_SPATHS, new Object[] {ids, stretchId});
		
		return vo2uiAdapter.convert(VODataSourceAdapter.DATA_SOURCE_GET_SPATHS, spaths);
	}
}
