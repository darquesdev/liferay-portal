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

package com.liferay.analytics.reports.web.internal.util;

import com.liferay.analytics.reports.web.internal.constants.AnalyticsReportsPortletKeys;
import com.liferay.asset.display.page.constants.AssetDisplayPageWebKeys;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sarai Díaz
 */
public class AnalyticsReportsUtil {

	public static final String ANALYTICS_CLOUD_TRIAL_URL =
		"https://www.liferay.com/products/analytics-cloud/get-started";

	public static String getAnalyticsReportsPanelURL(
			HttpServletRequest httpServletRequest,
			PortletURLFactory portletURLFactory)
		throws WindowStateException {

		PortletURL portletURL = portletURLFactory.create(
			httpServletRequest, AnalyticsReportsPortletKeys.ANALYTICS_REPORTS,
			RenderRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/analytics_reports_panel.jsp");

		portletURL.setWindowState(LiferayWindowState.EXCLUSIVE);

		InfoDisplayObjectProvider<?> infoDisplayObjectProvider =
			(InfoDisplayObjectProvider<?>)httpServletRequest.getAttribute(
				AssetDisplayPageWebKeys.INFO_DISPLAY_OBJECT_PROVIDER);

		portletURL.setParameter(
			"classNameId",
			String.valueOf(infoDisplayObjectProvider.getClassNameId()));
		portletURL.setParameter(
			"classPK", String.valueOf(infoDisplayObjectProvider.getClassPK()));

		return portletURL.toString();
	}

	public static String getAsahFaroBackendDataSourceId(long companyId) {
		return PrefsPropsUtil.getString(
			companyId, "liferayAnalyticsDataSourceId");
	}

	public static String getAsahFaroBackendSecuritySignature(long companyId) {
		return PrefsPropsUtil.getString(
			companyId, "liferayAnalyticsFaroBackendSecuritySignature");
	}

	public static String getAsahFaroBackendURL(long companyId) {
		return PrefsPropsUtil.getString(
			companyId, "liferayAnalyticsFaroBackendURL");
	}

	public static boolean isAnalyticsConnected(long companyId) {
		if (Validator.isNull(
				PrefsPropsUtil.getString(
					companyId, "liferayAnalyticsDataSourceId")) ||
			Validator.isNull(
				PrefsPropsUtil.getString(
					companyId,
					"liferayAnalyticsFaroBackendSecuritySignature")) ||
			Validator.isNull(
				PrefsPropsUtil.getString(
					companyId, "liferayAnalyticsFaroBackendURL"))) {

			return false;
		}

		return true;
	}

	public static boolean isAnalyticsSynced(long companyId, long groupId) {
		if (!isAnalyticsConnected(companyId)) {
			return false;
		}

		if (PrefsPropsUtil.getBoolean(
				companyId, "liferayAnalyticsEnableAllGroupIds")) {

			return true;
		}

		String[] liferayAnalyticsGroupIds = PrefsPropsUtil.getStringArray(
			companyId, "liferayAnalyticsGroupIds", StringPool.COMMA);

		if (ArrayUtil.contains(
				liferayAnalyticsGroupIds, String.valueOf(groupId))) {

			return true;
		}

		return false;
	}

}