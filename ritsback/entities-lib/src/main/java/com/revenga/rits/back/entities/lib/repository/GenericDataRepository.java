package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.GenericData;
import com.revenga.rits.back.data.core.model.GenericData.ManagementAreaValue;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class GenericDataRepository extends EntityRepository implements EntityCacheable<GenericData> {

	// region Constructors
	public GenericDataRepository() {

		super(GenericData.class);
	}

	public GenericDataRepository(Boolean cacheable) {

		super(GenericData.class, cacheable);

	}

	public GenericDataRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(GenericData.class, cacheable, cacheRetentionTime);

	}
	// endregion Constructors

	// region getters
	public GenericData get(Long id, Boolean fillObject) {

		GenericData genericData = (GenericData) getFromCache(new String[] { String.valueOf(id) }, fillObject);

		if (genericData != null && StringUtils.isNotEmpty(genericData.getStrManagementAreaValues())) {

			ObjectMapper objectMapper = new ObjectMapper();
			try {
				genericData.setManagementAreaValues(objectMapper.readValue(genericData.getStrManagementAreaValues(),
						new TypeReference<List<ManagementAreaValue>>() {
						}));
				
			} catch (JsonProcessingException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}

		return genericData;
	}

	@Override
	public List<GenericData> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			List<GenericData> genericDatas = basicElements.stream().map(GenericData.class::cast)
					.collect(Collectors.toList());

			ObjectMapper objectMapper = new ObjectMapper();

			genericDatas.forEach(genericData -> {

				if (genericData != null && StringUtils.isNotEmpty(genericData.getStrManagementAreaValues())) {

					try {

						genericData.setManagementAreaValues(
								objectMapper.readValue(genericData.getStrManagementAreaValues(),
										new TypeReference<List<ManagementAreaValue>>() {
										}));
						
					} catch (JsonProcessingException e) {

						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
					}
				}

			});

		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public GenericData put(GenericData entity) {

		if (!CollectionUtils.isEmpty(entity.getManagementAreaValues())) {

			ObjectMapper objectMapper = new ObjectMapper();
			try {
				entity.setStrManagementAreaValues(objectMapper.writeValueAsString(entity.getManagementAreaValues()));

			} catch (JsonProcessingException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}

		return (GenericData) putFromCache(entity);
	}

	@Override
	public List<GenericData> put(List<GenericData> entities) {

		if (!CollectionUtils.isEmpty(entities)) {

			ObjectMapper objectMapper = new ObjectMapper();

			entities.forEach(entity -> {

				if (!CollectionUtils.isEmpty(entity.getManagementAreaValues())) {

					try {
						entity.setStrManagementAreaValues(
								objectMapper.writeValueAsString(entity.getManagementAreaValues()));

					} catch (JsonProcessingException e) {

						log.error(e.getMessage());
						log.debug(ExceptionUtils.getStackTrace(e));
					}
				}

			});

			List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast)
					.collect(Collectors.toList());
			basicElements = putsFromCache(basicElements);
			
			return basicElements.stream().map(GenericData.class::cast).collect(Collectors.toList());
			
		} else {

			return Collections.emptyList();	
		}

	}
	// endregion puts

	// region dels
	@Override
	public GenericData del(GenericData entity) {

		return (GenericData) delFromCache(entity);

	}

	@Override
	public List<GenericData> del(List<GenericData> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(GenericData.class::cast).collect(Collectors.toList());
	}
	// endregion dels

}
