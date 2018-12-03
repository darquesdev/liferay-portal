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
 * @author Shinn Lok
 * @author David Arques
 */
public class OrderByField {

	public OrderByField() {
	}

	public OrderByField(String fieldName, String orderBy) {
		_fieldName = fieldName;
		_orderBy = orderBy;
	}

	public OrderByField(String fieldName, String orderBy, boolean system) {
		_fieldName = fieldName;
		_orderBy = orderBy;
		_system = system;
	}

	public String getFieldName() {
		return _fieldName;
	}

	public String getOrderBy() {
		return _orderBy;
	}

	public boolean isSystem() {
		return _system;
	}

	private String _fieldName;
	private String _orderBy;
	private boolean _system;

}