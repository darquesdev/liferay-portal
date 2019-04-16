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

package com.liferay.data.engine.rest.client.serdes.v1_0;

import com.liferay.data.engine.rest.client.dto.v1_0.CustomProperty;
import com.liferay.data.engine.rest.client.json.BaseJSONParser;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public class CustomPropertySerDes {

	public static CustomProperty toDTO(String json) {
		CustomPropertyJSONParser customPropertyJSONParser =
			new CustomPropertyJSONParser();

		return customPropertyJSONParser.parseToDTO(json);
	}

	public static CustomProperty[] toDTOs(String json) {
		CustomPropertyJSONParser customPropertyJSONParser =
			new CustomPropertyJSONParser();

		return customPropertyJSONParser.parseToDTOs(json);
	}

	public static String toJSON(CustomProperty customProperty) {
		if (customProperty == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"key\": ");

		if (customProperty.getKey() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(customProperty.getKey());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"value\": ");

		if (customProperty.getValue() == null) {
			sb.append("null");
		}
		else {
			sb.append(customProperty.getValue());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class CustomPropertyJSONParser
		extends BaseJSONParser<CustomProperty> {

		@Override
		protected CustomProperty createDTO() {
			return new CustomProperty();
		}

		@Override
		protected CustomProperty[] createDTOArray(int size) {
			return new CustomProperty[size];
		}

		@Override
		protected void setField(
			CustomProperty customProperty, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "key")) {
				if (jsonParserFieldValue != null) {
					customProperty.setKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "value")) {
				if (jsonParserFieldValue != null) {
					customProperty.setValue((Object)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}