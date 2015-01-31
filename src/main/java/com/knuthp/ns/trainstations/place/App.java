package com.knuthp.ns.trainstations.place;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.setPort;

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

		PlaceStorage placeStorage = new PlaceStorage(dozerBeanMapper,
				ruterGateway);
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
}
