/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.content.dashboard.web.internal.portlet;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.content.dashboard.web.internal.constants.ContentDashboardPortletKeys;
import com.liferay.content.dashboard.web.internal.constants.ContentDashboardWebKeys;
import com.liferay.content.dashboard.web.internal.dao.search.ContentDashboardItemSearchContainerFactory;
import com.liferay.content.dashboard.web.internal.display.context.ContentDashboardAdminDisplayContext;
import com.liferay.content.dashboard.web.internal.display.context.ContentDashboardAdminManagementToolbarDisplayContext;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItem;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItemFactoryTracker;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-content-dashboard-admin",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.preferences-owned-by-group=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Content Dashboard",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ContentDashboardPortletKeys.CONTENT_DASHBOARD_ADMIN,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class ContentDashboardAdminPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		LiferayPortletRequest liferayPortletRequest =
			_portal.getLiferayPortletRequest(renderRequest);
		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(renderResponse);

		ContentDashboardItemSearchContainerFactory
			contentDashboardItemSearchContainerFactory =
				ContentDashboardItemSearchContainerFactory.getInstance(
					_contentDashboardItemFactoryTracker, _portal, renderRequest,
					renderResponse);

		SearchContainer<ContentDashboardItem<?>> searchContainer =
			contentDashboardItemSearchContainerFactory.create();

		ContentDashboardAdminDisplayContext
			contentDashboardAdminDisplayContext =
				new ContentDashboardAdminDisplayContext(
					Collections.singletonMap(
						"props", _getProps(liferayPortletRequest)), _http,
					_itemSelector, _language, liferayPortletRequest,
					liferayPortletResponse, _portal, searchContainer);

		renderRequest.setAttribute(
			ContentDashboardWebKeys.CONTENT_DASHBOARD_ADMIN_DISPLAY_CONTEXT,
			contentDashboardAdminDisplayContext);

		ContentDashboardAdminManagementToolbarDisplayContext
			contentDashboardAdminManagementToolbarDisplayContext =
				new ContentDashboardAdminManagementToolbarDisplayContext(
					contentDashboardAdminDisplayContext,
					_portal.getHttpServletRequest(renderRequest),
					liferayPortletRequest, liferayPortletResponse,
					_userLocalService);

		renderRequest.setAttribute(
			ContentDashboardWebKeys.
				CONTENT_DASHBOARD_ADMIN_MANAGEMENT_TOOLBAR_DISPLAY_CONTEXT,
			contentDashboardAdminManagementToolbarDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	private Map<String, JSONObject> _getProps(
		LiferayPortletRequest liferayPortletRequest) {
		Locale locale = _portal.getLocale(liferayPortletRequest);

		return Optional.ofNullable(
			_groupLocalService.fetchCompanyGroup(
				_portal.getCompanyId(liferayPortletRequest))
		).map(
			group ->
				Collections.singletonMap(
					"vocabularies",
					_getVocabulariesJSONObject(
						_assetVocabularyLocalService.fetchGroupVocabulary(
							group.getGroupId(), "audience"),
						_assetVocabularyLocalService.fetchGroupVocabulary(
							group.getGroupId(), "stage"), locale)
				)
		).orElseGet(
			Collections::emptyMap
		);

	}

	private JSONObject _getVocabulariesJSONObject(
		AssetVocabulary assetVocabulary, AssetVocabulary childAssetVocabulary,
		Locale locale) {

		if ((assetVocabulary == null) && (childAssetVocabulary == null)) {
			return null;
		}

		if (assetVocabulary != null) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put(
				"vocabularyName", assetVocabulary.getTitle(locale));

			if (childAssetVocabulary != null) {
				jsonObject.put(
					"childVocabularyName",
					childAssetVocabulary.getTitle(locale));
			}

			return jsonObject;
		}
		else {
			return JSONUtil.put(
				"vocabularyName", childAssetVocabulary.getTitle(locale));
		}
	}

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private ContentDashboardItemFactoryTracker
		_contentDashboardItemFactoryTracker;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Http _http;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}