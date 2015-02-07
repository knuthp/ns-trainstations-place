package com.knuthp.ns.trainstations.place;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.port;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.dozer.DozerBeanMapper;

import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knuthp.ns.trainstations.place.reisapi.RuterGateway;

/**
 *
 */
public class App {

	public static void main(String[] args) throws URISyntaxException,
			SQLException {
		String portEnv = System.getenv("PORT");
		if (portEnv != null) {
			port(Integer.parseInt(portEnv));
		}

		DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
		RuterGateway ruterGateway = new RuterGateway();

		PlaceStorage placeStorage = new PlaceStoragePostgres(dozerBeanMapper,
				ruterGateway, getConnection());

		get("/", (req, res) -> {
			Map<String, Object> attributes = new HashMap<String, Object>();
			attributes.put("places", placeStorage.getPlaces());
			return new ModelAndView(attributes, "home.ftl");

		}, new FreeMarkerEngine());
		get("/hello", (req, res) -> "Hello World");

		get("/api/place", "application/json", (req, res) -> {
			res.type("application/json");
			return placeStorage.getPlaces();
		}, new JsonTransformer());

		get("/api/place/:id", (req, res) -> {
			res.type("application/json");
			return placeStorage.getPlaceFromId(req.params(":id"));
		}, new JsonTransformer());

		post("/api/place", "application/json", (req, res) -> {
			res.type("application/json");
			ObjectMapper mapper = new ObjectMapper();
			Place newPlace = mapper.readValue(req.body(), Place.class);
			Place place = placeStorage.addPlace(newPlace.getId());
			return place;
		}, new JsonTransformer());
		post("api/place", "application/x-www-form-urlencoded", (req, res) -> {
			String id = req.queryParams("id");
			res.type("application/json");
			Place place = placeStorage.addPlace(id);
			return place;
		});
		delete("/api/place/:id", (req, res) -> {
			placeStorage.deletePlace(req.params(":id"));
			return "";
		});
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
