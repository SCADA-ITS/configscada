package com.revenga.rits.back.service.graphql.service;

import java.util.List;
import java.util.Optional;

public interface QueryInterface<T> {

	@Deprecated
	public List<T> getAll();

	@Deprecated
	public Optional<T> get(final String idItem);
	
	public List<T> gets(final List<String> idItems);

}
