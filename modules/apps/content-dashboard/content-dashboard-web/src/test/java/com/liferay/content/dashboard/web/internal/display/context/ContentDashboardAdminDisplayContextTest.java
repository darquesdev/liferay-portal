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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletURL;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.language.LanguageImpl;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.util.HtmlImpl;
import com.liferay.portal.util.HttpImpl;
import com.liferay.portal.util.PortalImpl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Cristina González
 */
public class ContentDashboardAdminDisplayContextTest {

	@BeforeClass
	public static void setUpClass() {
		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(new HtmlImpl());

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		_http = new HttpImpl();

		_language = new LanguageImpl();

		LanguageResources languageResources = new LanguageResources();

		languageResources.setConfig(StringPool.BLANK);

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_language);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());

		PropsTestUtil.setProps(Collections.emptyMap());
	}

	@Test
	public void testGetEditURL() {
		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		MockLiferayPortletURL mockLiferayPortletURL =
			new MockLiferayPortletURL();

		mockLiferayPortletRenderRequest.setAttribute(
			"null" + StringPool.DASH + WebKeys.CURRENT_PORTLET_URL,
			mockLiferayPortletURL);

		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.LOCALE, LocaleUtil.US);

		ContentDashboardAdminDisplayContext
			contentDashboardAdminDisplayContext =
				new ContentDashboardAdminDisplayContext(
					null, null, _http, null, _language,
					mockLiferayPortletRenderRequest, null, new PortalImpl(),
					null);

		ContentDashboardItem contentDashboardItem = Mockito.mock(
			ContentDashboardItem.class);

		Mockito.when(
			contentDashboardItem.isEditURLEnabled(
				Mockito.any(HttpServletRequest.class))
		).thenReturn(
			true
		);

		Mockito.when(
			contentDashboardItem.getEditURL(
				Mockito.any(HttpServletRequest.class))
		).thenReturn(
			"validURL"
		);

		List<DropdownItem> dropdownItems =
			contentDashboardAdminDisplayContext.getDropdownItems(
				contentDashboardItem);

		DropdownItem dropdownItem = dropdownItems.get(0);

		Assert.assertEquals(
			"edit", _http.getPath(String.valueOf(dropdownItem.get("label"))));
		Assert.assertEquals(
			"validURL",
			_http.getPath(String.valueOf(dropdownItem.get("href"))));
	}

	@Test
	public void testGetProps() {
		AssetVocabularyLocalService assetVocabularyLocalService = Mockito.mock(
			AssetVocabularyLocalService.class);

		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getGroupId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		AssetVocabulary assetVocabulary = Mockito.mock(AssetVocabulary.class);

		Mockito.when(
			assetVocabulary.getTitle(LocaleUtil.US)
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			assetVocabularyLocalService.fetchGroupVocabulary(
				group.getGroupId(), "audience")
		).thenReturn(
			assetVocabulary
		);

		AssetVocabulary childAssetVocabulary = Mockito.mock(
			AssetVocabulary.class);

		Mockito.when(
			childAssetVocabulary.getTitle(LocaleUtil.US)
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			assetVocabularyLocalService.fetchGroupVocabulary(
				group.getGroupId(), "stage")
		).thenReturn(
			childAssetVocabulary
		);

		GroupLocalService groupLocalService = Mockito.mock(
			GroupLocalService.class);

		PortalImpl portalImpl = new PortalImpl() {

			@Override
			public long getCompanyId(PortletRequest portletRequest) {
				return RandomTestUtil.randomLong();
			}

		};

		Mockito.when(
			groupLocalService.fetchCompanyGroup(
				portalImpl.getCompanyId(Mockito.any(PortletRequest.class)))
		).thenReturn(
			group
		);

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		MockLiferayPortletURL mockLiferayPortletURL =
			new MockLiferayPortletURL();

		mockLiferayPortletRenderRequest.setAttribute(
			"null" + StringPool.DASH + WebKeys.CURRENT_PORTLET_URL,
			mockLiferayPortletURL);

		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.LOCALE, LocaleUtil.US);

		ContentDashboardAdminDisplayContext
			contentDashboardAdminDisplayContext =
				new ContentDashboardAdminDisplayContext(
					assetVocabularyLocalService, groupLocalService, null, null,
					null, mockLiferayPortletRenderRequest, null, portalImpl,
					null);

		Map<String, Object> data =
			contentDashboardAdminDisplayContext.getData();

		Map<String, Object> props = (Map<String, Object>)data.get("props");

		Assert.assertNotNull(props);

		JSONObject vocabulariesJSONObject = (JSONObject)props.get(
			"vocabularies");

		Assert.assertEquals(
			JSONUtil.put(
				"childVocabularyName",
				childAssetVocabulary.getTitle(LocaleUtil.US)
			).put(
				"vocabularyName", assetVocabulary.getTitle(LocaleUtil.US)
			).toJSONString(),
			vocabulariesJSONObject.toJSONString());
	}

	@Test
	public void testGetPropsWithMissingAssetVocabularies() {
		AssetVocabularyLocalService assetVocabularyLocalService = Mockito.mock(
			AssetVocabularyLocalService.class);

		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getGroupId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			assetVocabularyLocalService.fetchGroupVocabulary(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			null
		);

		Mockito.when(
			assetVocabularyLocalService.fetchGroupVocabulary(
				group.getGroupId(), "stage")
		).thenReturn(
			null
		);

		GroupLocalService groupLocalService = Mockito.mock(
			GroupLocalService.class);

		PortalImpl portalImpl = new PortalImpl() {

			@Override
			public long getCompanyId(PortletRequest portletRequest) {
				return RandomTestUtil.randomLong();
			}

		};

		Mockito.when(
			groupLocalService.fetchCompanyGroup(
				portalImpl.getCompanyId(Mockito.any(PortletRequest.class)))
		).thenReturn(
			group
		);

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		MockLiferayPortletURL mockLiferayPortletURL =
			new MockLiferayPortletURL();

		mockLiferayPortletRenderRequest.setAttribute(
			"null" + StringPool.DASH + WebKeys.CURRENT_PORTLET_URL,
			mockLiferayPortletURL);

		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.LOCALE, LocaleUtil.US);

		ContentDashboardAdminDisplayContext
			contentDashboardAdminDisplayContext =
				new ContentDashboardAdminDisplayContext(
					assetVocabularyLocalService, groupLocalService, null, null,
					null, mockLiferayPortletRenderRequest, null, portalImpl,
					null);

		Map<String, Object> data =
			contentDashboardAdminDisplayContext.getData();

		Map<String, Object> props = (Map<String, Object>)data.get("props");

		Assert.assertNotNull(props);

		Assert.assertNull(props.get("vocabularies"));
	}

	@Test
	public void testGetPropsWithMissingAssetVocabulary() {
		AssetVocabularyLocalService assetVocabularyLocalService = Mockito.mock(
			AssetVocabularyLocalService.class);

		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getGroupId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			assetVocabularyLocalService.fetchGroupVocabulary(
				group.getGroupId(), "audience")
		).thenReturn(
			null
		);

		AssetVocabulary childAssetVocabulary = Mockito.mock(
			AssetVocabulary.class);

		Mockito.when(
			childAssetVocabulary.getTitle(LocaleUtil.US)
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			assetVocabularyLocalService.fetchGroupVocabulary(
				group.getGroupId(), "stage")
		).thenReturn(
			childAssetVocabulary
		);

		GroupLocalService groupLocalService = Mockito.mock(
			GroupLocalService.class);

		PortalImpl portalImpl = new PortalImpl() {

			@Override
			public long getCompanyId(PortletRequest portletRequest) {
				return RandomTestUtil.randomLong();
			}

		};

		Mockito.when(
			groupLocalService.fetchCompanyGroup(
				portalImpl.getCompanyId(Mockito.any(PortletRequest.class)))
		).thenReturn(
			group
		);

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		MockLiferayPortletURL mockLiferayPortletURL =
			new MockLiferayPortletURL();

		mockLiferayPortletRenderRequest.setAttribute(
			"null" + StringPool.DASH + WebKeys.CURRENT_PORTLET_URL,
			mockLiferayPortletURL);

		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.LOCALE, LocaleUtil.US);

		ContentDashboardAdminDisplayContext
			contentDashboardAdminDisplayContext =
				new ContentDashboardAdminDisplayContext(
					assetVocabularyLocalService, groupLocalService, null, null,
					null, mockLiferayPortletRenderRequest, null, portalImpl,
					null);

		Map<String, Object> data =
			contentDashboardAdminDisplayContext.getData();

		Map<String, Object> props = (Map<String, Object>)data.get("props");

		Assert.assertNotNull(props);

		JSONObject vocabulariesJSONObject = (JSONObject)props.get(
			"vocabularies");

		Assert.assertEquals(
			JSONUtil.put(
				"vocabularyName", childAssetVocabulary.getTitle(LocaleUtil.US)
			).toJSONString(),
			vocabulariesJSONObject.toJSONString());
	}

	@Test
	public void testGetPropsWithMissingChildAssetVocabulary() {
		AssetVocabularyLocalService assetVocabularyLocalService = Mockito.mock(
			AssetVocabularyLocalService.class);

		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getGroupId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		AssetVocabulary assetVocabulary = Mockito.mock(AssetVocabulary.class);

		Mockito.when(
			assetVocabulary.getTitle(LocaleUtil.US)
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			assetVocabularyLocalService.fetchGroupVocabulary(
				group.getGroupId(), "audience")
		).thenReturn(
			assetVocabulary
		);

		Mockito.when(
			assetVocabularyLocalService.fetchGroupVocabulary(
				group.getGroupId(), "stage")
		).thenReturn(
			null
		);

		GroupLocalService groupLocalService = Mockito.mock(
			GroupLocalService.class);

		PortalImpl portalImpl = new PortalImpl() {

			@Override
			public long getCompanyId(PortletRequest portletRequest) {
				return RandomTestUtil.randomLong();
			}

		};

		Mockito.when(
			groupLocalService.fetchCompanyGroup(
				portalImpl.getCompanyId(Mockito.any(PortletRequest.class)))
		).thenReturn(
			group
		);

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		MockLiferayPortletURL mockLiferayPortletURL =
			new MockLiferayPortletURL();

		mockLiferayPortletRenderRequest.setAttribute(
			"null" + StringPool.DASH + WebKeys.CURRENT_PORTLET_URL,
			mockLiferayPortletURL);

		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.LOCALE, LocaleUtil.US);

		ContentDashboardAdminDisplayContext
			contentDashboardAdminDisplayContext =
				new ContentDashboardAdminDisplayContext(
					assetVocabularyLocalService, groupLocalService, null, null,
					null, mockLiferayPortletRenderRequest, null, portalImpl,
					null);

		Map<String, Object> data =
			contentDashboardAdminDisplayContext.getData();

		Map<String, Object> props = (Map<String, Object>)data.get("props");

		Assert.assertNotNull(props);

		JSONObject vocabulariesJSONObject = (JSONObject)props.get(
			"vocabularies");

		Assert.assertEquals(
			JSONUtil.put(
				"vocabularyName", assetVocabulary.getTitle(LocaleUtil.US)
			).toJSONString(),
			vocabulariesJSONObject.toJSONString());
	}

	@Test
	public void testGetURLBackURL() {
		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		MockLiferayPortletURL mockLiferayPortletURL =
			new MockLiferayPortletURL();

		mockLiferayPortletRenderRequest.setAttribute(
			"null" + StringPool.DASH + WebKeys.CURRENT_PORTLET_URL,
			mockLiferayPortletURL);

		ContentDashboardAdminDisplayContext
			contentDashboardAdminDisplayContext =
				new ContentDashboardAdminDisplayContext(
					null, null, _http, null, _language,
					mockLiferayPortletRenderRequest, null, new PortalImpl(),
					null);

		ContentDashboardItem contentDashboardItem = Mockito.mock(
			ContentDashboardItem.class);

		Mockito.when(
			contentDashboardItem.isViewURLEnabled(
				Mockito.any(HttpServletRequest.class))
		).thenReturn(
			true
		);

		Mockito.when(
			contentDashboardItem.getViewURL(
				Mockito.any(HttpServletRequest.class))
		).thenReturn(
			"validURL"
		);

		List<DropdownItem> dropdownItems =
			contentDashboardAdminDisplayContext.getDropdownItems(
				contentDashboardItem);

		DropdownItem dropdownItem = dropdownItems.get(0);

		Assert.assertEquals(
			HtmlUtil.escapeURL(String.valueOf(mockLiferayPortletURL)),
			_http.getParameter(
				String.valueOf(dropdownItem.get("href")), "p_l_back_url"));
	}

	@Test
	public void testGetURLBackURLWithBackURLParameter() {
		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		mockLiferayPortletRenderRequest.setAttribute(
			"null" + StringPool.DASH + WebKeys.CURRENT_PORTLET_URL,
			new MockLiferayPortletURL());

		String backURL = RandomTestUtil.randomString();

		mockLiferayPortletRenderRequest.setParameter("backURL", backURL);

		ContentDashboardAdminDisplayContext
			contentDashboardAdminDisplayContext =
				new ContentDashboardAdminDisplayContext(
					null, null, _http, null, _language,
					mockLiferayPortletRenderRequest, null, new PortalImpl(),
					null);

		ContentDashboardItem contentDashboardItem = Mockito.mock(
			ContentDashboardItem.class);

		Mockito.when(
			contentDashboardItem.isViewURLEnabled(
				Mockito.any(HttpServletRequest.class))
		).thenReturn(
			true
		);

		Mockito.when(
			contentDashboardItem.getViewURL(
				Mockito.any(HttpServletRequest.class))
		).thenReturn(
			"validURL"
		);

		List<DropdownItem> dropdownItems =
			contentDashboardAdminDisplayContext.getDropdownItems(
				contentDashboardItem);

		DropdownItem dropdownItem = dropdownItems.get(0);

		Assert.assertEquals(
			backURL,
			_http.getParameter(
				String.valueOf(dropdownItem.get("href")), "p_l_back_url"));
	}

	@Test
	public void testGetViewURL() {
		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		MockLiferayPortletURL mockLiferayPortletURL =
			new MockLiferayPortletURL();

		mockLiferayPortletRenderRequest.setAttribute(
			"null" + StringPool.DASH + WebKeys.CURRENT_PORTLET_URL,
			mockLiferayPortletURL);

		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.LOCALE, LocaleUtil.US);

		ContentDashboardAdminDisplayContext
			contentDashboardAdminDisplayContext =
				new ContentDashboardAdminDisplayContext(
					null, null, _http, null, _language,
					mockLiferayPortletRenderRequest, null, new PortalImpl(),
					null);

		ContentDashboardItem contentDashboardItem = Mockito.mock(
			ContentDashboardItem.class);

		Mockito.when(
			contentDashboardItem.isViewURLEnabled(
				Mockito.any(HttpServletRequest.class))
		).thenReturn(
			true
		);

		Mockito.when(
			contentDashboardItem.getViewURL(
				Mockito.any(HttpServletRequest.class))
		).thenReturn(
			"validURL"
		);

		List<DropdownItem> dropdownItems =
			contentDashboardAdminDisplayContext.getDropdownItems(
				contentDashboardItem);

		DropdownItem dropdownItem = dropdownItems.get(0);

		Assert.assertEquals(
			"view", _http.getPath(String.valueOf(dropdownItem.get("label"))));
		Assert.assertEquals(
			"validURL",
			_http.getPath(String.valueOf(dropdownItem.get("href"))));
	}

	private static Http _http;
	private static Language _language;

}