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

import com.liferay.analytics.reports.info.action.AnalyticsReportsContentDashboardItemActionProvider;
import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItem;
import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItemTracker;
import com.liferay.analytics.reports.web.internal.constants.AnalyticsReportsPortletKeys;
import com.liferay.asset.display.page.util.AssetDisplayPageUtil;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.item.action.ContentDashboardItemActionException;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ResourceBundle;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(service = AnalyticsReportsContentDashboardItemActionProvider.class)
public class AnalyticsReportsContentDashboardItemActionProviderImpl
	implements AnalyticsReportsContentDashboardItemActionProvider {

	@Override
	public ContentDashboardItemAction getContentDashboardItemAction(
			String className, long classPK,
			HttpServletRequest httpServletRequest)
		throws ContentDashboardItemActionException {

		try {
			if (!isShowContentDashboardItemAction(
					className, classPK, httpServletRequest)) {

				return null;
			}

			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				_portal.getLocale(httpServletRequest), getClass());

			PortletURL portletURL = _portletURLFactory.create(
				httpServletRequest,
				AnalyticsReportsPortletKeys.ANALYTICS_REPORTS,
				RenderRequest.RENDER_PHASE);

			portletURL.setParameter("mvcPath", "/analytics_reports_panel.jsp");

			portletURL.setWindowState(LiferayWindowState.EXCLUSIVE);

			portletURL.setParameter(
				"classNameId",
				String.valueOf(
					_classNameLocalService.getClassNameId(className)));

			portletURL.setParameter("classPK", String.valueOf(classPK));

			return new AnalyticsReportsContentDashboardItemAction(
				_language.get(resourceBundle, "view-metrics"),
				portletURL.toString());
		}
		catch (Exception exception) {
			throw new ContentDashboardItemActionException(exception);
		}
	}

	@Override
	public boolean isShowContentDashboardItemAction(
			String className, long classPK,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		InfoDisplayContributor<?> infoDisplayContributor =
			_infoDisplayContributorTracker.getInfoDisplayContributor(className);

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

		if (!_hasEditPermission(
				infoDisplayObjectProvider.getClassNameId(),
				infoDisplayObjectProvider.getClassPK(), layoutPageTemplateEntry,
				themeDisplay)) {

			return false;
		}

		return true;
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