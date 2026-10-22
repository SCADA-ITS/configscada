package com.revenga.rits.back.service.graphql.mutation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.revenga.rits.back.front.core.model.vo.LocationVO;
import com.revenga.rits.back.service.graphql.service.LocationService;

@Component
public class LocationMutation implements GraphQLMutationResolver {
	
	@Autowired
	private LocationService locationService;

	public Optional<LocationVO> createLocation(final LocationVO location) {

		return locationService.create(location);
	}

	public Optional<LocationVO> updateLocation(final LocationVO location) {

		return locationService.update(location);
	}
	public Optional<LocationVO> deleteLocation(final String locationId) {

		return locationService.delete(locationId);
	}
}
