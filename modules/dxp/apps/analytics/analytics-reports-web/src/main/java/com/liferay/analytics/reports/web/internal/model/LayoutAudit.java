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

import java.util.Objects;

/**
 * @author David Arques
 */
public class LayoutAudit {

	public LayoutAudit(int accessibilityIssuesCount, int seoIssuesCount) {
		_accessibilityIssuesCount = accessibilityIssuesCount;
		_seoIssuesCount = seoIssuesCount;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LayoutAudit)) {
			return false;
		}

		LayoutAudit layoutAudit = (LayoutAudit)object;

		if (Objects.equals(
				_accessibilityIssuesCount,
				layoutAudit._accessibilityIssuesCount) &&
			Objects.equals(_seoIssuesCount, layoutAudit._seoIssuesCount)) {

			return true;
		}

		return false;
	}

	public int getAccessibilityIssuesCount() {
		return _accessibilityIssuesCount;
	}

	public int getSeoIssuesCount() {
		return _seoIssuesCount;
	}

	private final int _accessibilityIssuesCount;
	private final int _seoIssuesCount;

}