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

package com.liferay.segments.internal.asah.client.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Matthew Kong
 * @author David Arques
 */
public class ContactsCriterionConstants {

	public static final int OPERATOR_ID_AFTER = 0;

	public static final int OPERATOR_ID_AND = 11;

	public static final int OPERATOR_ID_BEFORE = 1;

	public static final int OPERATOR_ID_BEHAVIOR_EQUALS = 13;

	public static final int OPERATOR_ID_BEHAVIOR_NOT_EQUALS = 16;

	public static final int OPERATOR_ID_BETWEEN = 2;

	public static final int OPERATOR_ID_CONTAINS = 3;

	public static final int OPERATOR_ID_EQUALS = 4;

	public static final int OPERATOR_ID_GREATER_THAN = 5;

	public static final int OPERATOR_ID_GREATER_THAN_OR_EQUALS = 14;

	public static final int OPERATOR_ID_KNOWN = 6;

	public static final int OPERATOR_ID_LESS_THAN = 7;

	public static final int OPERATOR_ID_LESS_THAN_OR_EQUALS = 15;

	public static final int OPERATOR_ID_NOT_CONTAIN = 8;

	public static final int OPERATOR_ID_NOT_EQUALS = 9;

	public static final int OPERATOR_ID_NOT_KNOWN = 10;

	public static final int OPERATOR_ID_OR = 12;

	public static final String OPERATOR_LABEL_AND = "operatorAnd";

	public static final String OPERATOR_LABEL_OR = "operatorOr";

	public static final int TYPE_BEHAVIOR = 1;

	public static final int TYPE_DEMOGRAPHIC = 2;

	public static final int TYPE_LOGICAL = 0;

	public static Map<String, Integer> getLogicalOperatorIds() {
		return _logicalOperatorIds;
	}

	public static Map<String, Integer> getTypes() {
		return _types;
	}

	public static boolean isLogicalOperator(int operatorId) {
		return _logicalOperatorIds.containsValue(operatorId);
	}

	private static final Map<String, Integer> _logicalOperatorIds =
		new HashMap<String, Integer>() {
			{
				put(OPERATOR_LABEL_AND, OPERATOR_ID_AND);
				put(OPERATOR_LABEL_OR, OPERATOR_ID_OR);
			}
		};
	private static final Map<String, Integer> _types =
		new HashMap<String, Integer>() {
			{
				put("behavior", TYPE_BEHAVIOR);
				put("demographic", TYPE_DEMOGRAPHIC);
				put("logical", TYPE_LOGICAL);
			}
		};

}