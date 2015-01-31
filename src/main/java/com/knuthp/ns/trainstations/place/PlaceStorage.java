package com.knuthp.ns.trainstations.place;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;

import com.knuthp.ns.trainstations.place.reisapi.PlaceJ;
import com.knuthp.ns.trainstations.place.reisapi.RuterGateway;

public class PlaceStorage {

	private DozerBeanMapper dozerBeanMapper;
	private RuterGateway ruterGateway;
	private List<String> placeIdList = new ArrayList<String>();

	public PlaceStorage(DozerBeanMapper dozerBeanMapper, RuterGateway ruterGateway) {
		this.dozerBeanMapper = dozerBeanMapper;
		this.ruterGateway = ruterGateway;
	}

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

	public void addPlace(String placeId) {
		placeIdList.add(placeId);
		
	}

	public List<Place> getPlaces() {
		List<Place> placeList = new ArrayList<Place>();
		for(String placeId : placeIdList) {
			placeList.add(getPlaceFromId(placeId));
		}
		return placeList;
	}

}
