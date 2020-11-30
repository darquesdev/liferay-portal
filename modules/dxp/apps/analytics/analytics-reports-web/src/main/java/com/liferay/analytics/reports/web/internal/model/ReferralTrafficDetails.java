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
public class ReferralTrafficDetails {

	public ReferralTrafficDetails() {
	}

	public ReferralTrafficDetails(
		List<ReferringURL> referringDomains,
		List<ReferringURL> referringPages) {

		_referringDomains = referringDomains;
		_referringPages = referringPages;
	}

	public List<ReferringURL> getReferringDomains() {
		return _referringDomains;
	}

	public List<ReferringURL> getReferringPages() {
		return _referringPages;
	}

	public void setReferringDomains(List<ReferringURL> referringDomains) {
		_referringDomains = referringDomains;
	}

	public void setReferringPages(List<ReferringURL> referringPages) {
		_referringPages = referringPages;
	}

	public JSONObject toJSONObject(JSONObject jsonObject) {
		return jsonObject.put(
			"referringDomains", _getReferringDomainsJSONArray()
		).put(
			"referringPages", _getReferringPagesJSONArray()
		);
	}

	private JSONArray _getReferringDomainsJSONArray() {
		Stream<ReferringURL> stream = _referringDomains.stream();

		return JSONUtil.putAll(
			stream.map(
				ReferringURL::toJSONObject
			).toArray());
	}

	private JSONArray _getReferringPagesJSONArray() {
		Stream<ReferringURL> stream = _referringPages.stream();

		return JSONUtil.putAll(
			stream.map(
				ReferringURL::toJSONObject
			).toArray());
	}

	private List<ReferringURL> _referringDomains;
	private List<ReferringURL> _referringPages;

}