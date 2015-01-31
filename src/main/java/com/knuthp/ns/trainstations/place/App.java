package com.knuthp.ns.trainstations.place;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.setPort;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.dozer.DozerBeanMapper;

import com.knuthp.ns.trainstations.place.reisapi.RuterGateway;

/**
 * Hello world!
 *
 */
public class App {
	private static final String ASKER = "2200500";
	private static final String NATIONALTEATRET = "3010030";

	public static void main(String[] args) {
		String portEnv = System.getenv("PORT");
		if (portEnv != null) {
			setPort(Integer.parseInt(portEnv));
		}

		DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
		RuterGateway ruterGateway = new RuterGateway();

		PlaceStorage placeStorage = new PlaceStorageMemory(dozerBeanMapper,
				ruterGateway);
		// PlaceStorage placeStorage = new PlaceStoragePostgre(dozerBeanMapper,
		// ruterGateway, getConnection());

		placeStorage.addPlace(NATIONALTEATRET);
		placeStorage.addPlace(ASKER);

		get("/", (req, res) -> "api/place");
		get("/hello", (req, res) -> "Hello World");

		get("/api/place", "application/json", (req, res) -> {
			res.type("application/json");
			return placeStorage.getPlaces();
		}, new JsonTransformer());

		get("/api/place/:id", (req, res) -> {
			res.type("application/json");
			return placeStorage.getPlaceFromId(req.params(":id"));
		}, new JsonTransformer());

		post("/api/place", (req, res) -> {
			res.type("application/json");
			Place place = placeStorage.addPlace("6049104");
			return place;
		}, new JsonTransformer());
	}

	private static Connection getConnection() throws URISyntaxException,
			SQLException {
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':'
				+ dbUri.getPort() + dbUri.getPath();

		return DriverManager.getConnection(dbUrl, username, password);
	}
}
