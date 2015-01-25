package com.knuthp.ns.trainstations.place;

import static spark.Spark.*;
/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		 	setPort(Integer.parseInt(System.getenv("PORT")));
            get("/hello", (req, res) -> "Hello World");
        }
}
