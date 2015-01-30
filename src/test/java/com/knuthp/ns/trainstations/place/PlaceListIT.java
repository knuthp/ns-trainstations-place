package com.knuthp.ns.trainstations.place;

import static org.junit.Assert.*;

import org.junit.Test;


public class PlaceListIT {

	
	@Test
	public void testName() throws Exception {
		PlaceList placeList = new PlaceList();
		Place place = placeList.getPlaceFromId("3010030");
		
		assertEquals("3010030", place.getId());
		assertEquals("Nationaltheatret [tog]", place.getName());
		assertEquals("NAT", place.getShortName());
		assertEquals(new UtmLocation(596764, 6643250), place.getUtmLocation());
	}
}
