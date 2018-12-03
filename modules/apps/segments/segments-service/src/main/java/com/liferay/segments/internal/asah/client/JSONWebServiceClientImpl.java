/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.segments.internal.asah.client;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(immediate = true, service = JSONWebServiceClient.class)
public class JSONWebServiceClientImpl implements JSONWebServiceClient {

	@Override
	public String doGet(
		String url, Map<String, Object> parameters,
		Map<String, String> headers) {

		WebTarget target = _client.target(_baseUri);

		target = target.path(url);

		for (Map.Entry<String, Object> entry : parameters.entrySet()) {
			target = target.queryParam(entry.getKey(), entry.getValue());
		}

		Invocation.Builder builder = target.request(
			MediaType.APPLICATION_JSON_TYPE);

		for (Map.Entry<String, String> entry : headers.entrySet()) {
			builder.header(entry.getKey(), entry.getValue());
		}

		Response response = builder.get();

		return response.readEntity(String.class);
	}

	@Override
	public String getBaseUri() {
		return _baseUri;
	}

	@Override
	public void setBaseUri(String baseUri) {
		_baseUri = baseUri;
	}

	@Activate
	protected void activate() {
		_client = _clientBuilder.build();

		_client.register(JacksonJsonProvider.class);
	}

	private String _baseUri;
	private Client _client;

	@Reference
	private ClientBuilder _clientBuilder;

}