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

package com.liferay.content.dashboard.web.internal.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author David Arques
 */
public class AssetCategoryMetric {

	public AssetCategoryMetric(String key, long value) {
		_key = key;
		_value = value;
		_assetCategoryMetrics = Collections.emptyList();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AssetCategoryMetric)) {
			return false;
		}

		AssetCategoryMetric assetCategoryMetric = (AssetCategoryMetric)object;

		if (Objects.equals(
				_assetCategoryMetrics,
				assetCategoryMetric._assetCategoryMetrics) &&
			Objects.equals(_key, assetCategoryMetric._key) &&
			Objects.equals(_value, assetCategoryMetric._value)) {

			return true;
		}

		return false;
	}

	public List<AssetCategoryMetric> getAssetCategoryMetrics() {
		return _assetCategoryMetrics;
	}

	public String getKey() {
		return _key;
	}

	public long getValue() {
		return _value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_assetCategoryMetrics, _key, _value);
	}

	public void setAssetCategoryMetrics(
		List<AssetCategoryMetric> assetCategoryMetrics) {

		_assetCategoryMetrics = assetCategoryMetrics;
	}

	private List<AssetCategoryMetric> _assetCategoryMetrics;
	private final String _key;
	private final long _value;

}