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

package com.liferay.portal.kernel.templateparser;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alexander Chow
 * @author Raymond Augé
 */
public class TemplateNode extends LinkedHashMap<String, Object> {

	public TemplateNode(
		ThemeDisplay themeDisplay, String name, String data, String type,
		Map<String, String> attributes) {

		_themeDisplay = themeDisplay;

		put("attributes", attributes);
		put("name", name);
		put("data", data);
		put("type", type);
		put("options", new ArrayList<String>());
		put("optionsMap", new LinkedHashMap<String, String>());
	}

	public void appendChild(TemplateNode templateNode) {
		_childTemplateNodes.put(templateNode.getName(), templateNode);

		put(templateNode.getName(), templateNode);
	}

	public void appendChildren(List<TemplateNode> templateNodes) {
		for (TemplateNode templateNode : templateNodes) {
			appendChild(templateNode);
		}
	}

	public void appendOption(String option) {
		List<String> options = getOptions();

		options.add(option);
	}

	public void appendOptionMap(String value, String label) {
		Map<String, String> optionsMap = getOptionsMap();

		optionsMap.put(value, label);
	}

	public void appendOptions(List<String> options) {
		List<String> curOptions = getOptions();

		curOptions.addAll(options);
	}

	public void appendOptionsMap(Map<String, String> optionMap) {
		Map<String, String> optionsMap = getOptionsMap();

		optionsMap.putAll(optionMap);
	}

	public void appendSibling(TemplateNode templateNode) {
		_siblingTemplateNodes.add(templateNode);
	}

	public String getAttribute(String name) {
		Map<String, String> attributes = getAttributes();

		if (attributes == null) {
			return StringPool.BLANK;
		}

		return attributes.get(name);
	}

	public Map<String, String> getAttributes() {
		return (Map<String, String>)get("attributes");
	}

	public TemplateNode getChild(String name) {
		return _childTemplateNodes.get(name);
	}

	public List<TemplateNode> getChildren() {
		return new ArrayList<>(_childTemplateNodes.values());
	}

	public String getData() {
		String type = getType();

		if (type.equals("ddm-journal-article")) {
			return _getLatestArticleData();
		}
		else if (type.equals("document_library") || type.equals("image")) {
			return _getFileEntryData();
		}

		return (String)get("data");
	}

	public String getFriendlyUrl() {
		String type = getType();

		if (type.equals("ddm-journal-article") ||
			type.equals("journal_article")) {

			return _getDDMJournalArticleFriendlyURL();
		}
		else if (type.equals("ddm-link-to-page") ||
				 type.equals("link_to_layout")) {

			return getUrl();
		}

		return StringPool.BLANK;
	}

	public String getName() {
		Object name = get("name");

		if ((name == null) || (name instanceof String)) {
			return (String)name;
		}

		return "name";
	}

	public List<String> getOptions() {
		return (List<String>)get("options");
	}

	public Map<String, String> getOptionsMap() {
		return (Map<String, String>)get("optionsMap");
	}

	public List<TemplateNode> getSiblings() {
		return _siblingTemplateNodes;
	}

	public String getType() {
		return (String)get("type");
	}

	public String getUrl() {
		String type = getType();

		if (!type.equals("ddm-link-to-page") &&
			!type.equals("link_to_layout")) {

			return StringPool.BLANK;
		}

		String data = (String)get("data");

		if (!JSONUtil.isValid(data)) {
			return StringPool.BLANK;
		}

		long layoutGroupId = 0;
		long layoutId = 0;
		String layoutType = StringPool.BLANK;

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(data);

			layoutGroupId = jsonObject.getLong("groupId");
			layoutId = jsonObject.getLong("layoutId");

			if (jsonObject.getBoolean("privateLayout")) {
				layoutType = _LAYOUT_TYPE_PRIVATE_GROUP;
			}
			else {
				layoutType = _LAYOUT_TYPE_PUBLIC;
			}
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse JSON from data: " + data);
			}

			return StringPool.BLANK;
		}

		if (Validator.isNull(layoutType)) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(5);

		if (layoutType.equals(_LAYOUT_TYPE_PRIVATE_GROUP)) {
			sb.append(PortalUtil.getPathFriendlyURLPrivateGroup());
		}
		else if (layoutType.equals(_LAYOUT_TYPE_PRIVATE_USER)) {
			sb.append(PortalUtil.getPathFriendlyURLPrivateUser());
		}
		else if (layoutType.equals(_LAYOUT_TYPE_PUBLIC)) {
			sb.append(PortalUtil.getPathFriendlyURLPublic());
		}
		else {
			sb.append("@friendly_url_current@");
		}

		sb.append(StringPool.SLASH);

		try {
			Group group = GroupLocalServiceUtil.getGroup(layoutGroupId);

			String name = group.getFriendlyURL();

			name = name.substring(1);

			sb.append(name);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			sb.append("@group_id@");
		}

		sb.append(StringPool.SLASH);
		sb.append(layoutId);

		return sb.toString();
	}

	private String _getDDMJournalArticleFriendlyURL() {
		if (_themeDisplay == null) {
			return StringPool.BLANK;
		}

		String data = (String)get("data");

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(data);

			AssetRendererFactory<?> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(
						jsonObject.getString("className"));

			if (assetRendererFactory == null) {
				return StringPool.BLANK;
			}

			long classPK = GetterUtil.getLong(jsonObject.getLong("classPK"));

			AssetRenderer<?> assetRenderer =
				assetRendererFactory.getAssetRenderer(classPK);

			if (assetRenderer == null) {
				return StringPool.BLANK;
			}

			HttpServletRequest httpServletRequest = _themeDisplay.getRequest();

			PortletRequest portletRequest =
				(PortletRequest)httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST);
			PortletResponse portletResponse =
				(PortletResponse)httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE);

			return assetRenderer.getURLViewInContext(
				PortalUtil.getLiferayPortletRequest(portletRequest),
				PortalUtil.getLiferayPortletResponse(portletResponse),
				StringPool.BLANK);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return StringPool.BLANK;
	}

	private String _getFileEntryData() {
		String data = (String)get("data");

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(data);

			String uuid = jsonObject.getString("uuid");
			long groupId = jsonObject.getLong("groupId");

			if (Validator.isNull(uuid) && (groupId == 0)) {
				return StringPool.BLANK;
			}

			FileEntry fileEntry =
				DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
					uuid, groupId);

			return DLUtil.getPreviewURL(
				fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK,
				false, true);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return StringPool.BLANK;
	}

	private String _getLatestArticleData() {
		String data = (String)get("data");

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(data);

			AssetRendererFactory<?> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(
						jsonObject.getString("className"));

			if (assetRendererFactory == null) {
				return StringPool.BLANK;
			}

			long classPK = GetterUtil.getLong(jsonObject.getLong("classPK"));

			AssetRenderer<?> assetRenderer =
				assetRendererFactory.getAssetRenderer(classPK);

			if (assetRenderer == null) {
				return StringPool.BLANK;
			}

			if (Objects.equals(
					jsonObject.getString("uuid"), assetRenderer.getUuid())) {

				return data;
			}

			String updatedTitle = assetRenderer.getTitle(
				LocaleUtil.fromLanguageId(
					assetRenderer.getDefaultLanguageId()));

			jsonObject.put("title", updatedTitle);

			Map<Locale, String> titleMap = new HashMap<>();

			for (String languageId : assetRenderer.getAvailableLanguageIds()) {
				Locale locale = LocaleUtil.fromLanguageId(languageId);

				if (locale != null) {
					titleMap.put(locale, assetRenderer.getTitle(locale));
				}
			}

			jsonObject.put(
				"titleMap", titleMap
			).put(
				"uuid", assetRenderer.getUuid()
			);

			return jsonObject.toJSONString();
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse JSON from data: " + data);
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception.getMessage());
			}
		}

		return (String)get("data");
	}

	private static final String _LAYOUT_TYPE_PRIVATE_GROUP = "private-group";

	private static final String _LAYOUT_TYPE_PRIVATE_USER = "private-user";

	private static final String _LAYOUT_TYPE_PUBLIC = "public";

	private static final Log _log = LogFactoryUtil.getLog(TemplateNode.class);

	private final Map<String, TemplateNode> _childTemplateNodes =
		new LinkedHashMap<>();
	private final List<TemplateNode> _siblingTemplateNodes = new ArrayList<>();
	private ThemeDisplay _themeDisplay;

}