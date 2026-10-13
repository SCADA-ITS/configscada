package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.CommandElementValuePrecondition;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class CommandElementValuePreconditionRepository extends EntityRepository
		implements EntityCacheable<CommandElementValuePrecondition> {

	// region Constructors
	public CommandElementValuePreconditionRepository() {

		super(CommandElementValuePrecondition.class);
	}

	public CommandElementValuePreconditionRepository(Boolean cacheable) {

		super(CommandElementValuePrecondition.class, cacheable);
	}

	public CommandElementValuePreconditionRepository(Boolean cacheable, Long cacheRetentionTime) {

		super(CommandElementValuePrecondition.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public CommandElementValuePrecondition get(Long commandElementTypeId, Long elementTypeId, Long preconditionId,
			Long commandElementTypeParamId, Boolean fillObject) {

		return (CommandElementValuePrecondition) getFromCache(
				new String[] { String.valueOf(commandElementTypeId), String.valueOf(elementTypeId),
						String.valueOf(preconditionId), String.valueOf(commandElementTypeParamId) },
				fillObject);
	}

	public List<CommandElementValuePrecondition> gets(Long commandElementTypeId, Long elementTypeId, Long elementId,
			Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(commandElementTypeId),
				String.valueOf(elementTypeId), String.valueOf(elementId), null, null }, fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(CommandElementValuePrecondition.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	public List<CommandElementValuePrecondition> gets(Long commandElementTypeId, Long elementTypeId, Long elementId,
			Long preconditionId, Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(new String[] { String.valueOf(commandElementTypeId),
				String.valueOf(elementTypeId), String.valueOf(elementId), String.valueOf(preconditionId), null },
				fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(CommandElementValuePrecondition.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}

	@Override
	public List<CommandElementValuePrecondition> getAll(Boolean fillObject) {

		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(CommandElementValuePrecondition.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public CommandElementValuePrecondition put(CommandElementValuePrecondition entity) {

		return (CommandElementValuePrecondition) putFromCache(entity);
	}

	@Override
	public List<CommandElementValuePrecondition> put(List<CommandElementValuePrecondition> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = putsFromCache(basicElements);

		return basicElements.stream().map(CommandElementValuePrecondition.class::cast).collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public CommandElementValuePrecondition del(CommandElementValuePrecondition entity) {

		return (CommandElementValuePrecondition) delFromCache(entity);

	}

	@Override
	public List<CommandElementValuePrecondition> del(List<CommandElementValuePrecondition> entities) {

		List<BasicElement> basicElements = entities.stream().map(BasicElement.class::cast).collect(Collectors.toList());

		basicElements = delFromCache(basicElements);

		return basicElements.stream().map(be -> (CommandElementValuePrecondition) be).collect(Collectors.toList());
	}
	// endregion dels

}
