package com.revenga.rits.back.io.controller.adapter;

public interface AdapterInterface <T, U> {

	public void load(Class<T> adapterType);
	public T get(U entity);
}
