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

package com.liferay.analytics.reports.web.internal.data.provider;

import com.liferay.analytics.reports.web.internal.configuration.AnalyticsReportsPageSpeedConfiguration;
import com.liferay.analytics.reports.web.internal.model.LayoutAudit;
import com.liferay.analytics.reports.web.internal.model.util.LayoutAuditUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.NestableRuntimeException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.Locale;

import org.apache.http.HttpStatus;

/**
 * @author David Arques
 */
public class AnalyticsReportsLayoutAuditDataProvider {

	public AnalyticsReportsLayoutAuditDataProvider(
		Http http,
		AnalyticsReportsPageSpeedConfiguration
			analyticsReportsPageSpeedConfiguration) {

		_http = http;
		_analyticsReportsPageSpeedConfiguration =
			analyticsReportsPageSpeedConfiguration;
	}

	public LayoutAudit getLayoutAudit(Locale locale, String url)
		throws PortalException {

		try {
			return LayoutAuditUtil.toLayoutAudit(
				_getLayoutAuditJSONObject(locale, url));
		}
		catch (Exception exception) {
			throw new PortalException(
				"Unable to get the layout audit", exception);
		}
	}

	private JSONObject _getLayoutAuditJSONObject(Locale locale, String url)
		throws IOException, JSONException {

		if (Validator.isNull(
				_analyticsReportsPageSpeedConfiguration.apiKey())) {

			throw new IllegalArgumentException(
				"PageSpeed API key is not configured");
		}

		Http.Options options = new Http.Options();

		options.setLocation(_getServiceURL(locale, url));
		options.setTimeout(_analyticsReportsPageSpeedConfiguration.timeout());

		String response = _http.URLtoString(options);

		Http.Response httpResponse = options.getResponse();

		if (httpResponse.getResponseCode() != HttpStatus.SC_OK) {
			throw new NestableRuntimeException(
				StringBundler.concat(
					"Unexpected response status ",
					httpResponse.getResponseCode(), " with response message: ",
					response));
		}

		return JSONFactoryUtil.createJSONObject(response);
	}

	private String _getServiceURL(Locale locale, String url) {
		return StringBundler.concat(
			"https://pagespeedonline.googleapis.com/pagespeedonline/v5",
			"/runPagespeed?category=accessibility&category=best-practices",
			"&category=seo&key=",
			_analyticsReportsPageSpeedConfiguration.apiKey(), "&locale=",
			locale.toLanguageTag(), "&strategy=",
			_analyticsReportsPageSpeedConfiguration.strategy(), "&url=", url);
	}

	private final AnalyticsReportsPageSpeedConfiguration
		_analyticsReportsPageSpeedConfiguration;
	private final Http _http;

}