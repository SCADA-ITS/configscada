package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.SystemPermission;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class SystemPermissionRepository extends EntityRepository implements EntityCacheable<SystemPermission> {

	// region Constructors
	public SystemPermissionRepository() {
		
		super(SystemPermission.class);
	}
	
	public SystemPermissionRepository(Boolean cacheable) {
		
		super(SystemPermission.class, cacheable);
	}
	
	public SystemPermissionRepository(Boolean cacheable, Long cacheRetentionTime) {
		
		super(SystemPermission.class, cacheable, cacheRetentionTime);
	}
	// endregion Constructors

	// region getters
	public SystemPermission get(Long id, Boolean fillObject) {
		
		return (SystemPermission) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}
	
	@Override
	public List<SystemPermission> getAll(Boolean fillObject) {
		
		List<BasicElement> basicElements = getsFromCache(fillObject);
		
		if (!CollectionUtils.isEmpty(basicElements)) {
			
			return basicElements.stream()
					.map(be -> (SystemPermission) be).collect(Collectors.toList());
		}
		
		return Collections.emptyList();
	}
	// endregion getters

	// region puts
	@Override
	public SystemPermission put(SystemPermission entity) {

		return (SystemPermission) putFromCache(entity);
	}

	@Override
	public List<SystemPermission> put(List<SystemPermission> entities) {
		
		List<BasicElement> basicElements = entities.stream()
												   .map(element -> (BasicElement) element)
												   .collect(Collectors.toList());
		
		
		basicElements = putsFromCache(basicElements);
		
		return basicElements.stream()
						    .map(be -> (SystemPermission) be)
						    .collect(Collectors.toList());
	}
	// endregion puts

	// region dels
	@Override
	public SystemPermission del(SystemPermission entity) {
		
		return (SystemPermission) delFromCache(entity);
		
	}

	@Override
	public List<SystemPermission> del(List<SystemPermission> entities) {
		
		List<BasicElement> basicElements = entities.stream()
												   .map(element -> (BasicElement) element)
												   .collect(Collectors.toList());

		basicElements = delFromCache(basicElements);
		
		return basicElements.stream()
							.map(be -> (SystemPermission) be)
							.collect(Collectors.toList());
	}
	// endregion dels

}
