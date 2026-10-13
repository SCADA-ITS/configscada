package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.ProcessInfo;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;

public class ProcessInfoRepository extends EntityRepository
implements EntityCacheable<ProcessInfo>{

	//Constructors
	public ProcessInfoRepository() {

		super(ProcessInfo.class);
	}

	public ProcessInfoRepository(Boolean cacheable) {

		super(ProcessInfo.class, cacheable);
	}
	
	//Getters
	@Override
	public List<ProcessInfo> getAll(Boolean fillObject) {
		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(ProcessInfo.class::cast).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	

	public ProcessInfo getByPid(Long pid, Boolean fillObject) {

		for (ProcessInfo processInfo : getAll(false)) {
			
			if (processInfo.getPid().equals(pid)) {
				
				return processInfo;
			}
		}
		
		return null;
	}

	@Override
	public ProcessInfo put(ProcessInfo entity) {

		return null;
	}

	@Override
	public List<ProcessInfo> put(List<ProcessInfo> entities) {

		return Collections.emptyList();
	}

	@Override
	public ProcessInfo del(ProcessInfo entity) {

		return null;
	}

	@Override
	public List<ProcessInfo> del(List<ProcessInfo> entities) {

		return Collections.emptyList();
	}

}
