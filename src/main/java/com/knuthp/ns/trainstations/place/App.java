package com.knuthp.ns.trainstations.place;

import static spark.Spark.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		String portEnv = System.getenv("PORT");
		if (portEnv != null) {
			setPort(Integer.parseInt(portEnv));
		}
		
		List<String> placeList = new ArrayList<String>();
		placeList.add("1");
		placeList.add("2");
		
		get("/hello", (req, res) -> "Hello World");
		get("/api/place", (req, res) -> { return placeList; }, new JsonTransformer());
	}
}
