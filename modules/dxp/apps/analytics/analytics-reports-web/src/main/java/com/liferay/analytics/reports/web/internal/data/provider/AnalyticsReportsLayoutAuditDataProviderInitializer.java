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
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(
	configurationPid = "com.liferay.analytics.reports.web.internal.configuration.AnalyticsReportsPageSpeedConfiguration",
	immediate = true,
	service = AnalyticsReportsLayoutAuditDataProviderInitializer.class
)
public class AnalyticsReportsLayoutAuditDataProviderInitializer {

	@Activate
	protected void activate(Map<String, Object> properties) {
		try {
			AnalyticsReportsLayoutAuditDataProvider
				analyticsReportsLayoutAuditDataProvider =
					new AnalyticsReportsLayoutAuditDataProvider(
						_http,
						ConfigurableUtil.createConfigurable(
							AnalyticsReportsPageSpeedConfiguration.class,
							properties));

			LayoutAudit layoutAudit =
				analyticsReportsLayoutAuditDataProvider.getLayoutAudit(
					LocaleUtil.getSiteDefault(),
					"http://e17e0c30f685.ngrok.io/");

			if ((layoutAudit != null) && _log.isInfoEnabled()) {
				_log.info(layoutAudit);
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsReportsLayoutAuditDataProviderInitializer.class);

	@Reference
	private Http _http;

}