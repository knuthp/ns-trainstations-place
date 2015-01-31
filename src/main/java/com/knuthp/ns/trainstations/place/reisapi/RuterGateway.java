package com.knuthp.ns.trainstations.place.reisapi;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class RuterGateway {
	public PlaceJ getStop(String id) throws IOException,
			ClientProtocolException, JsonParseException, JsonMappingException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet get = new HttpGet("http://reisapi.ruter.no" + "/Place/GetStop/"
				+ id);
		HttpResponse response = httpClient.execute(get);
		ObjectMapper mapper = new ObjectMapper();
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.PASCAL_CASE_TO_CAMEL_CASE);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		PlaceJ placej = mapper.readValue(response.getEntity().getContent(),
				PlaceJ.class);
		return placej;
	}

}
