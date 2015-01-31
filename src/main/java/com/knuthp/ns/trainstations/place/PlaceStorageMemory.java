package com.knuthp.ns.trainstations.place;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;

import com.knuthp.ns.trainstations.place.reisapi.PlaceJ;
import com.knuthp.ns.trainstations.place.reisapi.RuterGateway;

public class PlaceStorageMemory implements PlaceStorage {

	private DozerBeanMapper dozerBeanMapper;
	private RuterGateway ruterGateway;
	private List<String> placeIdList = new ArrayList<String>();

	public PlaceStorageMemory(DozerBeanMapper dozerBeanMapper,
			RuterGateway ruterGateway) {
		this.dozerBeanMapper = dozerBeanMapper;
		this.ruterGateway = ruterGateway;
	}

	@Override
	public Place getPlaceFromId(String id) {
		try {
			PlaceJ placej = ruterGateway.getStop(id);

			Place place = dozerBeanMapper.map(placej, Place.class);
			place.setUtmLocation(new UtmLocation(placej.getX(), placej.getY()));

			return place;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Place addPlace(String placeId) {
		placeIdList.add(placeId);
		return getPlaceFromId(placeId);
	}

	@Override
	public List<Place> getPlaces() {
		List<Place> placeList = new ArrayList<Place>();
		for (String placeId : placeIdList) {
			placeList.add(getPlaceFromId(placeId));
		}
		return placeList;
	}

}
