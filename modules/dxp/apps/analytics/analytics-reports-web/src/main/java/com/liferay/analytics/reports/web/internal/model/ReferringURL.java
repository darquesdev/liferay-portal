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

	public ReferringURL(int traffic, String url) {
		_traffic = traffic;
		_url = url;
	}

	public int getTraffic() {
		return _traffic;
	}

	public String getUrl() {
		return _url;
	}

	public void setTraffic(int traffic) {
		_traffic = traffic;
	}

	public void setUrl(String url) {
		_url = url;
	}

	public JSONObject toJSONObject() {
		return JSONUtil.put(
			"traffic", _traffic
		).put(
			"url", _url
		);
	}

	private int _traffic;
	private String _url;

}