package com.knuthp.ns.trainstations.place;

import static org.junit.Assert.*;

import java.util.List;

import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;

import com.knuthp.ns.trainstations.place.reisapi.RuterGateway;

public class PlaceListIT {

	private PlaceStorage placeList;

	@Test
	public void testGetPlaceFromId() throws Exception {
		Place place = placeList.getPlaceFromId("3010030");

		assertEquals(createNationalteatret(), place);
	}

	private Place createNationalteatret() {
		Place nationalteatret = new Place();
		nationalteatret.setId("3010030");
		nationalteatret.setName("Nationaltheatret [tog]");
		nationalteatret.setShortName("NAT");
		nationalteatret.setUtmLocation(new UtmLocation(596764, 6643250));
		return nationalteatret;
	}
	
	private Place createAsker() {
		Place asker = new Place();
		asker.setId("2200500");
		asker.setName("Asker [tog]");
		asker.setShortName("ASR");
		asker.setUtmLocation(new UtmLocation(580440, 6633790));
		return asker;
	}


	@Before
	public void setUp() {
		DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
		RuterGateway ruterGateway = new RuterGateway();

		placeList = new PlaceStorageMemory(dozerBeanMapper, ruterGateway);
	}

	@Test
	public void testGetPlaces() throws Exception {
		placeList.addPlace("3010030");
		placeList.addPlace("2200500");
		
		List<Place> placeSimpleList = placeList.getPlaces();
		
		assertEquals(2, placeSimpleList.size());
		assertEquals(createNationalteatret(), placeSimpleList.get(0));
		assertEquals(createAsker(), placeSimpleList.get(1));
	}
}
