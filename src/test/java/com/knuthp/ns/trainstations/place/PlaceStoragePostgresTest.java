package com.knuthp.ns.trainstations.place;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.knuthp.ns.trainstations.place.reisapi.PlaceJ;
import com.knuthp.ns.trainstations.place.reisapi.RuterGateway;

public class PlaceStoragePostgresTest {

	private DozerBeanMapper dozerBeanMapper;
	private RuterGateway ruterGateway;
	private Connection connection;
	private PlaceStorage placeStorage;

	@Test
	public void testEmptyDatabaseReturnsZero() throws Exception {
		List<Place> places = placeStorage.getPlaces();

		assertEquals(0, places.size());
	}

	@Test
	public void testAddingReturnsInList() throws Exception {
		String placeId = "11";
		PlaceJ expected = createPlaceJ(placeId);
		when(ruterGateway.getStop(eq(placeId))).thenReturn(expected);
		placeStorage.addPlace(placeId);
		List<Place> places = placeStorage.getPlaces();

		assertEquals(1, places.size());
		assertEquals(expected.getId(), places.get(0).getId());
		assertEquals(expected.getName(), places.get(0).getName());
		assertEquals(expected.getShortName(), places.get(0).getShortName());
		assertEquals(expected.getX(), places.get(0).getUtmLocation().getX());
		assertEquals(expected.getY(), places.get(0).getUtmLocation().getY());

	}

	private PlaceJ createPlaceJ(String placeId) {
		PlaceJ place = new PlaceJ();
		place.setId(placeId);
		place.setName("myName");
		place.setShortName("myShortName");
		place.setX(10);
		place.setY(20);
		return place;
	}

	@Before
	public void setUp() throws SQLException {
		dozerBeanMapper = new DozerBeanMapper();

		ruterGateway = mock(RuterGateway.class);
		connection = DriverManager
				.getConnection("jdbc:h2:mem:test", "sa", null);
		placeStorage = new PlaceStoragePostgres(dozerBeanMapper, ruterGateway,
				connection);
	}

	@After
	public void tearDown() throws Exception {
		connection.close();
	}
}
