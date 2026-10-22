package com.revenga.rits.back.equipment.manager.data;

import java.util.List;
import java.util.Map;

import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.equipment.manager.service.EntitiesManager;

public abstract class EntityData<T, U> {
	
	protected EntitiesManager em;
	//protected Map<Class<?>, List<BasicElement>> mapCollections;
	protected RepositoryData repositoryData;

	protected EntityData(EntitiesManager em) {
		super();
		
		this.em = em;
		this.repositoryData = em.getRepositoryData();
		//this.mapCollections = em.getRepositoryData().getMapCollections();
	}
	
	public abstract void load();
	public abstract void load(U entity);
	
	public abstract T get(Long id);
}
