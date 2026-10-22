package com.revenga.rits.back.service.graphql.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.revenga.rits.back.front.core.model.vo.LocationTypeVO;
import com.revenga.rits.back.front.core.model.vo.LocationVO;
import com.revenga.rits.back.front.core.model.vo.RoadVO;
import com.revenga.rits.back.front.core.model.vo.SpathVO;
import com.revenga.rits.back.front.core.model.vo.StretchStretchVO;
import com.revenga.rits.back.front.core.model.vo.StretchVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;
import com.revenga.rits.back.service.graphql.service.LocationService;
import com.revenga.rits.back.service.graphql.service.LocationTypeService;
import com.revenga.rits.back.service.graphql.service.RoadService;
import com.revenga.rits.back.service.graphql.service.SpathService;
import com.revenga.rits.back.service.graphql.service.StretchService;
import com.revenga.rits.back.service.graphql.service.StretchStretchService;

import graphql.schema.DataFetchingEnvironment;

@Component
public class LocationQuery implements GraphQLQueryResolver {

	@Autowired
	private RoadService roadService;

	@Autowired
	private StretchService stretchService;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private LocationTypeService locationTypeService;
	
	@Autowired
	private SpathService spathService;
	
	@Autowired
	private StretchStretchService stretchStretchService;

	public List<RoadVO> getRoads(DataFetchingEnvironment env) {

		return roadService.getAll(EntitiesManager.getManagementAreaId(env));
	}

	public Optional<RoadVO> getRoad(final String roadId) {

		return roadService.get(roadId);
	}
	
	public List<StretchVO> getStretchs(DataFetchingEnvironment env) {

		return stretchService.getAll(EntitiesManager.getManagementAreaId(env));
	}

	public Optional<StretchVO> getStretch(final String stretchId) {

		return stretchService.get(stretchId);
	}
	
	public List<StretchVO> getStretchByRoad(final String roadId) {

		return stretchService.getByRoad(roadId);
	}
	
	public List<StretchVO> getStretchByLocation(final String locationId) {

		return stretchService.getByLocation(locationId);
	}
	
	public List<StretchStretchVO> getStretchStretchsByStretch(final String stretchId){
		
		return stretchStretchService.getsByStretch(stretchId);
	}
		
	public List<LocationVO> getLocations() {

		return locationService.getAll();
	}

	public List<LocationTypeVO> getLocationTypes() {

		return locationTypeService.getAll();
	}
	
	public Optional<LocationVO> getLocation(final String locationId) {

		return locationService.get(locationId);
	}
	
	public List<LocationVO> getLocationsByStretch(final String stretchId) {

		return locationService.getByStretch(stretchId);
	}
	
	public List<LocationVO> getLocationsByStretchs(final List<String> stretchIds) {

		return locationService.getByStretchs(stretchIds);
	}
	
	public List<SpathVO> getSpaths(final List<String> spathIds, final String stretchId) {

		return spathService.gets(spathIds, stretchId);
	}
}