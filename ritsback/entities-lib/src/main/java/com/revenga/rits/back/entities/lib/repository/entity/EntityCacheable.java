package com.revenga.rits.back.entities.lib.repository.entity;

import java.util.List;

public interface EntityCacheable <T> {

	List<T> getAll(Boolean fillObject);
	T put(T entity);
	List<T> put(List<T> entities);
	T del(T entity);
	List<T> del(List<T> entities);
}
