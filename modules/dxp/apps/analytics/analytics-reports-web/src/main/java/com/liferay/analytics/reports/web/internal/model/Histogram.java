/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.analytics.reports.web.internal.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Cistina González
 */
public class Histogram {

	public Histogram() {
	}

	public Histogram(List<HistogramMetric> histogramMetrics, double value) {
		if (histogramMetrics == null) {
			throw new IllegalArgumentException("Histogram Metrics are null");
		}

		_histogramMetrics = Collections.unmodifiableList(histogramMetrics);
		_value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Histogram)) {
			return false;
		}

		Histogram histogram = (Histogram)obj;

		if ((Double.compare(histogram._value, _value) == 0) &&
			Objects.equals(_histogramMetrics, histogram._histogramMetrics)) {

			return true;
		}

		return false;
	}

	public List<HistogramMetric> getHistogramMetrics() {
		return _histogramMetrics;
	}

	public Double getValue() {
		return _value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_histogramMetrics, _value);
	}

	public void setHistogramMetrics(List<HistogramMetric> histogramMetrics) {
		_histogramMetrics = histogramMetrics;
	}

	public void setValue(double value) {
		_value = value;
	}

	public JSONObject toJSONObject() {
		Stream<HistogramMetric> stream = _histogramMetrics.stream();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		stream.forEach(
			histogramMetric -> jsonArray.put(histogramMetric.toJSONObject()));

		return JSONUtil.put(
			"histogram", jsonArray
		).put(
			"value", _value
		);
	}

	@JsonProperty("histogram")
	private List<HistogramMetric> _histogramMetrics;

	private double _value;

}