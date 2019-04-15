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

package com.liferay.headless.form.client.serdes.v1_0;

import com.liferay.headless.form.client.dto.v1_0.Field;
import com.liferay.headless.form.client.dto.v1_0.Field_Page;
import com.liferay.headless.form.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Field_PageSerDes {

	public static Field_Page toDTO(String json) {
		Field_PageJSONParser field_PageJSONParser = new Field_PageJSONParser();

		return field_PageJSONParser.parseToDTO(json);
	}

	public static Field_Page[] toDTOs(String json) {
		Field_PageJSONParser field_PageJSONParser = new Field_PageJSONParser();

		return field_PageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Field_Page field_Page) {
		if (field_Page == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (field_Page.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < field_Page.getItems().length; i++) {
				sb.append(FieldSerDes.toJSON(field_Page.getItems()[i]));

				if ((i + 1) < field_Page.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (field_Page.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(field_Page.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (field_Page.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(field_Page.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (field_Page.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(field_Page.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (field_Page.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(field_Page.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class Field_PageJSONParser
		extends BaseJSONParser<Field_Page> {

		protected Field_Page createDTO() {
			return new Field_Page();
		}

		protected Field_Page[] createDTOArray(int size) {
			return new Field_Page[size];
		}

		protected void setField(
			Field_Page field_Page, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					field_Page.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> FieldSerDes.toDTO((String)object)
						).toArray(
							size -> new Field[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					field_Page.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					field_Page.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					field_Page.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					field_Page.setTotalCount(
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