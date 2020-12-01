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

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

/**
 * @author David Arques
 */
public class ReferringSocialMedia {

	public ReferringSocialMedia() {
	}

	public ReferringSocialMedia(String name, String title, int trafficAmount) {
		_name = name;
		_title = title;
		_trafficAmount = trafficAmount;
	}

	public String getName() {
		return _name;
	}

	public String getTitle() {
		return _title;
	}

	public int getTrafficAmount() {
		return _trafficAmount;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setTrafficAmount(int trafficAmount) {
		_trafficAmount = trafficAmount;
	}

	public JSONObject toJSONObject() {
		return JSONUtil.put(
			"name", _name
		).put(
			"title", _title
		).put(
			"trafficAmount", _trafficAmount
		);
	}

	private String _name;
	private String _title;
	private int _trafficAmount;

}