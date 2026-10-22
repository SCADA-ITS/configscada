package com.revenga.rits.back.entities.lib.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.data.core.model.RoadImpact;
import com.revenga.rits.back.entities.lib.repository.entity.EntityCacheable;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.entity.EntityRepositoryOptions;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class RoadImpactRepository extends EntityRepository
		implements EntityCacheable<RoadImpact> {

	// region Constructors
	/**
	 * 
	 */
	public RoadImpactRepository() {

		super(RoadImpact.class);
	}
	
	public RoadImpactRepository(EntityRepositoryOptions options) {

		super(RoadImpact.class, options);
	}
	
	//end region Constructors

	//Getters Region

	public RoadImpact get(Long id, Boolean fillObject) {

		return (RoadImpact) getFromCache(new String[] { String.valueOf(id) }, fillObject);
	}
	
	@Override
	public List<RoadImpact> getAll(Boolean fillObject) {
		
		List<BasicElement> basicElements = getsFromCache(fillObject);

		if (!CollectionUtils.isEmpty(basicElements)) {

			return basicElements.stream().map(be -> (RoadImpact) be).collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	//end Getters Region

	@Override
	public RoadImpact put(RoadImpact entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RoadImpact> put(List<RoadImpact> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoadImpact del(RoadImpact entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RoadImpact> del(List<RoadImpact> entities) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

