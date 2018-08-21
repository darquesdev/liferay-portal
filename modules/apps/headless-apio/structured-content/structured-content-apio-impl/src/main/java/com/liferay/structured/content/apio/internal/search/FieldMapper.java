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

package com.liferay.structured.content.apio.internal.search;

import com.liferay.portal.kernel.search.Field;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Provides the mapping between the external field names that can be used in a
 * filter and the internal names that are used to search.
 *
 * @author Ruben Pulido
 */
public class FieldMapper {

	public static Map<String, String> getExternalToInternalFieldMappingsMap() {
		return _externalToInternalFieldMappingsMap;
	}

	public static Optional<String> getInternalFieldName(
		String externalFieldName) {

		return Optional.ofNullable(
			_externalToInternalFieldMappingsMap.get(externalFieldName));
	}

	private static final Map<String, String>
		_externalToInternalFieldMappingsMap;

	static {
		_externalToInternalFieldMappingsMap = new HashMap();

		_externalToInternalFieldMappingsMap.put(
			FieldConstants.TITLE_FIELD_EXTERNAL_NAME, Field.TITLE);

		_externalToInternalFieldMappingsMap.put(
			FieldConstants.DATE_CREATED_FIELD_EXTERNAL_NAME, Field.CREATE_DATE);

		_externalToInternalFieldMappingsMap.put(
			FieldConstants.DATE_MODIFIED_FIELD_EXTERNAL_NAME,
			Field.MODIFIED_DATE);

		_externalToInternalFieldMappingsMap.put(
			FieldConstants.DATE_PUBLISHED_FIELD_EXTERNAL_NAME,
			Field.PUBLISH_DATE);
	}

}