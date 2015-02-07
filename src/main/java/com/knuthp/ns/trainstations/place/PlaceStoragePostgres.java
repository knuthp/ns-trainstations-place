package com.knuthp.ns.trainstations.place;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.knuthp.ns.trainstations.place.reisapi.PlaceJ;
import com.knuthp.ns.trainstations.place.reisapi.RuterGateway;

public class PlaceStoragePostgres implements PlaceStorage {
	private static final Logger LOG = LoggerFactory
			.getLogger(PlaceStorage.class);
	private Connection connection;
	private DozerBeanMapper dozerBeanMapper;
	private RuterGateway ruterGateway;

	public PlaceStoragePostgres(DozerBeanMapper dozerBeanMapper,
			RuterGateway ruterGateway, Connection connection) {
		this.dozerBeanMapper = dozerBeanMapper;
		this.ruterGateway = ruterGateway;
		this.connection = connection;
		try {
			Statement stmt = connection.createStatement();
			stmt.execute("CREATE TABLE IF NOT EXISTS places (placeId varchar(255), PRIMARY KEY (placeId))");

			Statement createStatement = connection.createStatement();
			String sql = "SELECT * FROM places";
			ResultSet executeQuery = createStatement.executeQuery(sql);
			while (executeQuery.next()) {
				LOG.info("Place:" + executeQuery.getString("placeId"));
			}
			LOG.info("Done printing table");
		} catch (SQLException e) {
			LOG.error("Could not list content of tables", e);
		}
	}

	@Override
	public Place getPlaceFromId(String id) {
		try {
			PlaceJ placej = ruterGateway.getStop(id);

			Place place = dozerBeanMapper.map(placej, Place.class);
			place.setUtmLocation(new UtmLocation(placej.getX(), placej.getY()));

			return place;
		} catch (IOException e) {
			LOG.error("Error getting from RuterGateway", e);
			return null;
		}
	}

	@Override
	public Place addPlace(String placeId) {
		try {
			String sql = "INSERT INTO places VALUES (" + placeId + ")";
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
			return getPlaceFromId(placeId);
		} catch (SQLException e) {
			LOG.error("Could not insert into db", e);
		}
		return null;
	}

	@Override
	public List<Place> getPlaces() {
		List<Place> placeList = new ArrayList<Place>();
		try {
			String sql = "SELECT * FROM places";
			Statement stmt = connection.createStatement();
			ResultSet executeQuery = stmt.executeQuery(sql);

			while (executeQuery.next()) {
				placeList
						.add(getPlaceFromId(executeQuery.getString("placeId")));
			}
		} catch (SQLException e) {
			LOG.error("Could not get placeList from db", e);
		}
		return placeList;
	}

}
