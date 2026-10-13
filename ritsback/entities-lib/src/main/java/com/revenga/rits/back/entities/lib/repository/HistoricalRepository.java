package com.revenga.rits.back.entities.lib.repository;

import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)

public class HistoricalRepository extends EntityRepository {

	public HistoricalRepository(Class<?> clazz, boolean b, Long defaultCacheRetentionTime) {

		super(clazz, b, defaultCacheRetentionTime);
	}

}
