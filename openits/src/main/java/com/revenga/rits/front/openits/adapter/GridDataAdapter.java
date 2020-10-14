package com.revenga.rits.front.openits.adapter;

import java.util.List;
import java.util.Map;

public interface GridDataAdapter<T> {

	public List<Map<String, Object>> toGridData(String fieldId, List<T> entities) throws IllegalAccessException;
}
