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

package com.liferay.content.dashboard.web.internal.display.context;

import java.util.Collections;
import java.util.List;

/**
 * @author David Arques
 */
public class AssetCategoriesAggregation {

	public AssetCategoriesAggregation(String key, long docCount) {
		_key = key;
		_docCount = docCount;
		_childAggregations = Collections.emptyList();
	}

	public List<AssetCategoriesAggregation> getChildAggregations() {
		return _childAggregations;
	}

	public long getDocCount() {
		return _docCount;
	}

	public String getKey() {
		return _key;
	}

	public void setChildAggregations(
		List<AssetCategoriesAggregation> childAggregations) {

		_childAggregations = childAggregations;
	}

	private List<AssetCategoriesAggregation> _childAggregations;
	private final long _docCount;
	private final String _key;

}