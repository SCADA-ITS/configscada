package com.revenga.rits.back.service.graphql.service;

import java.util.Optional;

public interface MutationInterface<T> {

	public Optional<T> create(final T item);

	public Optional<T> clone(final String item);

	public Optional<T> update(final T item);

	public Optional<T> delete(final String id);
}
