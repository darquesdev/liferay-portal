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

import com.liferay.portal.kernel.util.Validator;

/**
 * @author David Arques
 */
public class ContentDashboardItemAction {

	public ContentDashboardItemAction(String label, String name, String url) {
		if (Validator.isNull(label)) {
			throw new IllegalArgumentException("Label is null");
		}

		if (Validator.isNull(name)) {
			throw new IllegalArgumentException("Name is null");
		}

		if (Validator.isNull(url)) {
			throw new IllegalArgumentException("URL is null");
		}

		_label = label;
		_name = name;
		_url = url;
	}

	public String getLabel() {
		return _label;
	}

	public String getName() {
		return _name;
	}

	public String getUrl() {
		return _url;
	}

	private final String _label;
	private final String _name;
	private final String _url;

}