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

import com.liferay.headless.form.client.dto.v1_0.Column;
import com.liferay.headless.form.client.dto.v1_0.Column_Page;
import com.liferay.headless.form.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Column_PageSerDes {

	public static Column_Page toDTO(String json) {
		Column_PageJSONParser column_PageJSONParser =
			new Column_PageJSONParser();

		return column_PageJSONParser.parseToDTO(json);
	}

	public static Column_Page[] toDTOs(String json) {
		Column_PageJSONParser column_PageJSONParser =
			new Column_PageJSONParser();

		return column_PageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Column_Page column_Page) {
		if (column_Page == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (column_Page.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < column_Page.getItems().length; i++) {
				sb.append(ColumnSerDes.toJSON(column_Page.getItems()[i]));

				if ((i + 1) < column_Page.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (column_Page.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(column_Page.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (column_Page.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(column_Page.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (column_Page.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(column_Page.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (column_Page.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(column_Page.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class Column_PageJSONParser
		extends BaseJSONParser<Column_Page> {

		protected Column_Page createDTO() {
			return new Column_Page();
		}

		protected Column_Page[] createDTOArray(int size) {
			return new Column_Page[size];
		}

		protected void setField(
			Column_Page column_Page, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					column_Page.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ColumnSerDes.toDTO((String)object)
						).toArray(
							size -> new Column[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					column_Page.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					column_Page.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					column_Page.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					column_Page.setTotalCount(
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