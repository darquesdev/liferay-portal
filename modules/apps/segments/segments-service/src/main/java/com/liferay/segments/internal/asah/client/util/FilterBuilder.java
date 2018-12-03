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

package com.liferay.segments.internal.asah.client.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Matthew Kong
 * @author David Arques
 */
public class FilterBuilder {

	public void addFilter(FilterBuilder filterBuilder, boolean required) {
		if (required) {
			_requiredFilters.add(filterBuilder.build());
		}
		else {
			_filters.add(filterBuilder.build());
		}
	}

	public void addFilter(String fieldName, String operator, Object value) {
		addFilter(fieldName, operator, value, true);
	}

	public void addFilter(
		String fieldName, String operator, Object value, boolean required) {

		if (Validator.isNull(fieldName) || Validator.isNull(operator) ||
			Validator.isNull(value)) {

			return;
		}

		if (value instanceof String) {
			value = getValue(operator, value);
		}

		String filterString = FilterUtil.getFilter(fieldName, operator, value);

		if (required) {
			_requiredFilters.add(filterString);
		}
		else {
			_filters.add(filterString);
		}
	}

	public void addSearchFilter(
		String query, List<String> fieldNames, String fieldNameContext) {

		if (Validator.isNull(query) || fieldNames.isEmpty()) {
			return;
		}

		String[] keywords = StringUtil.split(query, StringPool.SPACE);

		boolean nameSearch = false;

		if ((keywords.length > 1) && fieldNames.containsAll(_nameFieldNames)) {
			nameSearch = true;
		}

		if (nameSearch) {
			FilterBuilder fieldNameFilterBuilder = new FilterBuilder();

			for (String fieldName : _nameFieldNames) {
				FilterBuilder keywordsFilterBuilder = new FilterBuilder();

				for (String keyword : keywords) {
					keywordsFilterBuilder.addFilter(
						FilterUtil.getFieldName(fieldName, fieldNameContext),
						FilterConstants.STRING_FUNCTION_CONTAINS, keyword,
						false);
				}

				fieldNameFilterBuilder.addFilter(keywordsFilterBuilder, true);
			}

			_filters.add(fieldNameFilterBuilder.build());
		}

		for (String fieldName : fieldNames) {
			if (!nameSearch || !_nameFieldNames.contains(fieldName)) {
				FilterBuilder fieldNameFilterBuilder = new FilterBuilder();

				fieldNameFilterBuilder.addSearchFilter(
					query, fieldName, fieldNameContext);

				_filters.add(fieldNameFilterBuilder.build());
			}
		}
	}

	public void addSearchFilter(
		String query, String fieldName, String fieldNameContext) {

		String[] keywords = StringUtil.split(query, StringPool.SPACE);

		for (String keyword : keywords) {
			addFilter(
				FilterUtil.getFieldName(fieldName, fieldNameContext),
				FilterConstants.STRING_FUNCTION_CONTAINS, keyword);
		}
	}

	public String build() {
		StringBundler sb = new StringBundler();

		if (!_filters.isEmpty()) {
			buildQueries(sb, _filters, FilterConstants.LOGICAL_OPERATOR_OR);

			if (!_requiredFilters.isEmpty()) {
				sb.append(FilterConstants.LOGICAL_OPERATOR_AND);
			}
		}

		if (!_requiredFilters.isEmpty()) {
			buildQueries(
				sb, _requiredFilters, FilterConstants.LOGICAL_OPERATOR_AND);
		}

		return sb.toString();
	}

	protected void buildQueries(
		StringBundler sb, List<String> filterQueries, String operator) {

		sb.append(StringPool.OPEN_PARENTHESIS);

		for (String filterQuery : filterQueries) {
			sb.append(filterQuery);
			sb.append(operator);
		}

		sb.setIndex(sb.index() - 1);

		sb.append(StringPool.CLOSE_PARENTHESIS);
	}

	protected String getValue(String operator, Object value) {
		String valueString = (String)value;

		valueString = valueString.replaceAll(
			StringPool.APOSTROPHE, StringPool.DOUBLE_APOSTROPHE);

		return valueString;
	}

	private static final List<String> _nameFieldNames = Arrays.asList(
		"familyName", "givenName");

	private final List<String> _filters = new ArrayList<>();
	private final List<String> _requiredFilters = new ArrayList<>();

}