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

package com.liferay.analytics.reports.web.internal.item.action;

import com.liferay.analytics.reports.info.action.ViewAnalyticsReportsPanelContentDashboardItemActionProvider;
import com.liferay.analytics.reports.web.internal.constants.AnalyticsReportsPortletKeys;
import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.item.action.ContentDashboardItemActionProvider;
import com.liferay.content.dashboard.item.action.ContentDashboardItemActionProviderException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.ResourceBundle;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(service = ContentDashboardItemActionProvider.class)
public class ViewAnalyticsReportsPanelContentDashboardItemActionProviderImpl
	implements ViewAnalyticsReportsPanelContentDashboardItemActionProvider {

	@Override
	public ContentDashboardItemAction getContentDashboardItemAction(
			String className, long classPK,
			HttpServletRequest httpServletRequest)
		throws ContentDashboardItemActionProviderException {

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			_portal.getLocale(httpServletRequest), getClass());

		PortletURL portletURL = _portletURLFactory.create(
			httpServletRequest, AnalyticsReportsPortletKeys.ANALYTICS_REPORTS,
			RenderRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/analytics_reports_panel.jsp");

		try {
			portletURL.setWindowState(LiferayWindowState.EXCLUSIVE);
		}
		catch (WindowStateException windowStateException) {
			throw new ContentDashboardItemActionProviderException(
				windowStateException);
		}

		portletURL.setParameter(
			"classNameId",
			String.valueOf(_classNameLocalService.getClassNameId(className)));

		portletURL.setParameter("classPK", String.valueOf(classPK));

		return new ContentDashboardItemAction(
			_language.get(resourceBundle, "view-metrics"), "viewMetrics",
			ContentDashboardItemAction.Type.VIEW_EMBEDDED,
			portletURL.toString());
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private PortletURLFactory _portletURLFactory;

}