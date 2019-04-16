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

import com.liferay.data.engine.rest.client.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.client.dto.v1_0.DataDefinitionField;
import com.liferay.data.engine.rest.client.dto.v1_0.DataDefinitionRule;
import com.liferay.data.engine.rest.client.dto.v1_0.LocalizedValue;
import com.liferay.data.engine.rest.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public class DataDefinitionSerDes {

	public static DataDefinition toDTO(String json) {
		DataDefinitionJSONParser dataDefinitionJSONParser =
			new DataDefinitionJSONParser();

		return dataDefinitionJSONParser.parseToDTO(json);
	}

	public static DataDefinition[] toDTOs(String json) {
		DataDefinitionJSONParser dataDefinitionJSONParser =
			new DataDefinitionJSONParser();

		return dataDefinitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DataDefinition dataDefinition) {
		if (dataDefinition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		sb.append("\"dataDefinitionFields\": ");

		if (dataDefinition.getDataDefinitionFields() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < dataDefinition.getDataDefinitionFields().length;
				 i++) {

				sb.append(
					DataDefinitionFieldSerDes.toJSON(
						dataDefinition.getDataDefinitionFields()[i]));

				if ((i + 1) < dataDefinition.getDataDefinitionFields().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"dataDefinitionRules\": ");

		if (dataDefinition.getDataDefinitionRules() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < dataDefinition.getDataDefinitionRules().length;
				 i++) {

				sb.append(
					DataDefinitionRuleSerDes.toJSON(
						dataDefinition.getDataDefinitionRules()[i]));

				if ((i + 1) < dataDefinition.getDataDefinitionRules().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"dateCreated\": ");

		if (dataDefinition.getDateCreated() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					dataDefinition.getDateCreated()));

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"dateModified\": ");

		if (dataDefinition.getDateModified() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					dataDefinition.getDateModified()));

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"description\": ");

		if (dataDefinition.getDescription() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < dataDefinition.getDescription().length; i++) {
				sb.append(
					LocalizedValueSerDes.toJSON(
						dataDefinition.getDescription()[i]));

				if ((i + 1) < dataDefinition.getDescription().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (dataDefinition.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(dataDefinition.getId());
		}

		sb.append(", ");

		sb.append("\"name\": ");

		if (dataDefinition.getName() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < dataDefinition.getName().length; i++) {
				sb.append(
					LocalizedValueSerDes.toJSON(dataDefinition.getName()[i]));

				if ((i + 1) < dataDefinition.getName().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"siteId\": ");

		if (dataDefinition.getSiteId() == null) {
			sb.append("null");
		}
		else {
			sb.append(dataDefinition.getSiteId());
		}

		sb.append(", ");

		sb.append("\"storageType\": ");

		if (dataDefinition.getStorageType() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(dataDefinition.getStorageType());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"userId\": ");

		if (dataDefinition.getUserId() == null) {
			sb.append("null");
		}
		else {
			sb.append(dataDefinition.getUserId());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class DataDefinitionJSONParser
		extends BaseJSONParser<DataDefinition> {

		@Override
		protected DataDefinition createDTO() {
			return new DataDefinition();
		}

		@Override
		protected DataDefinition[] createDTOArray(int size) {
			return new DataDefinition[size];
		}

		@Override
		protected void setField(
			DataDefinition dataDefinition, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dataDefinitionFields")) {
				if (jsonParserFieldValue != null) {
					dataDefinition.setDataDefinitionFields(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> DataDefinitionFieldSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new DataDefinitionField[size]
						));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "dataDefinitionRules")) {

				if (jsonParserFieldValue != null) {
					dataDefinition.setDataDefinitionRules(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> DataDefinitionRuleSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new DataDefinitionRule[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					dataDefinition.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					dataDefinition.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					dataDefinition.setDescription(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> LocalizedValueSerDes.toDTO((String)object)
						).toArray(
							size -> new LocalizedValue[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					dataDefinition.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					dataDefinition.setName(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> LocalizedValueSerDes.toDTO((String)object)
						).toArray(
							size -> new LocalizedValue[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteId")) {
				if (jsonParserFieldValue != null) {
					dataDefinition.setSiteId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "storageType")) {
				if (jsonParserFieldValue != null) {
					dataDefinition.setStorageType((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "userId")) {
				if (jsonParserFieldValue != null) {
					dataDefinition.setUserId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}