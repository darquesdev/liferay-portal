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

package com.liferay.petra.sql.dsl.query.impl;

import java.util.Objects;

/**
 * @author Preston Crary
 */
public class SetOperationType {

	public static final SetOperationType UNION = new SetOperationType("union");

	public static final SetOperationType UNION_ALL = new SetOperationType(
		"union all");

	public SetOperationType(String value) {
		_value = Objects.requireNonNull(value);
	}

	@Override
	public String toString() {
		return _value;
	}

	private final String _value;

}