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

/**
 * @author Matthew Kong
 * @author David Arques
 */
public class FilterConstants {

	public static final String COMPARISON_OPERATOR_EQUALS = " eq ";

	public static final String COMPARISON_OPERATOR_GREATER_THAN = " gt ";

	public static final String COMPARISON_OPERATOR_GREATER_THAN_OR_EQUAL =
		" ge ";

	public static final String COMPARISON_OPERATOR_LESS_THAN = " lt ";

	public static final String COMPARISON_OPERATOR_LESS_THAN_OR_EQUAL = " le ";

	public static final String COMPARISON_OPERATOR_NOT_EQUALS = " ne ";

	public static final String FIELD_NAME_CONTEXT_ACCOUNT =
		"organization/?/value";

	public static final String FIELD_NAME_CONTEXT_INDIVIDUAL =
		"demographics/?/value";

	public static final String FIELD_NAME_CONTEXT_INDIVIDUAL_SEGMENT =
		"fields/?/value";

	public static final String LOGICAL_OPERATOR_AND = " and ";

	public static final String LOGICAL_OPERATOR_OR = " or ";

	public static final String STRING_FUNCTION_CONTAINS = "contains";

	public static final String STRING_FUNCTION_ENDS_WITH = "endswith";

	public static final String STRING_FUNCTION_NOT_CONTAINS = "not contains";

	public static final String STRING_FUNCTION_STARTS_WITH = "startswith";

	public static boolean isStringFunction(String operator) {
		if (operator.equals(FilterConstants.STRING_FUNCTION_CONTAINS) ||
			operator.equals(FilterConstants.STRING_FUNCTION_ENDS_WITH) ||
			operator.equals(FilterConstants.STRING_FUNCTION_NOT_CONTAINS) ||
			operator.equals(FilterConstants.STRING_FUNCTION_STARTS_WITH)) {

			return true;
		}

		return false;
	}

}