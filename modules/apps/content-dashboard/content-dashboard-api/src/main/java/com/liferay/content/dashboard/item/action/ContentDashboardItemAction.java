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

package com.liferay.content.dashboard.item.action;

/**
 * @author David Arques
 */
public class ContentDashboardItemAction {

	public ContentDashboardItemAction(
		String label, String name, Type type, String url) {

		_label = label;
		_name = name;
		_type = type;
		_url = url;
	}

	public String getLabel() {
		return _label;
	}

	public String getName() {
		return _name;
	}

	public Type getType() {
		return _type;
	}

	public String getUrl() {
		return _url;
	}

	public enum Type {

		VIEW_EMBEDDED

	}

	private final String _label;
	private final String _name;
	private final Type _type;
	private final String _url;

}