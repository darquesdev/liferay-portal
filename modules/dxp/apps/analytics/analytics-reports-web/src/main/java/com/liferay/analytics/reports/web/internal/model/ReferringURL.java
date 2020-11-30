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
public class ReferringURL {

	public ReferringURL() {
	}

	public ReferringURL(int trafficAmount, String url) {
		_trafficAmount = trafficAmount;
		_url = url;
	}

	public int getTrafficAmount() {
		return _trafficAmount;
	}

	public String getUrl() {
		return _url;
	}

	public void setTrafficAmount(int trafficAmount) {
		_trafficAmount = trafficAmount;
	}

	public void setUrl(String url) {
		_url = url;
	}

	public JSONObject toJSONObject() {
		return JSONUtil.put(
			"trafficAmount", _trafficAmount
		).put(
			"url", _url
		);
	}

	private int _trafficAmount;
	private String _url;

}