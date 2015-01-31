package com.knuthp.ns.trainstations.place;

import java.util.List;

public interface PlaceStorage {

	public abstract Place getPlaceFromId(String id);

	public abstract Place addPlace(String placeId);

	public abstract List<Place> getPlaces();

}