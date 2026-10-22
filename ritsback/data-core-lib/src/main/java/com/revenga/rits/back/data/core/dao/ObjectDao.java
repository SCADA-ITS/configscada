package com.revenga.rits.back.data.core.dao;

import java.lang.reflect.Field;
import java.util.List;

public interface ObjectDao {

	public List<Object> getList();
	public void setList(List<Object> objects);
	public Object get(Long id);
	public Object get(Field field, Object value);
	public boolean add(Object obj);
	public boolean replace(Object source, Object target);
	public boolean replace(Long id, Object target);
	public boolean remove(Object obj);
	public boolean remove(Long id);
}