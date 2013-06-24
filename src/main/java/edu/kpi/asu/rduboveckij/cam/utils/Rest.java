package edu.kpi.asu.rduboveckij.cam.utils;

import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public abstract class Rest {
	protected HttpHeaders requestHeaders = new HttpHeaders();
	protected RestTemplate restTemplate = new RestTemplate();
	protected final String hostName;

	public Rest(final String hostName) {
		this.hostName = hostName;
		requestHeaders.setAccept(Collections.singletonList(new MediaType(
				"application", "json")));
		restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
	}

	protected <T> T[] get(final String url, Class<T[]> classT) {
		T[] collections = restTemplate.exchange(hostName + url, HttpMethod.GET,
				new HttpEntity<Object>(requestHeaders), classT).getBody();
		return collections;
	}
}
