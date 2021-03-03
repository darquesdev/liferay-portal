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

package com.liferay.analytics.reports.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author David Arques
 */
@ExtendedObjectClassDefinition(generateUI = false)
@Meta.OCD(
	id = "com.liferay.analytics.reports.web.internal.configuration.AnalyticsReportsPageSpeedConfiguration"
)
public interface AnalyticsReportsPageSpeedConfiguration {

	@Meta.AD(
		deflt = "AIzaSyAN5Fze8wNeedBZdY58nCl0jzELpaTg7_k", required = false
	)
	public String apiKey();

	@Meta.AD(
		deflt = "desktop", optionValues = {"desktop", "mobile"},
		required = false
	)
	public String strategy();

	@Meta.AD(deflt = "30000", required = false)
	public int timeout();

}