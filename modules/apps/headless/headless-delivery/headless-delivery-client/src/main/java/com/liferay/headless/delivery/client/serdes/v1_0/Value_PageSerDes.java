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

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.Value;
import com.liferay.headless.delivery.client.dto.v1_0.Value_Page;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Value_PageSerDes {

	public static Value_Page toDTO(String json) {
		Value_PageJSONParser value_PageJSONParser = new Value_PageJSONParser();

		return value_PageJSONParser.parseToDTO(json);
	}

	public static Value_Page[] toDTOs(String json) {
		Value_PageJSONParser value_PageJSONParser = new Value_PageJSONParser();

		return value_PageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Value_Page value_Page) {
		if (value_Page == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (value_Page.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < value_Page.getItems().length; i++) {
				sb.append(ValueSerDes.toJSON(value_Page.getItems()[i]));

				if ((i + 1) < value_Page.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (value_Page.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(value_Page.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (value_Page.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(value_Page.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (value_Page.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(value_Page.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (value_Page.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(value_Page.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class Value_PageJSONParser
		extends BaseJSONParser<Value_Page> {

		protected Value_Page createDTO() {
			return new Value_Page();
		}

		protected Value_Page[] createDTOArray(int size) {
			return new Value_Page[size];
		}

		protected void setField(
			Value_Page value_Page, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					value_Page.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ValueSerDes.toDTO((String)object)
						).toArray(
							size -> new Value[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					value_Page.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					value_Page.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					value_Page.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					value_Page.setTotalCount(
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