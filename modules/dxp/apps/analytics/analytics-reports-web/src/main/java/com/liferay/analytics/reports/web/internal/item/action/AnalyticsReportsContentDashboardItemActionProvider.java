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

import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItem;
import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItemTracker;
import com.liferay.analytics.reports.web.internal.constants.AnalyticsReportsPortletKeys;
import com.liferay.analytics.reports.web.internal.util.AnalyticsReportsUtil;
import com.liferay.asset.display.page.util.AssetDisplayPageUtil;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.item.action.ContentDashboardItemActionProvider;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.Optional;
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
public class AnalyticsReportsContentDashboardItemActionProvider
	implements ContentDashboardItemActionProvider {

	@Override
	public Optional<ContentDashboardItemAction> getContentDashboardItemAction(
		String className, long classPK, HttpServletRequest httpServletRequest,
		Locale locale) {

		if (!_isShow(className, classPK, httpServletRequest)) {
			return Optional.empty();
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		PortletURL portletURL = _portletURLFactory.create(
			httpServletRequest, AnalyticsReportsPortletKeys.ANALYTICS_REPORTS,
			RenderRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/analytics_reports_panel.jsp");

		try {
			portletURL.setWindowState(LiferayWindowState.EXCLUSIVE);
		}
		catch (WindowStateException windowStateException) {
			ReflectionUtil.throwException(windowStateException);
		}

		portletURL.setParameter(
			"classNameId",
			String.valueOf(_classNameLocalService.getClassNameId(className)));

		portletURL.setParameter("classPK", String.valueOf(classPK));

		return Optional.of(
			new ContentDashboardItemAction(
				_language.get(resourceBundle, "view-metrics"), "viewMetrics",
				portletURL.toString()));
	}

	@Override
	public String getKey() {
		Class<?> clazz = getClass();

		return clazz.getName();
	}

	private boolean _hasEditPermission(
			long classNameId, long classPK,
			LayoutPageTemplateEntry layoutPageTemplateEntry,
			ThemeDisplay themeDisplay)
		throws PortalException {

		Layout layout = _layoutLocalService.fetchLayout(
			layoutPageTemplateEntry.getPlid());

		if (layout == null) {
			return false;
		}

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(classNameId);

		AssetRenderer<?> assetRenderer = null;

		if (assetRendererFactory != null) {
			assetRenderer = assetRendererFactory.getAssetRenderer(classPK);
		}

		if (((assetRenderer == null) ||
			 !assetRenderer.hasEditPermission(
				 themeDisplay.getPermissionChecker())) &&
			!LayoutPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), layout,
				ActionKeys.UPDATE)) {

			return false;
		}

		return true;
	}

	private boolean _isShow(
		String className, long classPK, HttpServletRequest httpServletRequest) {

		try {
			InfoDisplayContributor<?> infoDisplayContributor =
				_infoDisplayContributorTracker.getInfoDisplayContributor(
					className);

			if (infoDisplayContributor == null) {
				return false;
			}

			InfoDisplayObjectProvider<?> infoDisplayObjectProvider =
				infoDisplayContributor.getInfoDisplayObjectProvider(classPK);

			if ((infoDisplayObjectProvider == null) ||
				(infoDisplayObjectProvider.getDisplayObject() == null)) {

				return false;
			}

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			LayoutPageTemplateEntry layoutPageTemplateEntry =
				AssetDisplayPageUtil.getAssetDisplayPageLayoutPageTemplateEntry(
					themeDisplay.getScopeGroupId(),
					infoDisplayObjectProvider.getClassNameId(),
					infoDisplayObjectProvider.getClassPK(),
					infoDisplayObjectProvider.getClassTypeId());

			if (layoutPageTemplateEntry == null) {
				return false;
			}

			AnalyticsReportsInfoItem<?> analyticsReportsInfoItem =
				_analyticsReportsInfoItemTracker.getAnalyticsReportsInfoItem(
					_portal.getClassName(
						infoDisplayObjectProvider.getClassNameId()));

			if (analyticsReportsInfoItem == null) {
				return false;
			}

			PortalPreferences portalPreferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(
					httpServletRequest);

			boolean hidePanel = GetterUtil.getBoolean(
				portalPreferences.getValue(
					AnalyticsReportsPortletKeys.ANALYTICS_REPORTS,
					"hide-panel"));

			if (!AnalyticsReportsUtil.isAnalyticsConnected(
					themeDisplay.getCompanyId()) &&
				hidePanel) {

				return false;
			}

			if (!_hasEditPermission(
					infoDisplayObjectProvider.getClassNameId(),
					infoDisplayObjectProvider.getClassPK(),
					layoutPageTemplateEntry, themeDisplay)) {

				return false;
			}

			return true;
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsReportsContentDashboardItemActionProvider.class);

	@Reference
	private AnalyticsReportsInfoItemTracker _analyticsReportsInfoItemTracker;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletURLFactory _portletURLFactory;

}