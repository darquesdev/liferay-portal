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

package com.liferay.analytics.reports.web.internal.model.util;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

/**
 * @author David Arques
 */
public final class TrafficChannelUtil {

	public static JSONObject toJSONObject(
		boolean error, String helpMessage, String name, String title,
		long trafficAmount, double trafficShare) {

		JSONObject jsonObject = JSONUtil.put(
			"helpMessage", helpMessage
		).put(
			"name", name
		);

		if (!error) {
			jsonObject.put("share", String.format("%.1f", trafficShare));
		}

		jsonObject.put("title", title);

		if (!error) {
			jsonObject.put("value", Math.toIntExact(trafficAmount));
		}

		return jsonObject;
	}

	private TrafficChannelUtil() {
	}

}