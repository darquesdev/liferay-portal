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

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author David Arques
 */
public class SocialMediaTrafficDetails {

	public SocialMediaTrafficDetails() {
	}

	public SocialMediaTrafficDetails(
		List<ReferringSocialMedia> referringSocialMedia) {

		_referringSocialMedia = referringSocialMedia;
	}

	public List<ReferringSocialMedia> getReferringSocialMedia() {
		return _referringSocialMedia;
	}

	public JSONObject putJSONObject(JSONObject jsonObject) {
		return jsonObject.put(
			"referringSocialMedia", _getReferringSocialMediaJSONArray());
	}

	public void setReferringSocialMedia(
		List<ReferringSocialMedia> referringSocialMedia) {

		_referringSocialMedia = referringSocialMedia;
	}

	private JSONArray _getReferringSocialMediaJSONArray() {
		Stream<ReferringSocialMedia> stream = _referringSocialMedia.stream();

		return JSONUtil.putAll(
			stream.map(
				ReferringSocialMedia::toJSONObject
			).toArray());
	}

	private List<ReferringSocialMedia> _referringSocialMedia;

}