package com.knuthp.ns.trainstations.place;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class PlaceList {

	public Place getPlaceFromId(String id) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet get = new HttpGet("http://reisapi.ruter.no"
					+ "/Place/GetStop/3010030");
			HttpResponse response = httpClient.execute(get);
			ObjectMapper mapper = new ObjectMapper();
			mapper.setPropertyNamingStrategy(PropertyNamingStrategy.PASCAL_CASE_TO_CAMEL_CASE);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			Place place = mapper.readValue(response.getEntity().getContent(),
					Place.class);

			return place;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
