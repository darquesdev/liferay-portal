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

import com.liferay.analytics.reports.web.internal.model.LayoutAudit;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author David Arques
 */
public final class LayoutAuditUtil {

	public static LayoutAudit toLayoutAudit(JSONObject jsonObject) {
		if (jsonObject == null) {
			throw new IllegalArgumentException("JSONObject is null");
		}

		Stream<String> auditsSeoStream = _auditsSeo.stream();
		Stream<String> auditsAccessibilityStream =
			_auditsAccessibility.stream();

		return new LayoutAudit(
			auditsAccessibilityStream.mapToInt(
				audit -> _getAuditIssuesCount(audit, jsonObject)
			).sum(),
			auditsSeoStream.mapToInt(
				audit -> _getAuditIssuesCount(audit, jsonObject)
			).sum());
	}

	private static int _getAuditIssuesCount(
		String auditName, JSONObject jsonObject) {

		JSONObject auditJSONObject = JSONUtil.getValueAsJSONObject(
			jsonObject, "JSONObject/lighthouseResult", "JSONObject/audits",
			"JSONObject/" + auditName);

		int score = auditJSONObject.getInt("score", -1);

		int issuesCount = (score == 0) ? 1 : 0;

		if (!auditJSONObject.has("details")) {
			return issuesCount;
		}

		JSONArray itemsJSONArray = JSONUtil.getValueAsJSONArray(
			auditJSONObject, "JSONObject/details", "JSONArray/items");

		return Math.max(issuesCount, itemsJSONArray.length());
	}

	private static final Set<String> _auditsAccessibility = new HashSet<>(
		Arrays.asList(
			"color-contrast", "image-alt", "input-image-alt", "video-caption"));
	private static final Set<String> _auditsSeo = new HashSet<>(
		Arrays.asList(
			"canonical", "crawlable-anchors", "document-title", "font-size",
			"hreflang", "image-aspect-ratio", "is-crawlable", "link-text",
			"meta-description", "tap-targets"));
}