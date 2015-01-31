package com.knuthp.ns.trainstations.place;

import static spark.Spark.*;

import java.util.ArrayList;
import java.util.List;

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

		PlaceStorage placeStorage = new PlaceStorage(dozerBeanMapper, ruterGateway);
		placeStorage.addPlace(NATIONALTEATRET);
		placeStorage.addPlace(ASKER);
		
		get("/hello", (req, res) -> "Hello World");
		get("/api/place", (req, res) -> { return placeStorage.getPlaces(); }, new JsonTransformer());
	}
}
