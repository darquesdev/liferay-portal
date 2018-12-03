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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NestableRuntimeException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.internal.asah.client.model.IndividualSegment;
import com.liferay.segments.internal.asah.client.model.Rels;
import com.liferay.segments.internal.asah.client.model.Results;
import com.liferay.segments.internal.asah.client.util.FilterBuilder;
import com.liferay.segments.internal.asah.client.util.FilterConstants;
import com.liferay.segments.internal.asah.client.util.OrderByField;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(immediate = true, service = AsahClient.class)
public class AsahClientImpl implements AsahClient {

	@Override
	public Results<IndividualSegment> getIndividualSegments(
		String weDeployKey, String query, List<String> fields, String name,
		String segmentType, String status, int cur, int delta,
		List<OrderByField> orderByFields) {

		try {
			String response = _jsonWebServiceClient.doGet(
				Rels.INDIVIDUAL_SEGMENTS,
				_getParameters(
					weDeployKey, query, fields, name, segmentType, status, cur,
					delta, orderByFields),
				_getHeaders());

			return _asahJSONMapper.mapToIndividualSegmentResults(response);
		}
		catch (IOException ioe) {
			throw new NestableRuntimeException(
				"Error handling JSON response: " + ioe.getMessage(), ioe);
		}
	}

	@Activate
	protected void activate() {
		_jsonWebServiceClient.setBaseUri(_ASAH_FARO_BACKEND_URL);
	}

	private static String _getSystemEnv(String name, String fallbackValue) {
		String value = System.getenv(name);

		if (Validator.isNull(value)) {
			return fallbackValue;
		}

		return value;
	}

	private Map<String, String> _getHeaders() {
		Map<String, String> headers = new HashMap<>();

		headers.put(
			_ASAH_SECURITY_SIGNATURE_HEADER, _ASAH_SECURITY_SIGNATURE_VALUE);

		return headers;
	}

	private Map<String, Object> _getParameters(
		String weDeployKey, String query, List<String> fields, String name,
		String segmentType, String status, int cur, int delta,
		List<OrderByField> orderByFields) {

		Map<String, Object> uriVariables = _getUriVariables(
			weDeployKey, cur, delta, orderByFields,
			FilterConstants.FIELD_NAME_CONTEXT_INDIVIDUAL_SEGMENT);

		FilterBuilder filterBuilder = new FilterBuilder();

		filterBuilder.addFilter(
			"name", FilterConstants.COMPARISON_OPERATOR_EQUALS, name);
		filterBuilder.addFilter(
			"segmentType", FilterConstants.COMPARISON_OPERATOR_EQUALS,
			segmentType);
		filterBuilder.addFilter(
			"status", FilterConstants.COMPARISON_OPERATOR_EQUALS, status);
		filterBuilder.addSearchFilter(query, fields, null);

		uriVariables.put("filter", filterBuilder.build());

		return uriVariables;
	}

	private Map<String, Object> _getUriVariables(String weDeployKey) {
		Map<String, Object> uriVariables = new HashMap<>();

		uriVariables.put("weDeployKey", weDeployKey);

		return uriVariables;
	}

	private Map<String, Object> _getUriVariables(
		String weDeployKey, int cur, int delta,
		List<OrderByField> orderByFields, String fieldNameContext) {

		Map<String, Object> uriVariables = _getUriVariables(weDeployKey);

		uriVariables.put("page", cur - 1);
		uriVariables.put("size", delta);

		if (orderByFields == null) {
			return uriVariables;
		}

		List<String> sort = new ArrayList<>();

		for (OrderByField orderByField : orderByFields) {
			String fieldName = orderByField.getFieldName();

			if (!orderByField.isSystem() && (fieldNameContext != null)) {
				fieldName = StringUtil.replace(
					fieldNameContext, CharPool.QUESTION, fieldName);
			}

			sort.add(fieldName + StringPool.COMMA + orderByField.getOrderBy());
		}

		uriVariables.put("sort", sort);

		return uriVariables;
	}

	private static final String _ASAH_FARO_BACKEND_URL = _getSystemEnv(
		"ASAH_FARO_BACKEND_URL", AsahClientImpl._ASAH_FARO_BACKEND_URL_DEV);

	private static final String _ASAH_FARO_BACKEND_URL_DEV =
		"https://osbasahfarobackend-asah93fdaf9914e34506bf664b9ab652fc01." +
			"eu-west-1.lfr.cloud";

	private static final String _ASAH_SECURITY_SIGNATURE_HEADER = _getSystemEnv(
		"ASAH_SECURITY_SIGNATURE_HEADER",
		"OSB-Asah-Faro-Backend-Security-Signature");

	private static final String _ASAH_SECURITY_SIGNATURE_VALUE = System.getenv(
		"ASAH_SECURITY_SIGNATURE_VALUE");

	private static final AsahJSONMapper _asahJSONMapper = new AsahJSONMapper();

	@Reference
	private JSONWebServiceClient _jsonWebServiceClient;

}