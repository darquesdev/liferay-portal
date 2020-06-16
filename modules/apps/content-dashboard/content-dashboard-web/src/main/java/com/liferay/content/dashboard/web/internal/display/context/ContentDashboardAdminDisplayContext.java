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

package com.liferay.content.dashboard.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public class ContentDashboardAdminDisplayContext {

	public ContentDashboardAdminDisplayContext(
		AssetVocabularyLocalService assetVocabularyLocalService,
		GroupLocalService groupLocalService, Http http, Language language,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, Portal portal,
		SearchContainer<ContentDashboardItem<?>> searchContainer) {

		_assetVocabularyLocalService = assetVocabularyLocalService;
		_groupLocalService = groupLocalService;
		_http = http;
		_language = language;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_portal = portal;
		_searchContainer = searchContainer;

		_currentURL = String.valueOf(
			PortletURLUtil.getCurrent(
				_liferayPortletRequest, _liferayPortletResponse));
	}

	public Map<String, Object> getData() {
		if (_data != null) {
			return _data;
		}

		_data = HashMapBuilder.<String, Object>put(
			"props", _getProps()
		).build();

		return _data;
	}

	public List<DropdownItem> getDropdownItems(
		ContentDashboardItem contentDashboardItem) {

		Locale locale = _portal.getLocale(_liferayPortletRequest);

		return DropdownItemList.of(
			() -> {
				HttpServletRequest httpServletRequest =
					_portal.getHttpServletRequest(_liferayPortletRequest);

				if (!contentDashboardItem.isViewURLEnabled(
						httpServletRequest)) {

					return null;
				}

				DropdownItem dropdownItem = new DropdownItem();

				dropdownItem.setHref(
					_getURLWithBackURL(
						contentDashboardItem.getViewURL(httpServletRequest)));
				dropdownItem.setLabel(_language.get(locale, "view"));

				return dropdownItem;
			},
			() -> {
				HttpServletRequest httpServletRequest =
					_portal.getHttpServletRequest(_liferayPortletRequest);

				if (!contentDashboardItem.isEditURLEnabled(
						httpServletRequest)) {

					return null;
				}

				DropdownItem dropdownItem = new DropdownItem();

				dropdownItem.setHref(
					_getURLWithBackURL(
						contentDashboardItem.getEditURL(httpServletRequest)));
				dropdownItem.setLabel(_language.get(locale, "edit"));

				return dropdownItem;
			});
	}

	public SearchContainer<ContentDashboardItem<?>> getSearchContainer() {
		return _searchContainer;
	}

	public Integer getStatus() {
		if (_status != null) {
			return _status;
		}

		_status = ParamUtil.getInteger(
			_liferayPortletRequest, "status", WorkflowConstants.STATUS_ANY);

		return _status;
	}

	private Map<String, Object> _getProps() {
		Group group = _groupLocalService.fetchCompanyGroup(
			_portal.getCompanyId(_liferayPortletRequest));

		if (group == null) {
			return Collections.emptyMap();
		}

		return HashMapBuilder.<String, Object>put(
			"vocabularies",
			_getVocabulariesJSONObject(
				_assetVocabularyLocalService.fetchGroupVocabulary(
					group.getGroupId(), "audience"),
				_assetVocabularyLocalService.fetchGroupVocabulary(
					group.getGroupId(), "stage"))
		).build();
	}

	private String _getURLWithBackURL(String url) {
		String backURL = ParamUtil.getString(_liferayPortletRequest, "backURL");

		if (Validator.isNotNull(backURL)) {
			return _http.setParameter(url, "p_l_back_url", backURL);
		}

		return _http.setParameter(url, "p_l_back_url", _currentURL);
	}

	private JSONObject _getVocabulariesJSONObject(
		AssetVocabulary audienceAssetVocabulary,
		AssetVocabulary stageAssetVocabulary) {

		if ((audienceAssetVocabulary == null) &&
			(stageAssetVocabulary == null)) {

			return null;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (audienceAssetVocabulary != null) {
			jsonObject.put(
				"vocabularyName",
				audienceAssetVocabulary.getTitle(
					_portal.getLocale(_liferayPortletRequest)));

			if (stageAssetVocabulary != null) {
				jsonObject.put(
					"childVocabularyName",
					stageAssetVocabulary.getTitle(
						_portal.getLocale(_liferayPortletRequest)));
			}
		}
		else {
			jsonObject.put(
				"vocabularyName",
				stageAssetVocabulary.getTitle(
					_portal.getLocale(_liferayPortletRequest)));
		}

		return jsonObject;
	}

	private final AssetVocabularyLocalService _assetVocabularyLocalService;
	private final String _currentURL;
	private Map<String, Object> _data;
	private final GroupLocalService _groupLocalService;
	private final Http _http;
	private final Language _language;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final Portal _portal;
	private final SearchContainer<ContentDashboardItem<?>> _searchContainer;
	private Integer _status;

}