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

package com.liferay.content.dashboard.web.internal.item;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Date;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Cristina González
 */
public class JournalArticleContentDashboardItemTest {

	@Test
	public void testGetExpirationDate() {
		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(null, journalArticle);

		Assert.assertEquals(
			journalArticle.getExpirationDate(),
			journalArticleContentDashboardItem.getExpirationDate());
	}

	@Test
	public void testGetModifiedDate() {
		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(null, journalArticle);

		Assert.assertEquals(
			journalArticle.getModifiedDate(),
			journalArticleContentDashboardItem.getModifiedDate());
	}

	@Test
	public void testGetPublishDate() {
		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(null, journalArticle);

		Assert.assertEquals(
			journalArticle.getDisplayDate(),
			journalArticleContentDashboardItem.getPublishDate());
	}

	@Test
	public void testGetStatusLabel() {
		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(null, journalArticle);

		Assert.assertEquals(
			WorkflowConstants.LABEL_APPROVED,
			journalArticleContentDashboardItem.getStatusLabel(LocaleUtil.US));
	}

	@Test
	public void testGetStatusStyle() {
		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(null, journalArticle);

		Assert.assertEquals(
			"success", journalArticleContentDashboardItem.getStatusStyle());
	}

	@Test
	public void testGetSubtype() {
		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(null, journalArticle);

		Assert.assertEquals(
			"subtype",
			journalArticleContentDashboardItem.getSubtype(LocaleUtil.US));
	}

	@Test
	public void testGetTitle() {
		JournalArticle journalArticle = _getJournalArticle();

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(null, journalArticle);

		Assert.assertEquals(
			journalArticle.getTitle(LocaleUtil.US),
			journalArticleContentDashboardItem.getTitle(LocaleUtil.US));
	}

	@Test
	public void testGetViewURL() throws PortalException {
		JournalArticle journalArticle = _getJournalArticle();

		AssetDisplayPageFriendlyURLProvider
			assetDisplayPageFriendlyURLProvider = Mockito.mock(
				AssetDisplayPageFriendlyURLProvider.class);

		Mockito.when(
			assetDisplayPageFriendlyURLProvider.getFriendlyURL(
				Mockito.anyString(), Mockito.anyLong(), Mockito.anyObject())
		).thenReturn(
			"validURL"
		);

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				assetDisplayPageFriendlyURLProvider, journalArticle);

		Assert.assertEquals(
			"validURL", journalArticleContentDashboardItem.getViewURL(null));
	}

	@Test
	public void testGetViewURLWithNullFriendlyURL() {
		JournalArticle journalArticle = _getJournalArticle();

		AssetDisplayPageFriendlyURLProvider
			assetDisplayPageFriendlyURLProvider = Mockito.mock(
				AssetDisplayPageFriendlyURLProvider.class);

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				assetDisplayPageFriendlyURLProvider, journalArticle);

		Assert.assertEquals(
			StringPool.BLANK,
			journalArticleContentDashboardItem.getViewURL(null));
	}

	@Test
	public void testIsViewURLEnabled() throws PortalException {
		JournalArticle journalArticle = _getJournalArticle();

		AssetDisplayPageFriendlyURLProvider
			assetDisplayPageFriendlyURLProvider = Mockito.mock(
				AssetDisplayPageFriendlyURLProvider.class);

		Mockito.when(
			assetDisplayPageFriendlyURLProvider.getFriendlyURL(
				Mockito.anyString(), Mockito.anyLong(), Mockito.anyObject())
		).thenReturn(
			"validURL"
		);

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				assetDisplayPageFriendlyURLProvider, journalArticle);

		Assert.assertTrue(
			journalArticleContentDashboardItem.isViewURLEnabled(null));
	}

	@Test
	public void testIsViewURLEnabledWithNullFriendlyURL() {
		JournalArticle journalArticle = _getJournalArticle();

		AssetDisplayPageFriendlyURLProvider
			assetDisplayPageFriendlyURLProvider = Mockito.mock(
				AssetDisplayPageFriendlyURLProvider.class);

		JournalArticleContentDashboardItem journalArticleContentDashboardItem =
			new JournalArticleContentDashboardItem(
				assetDisplayPageFriendlyURLProvider, journalArticle);

		Assert.assertFalse(
			journalArticleContentDashboardItem.isViewURLEnabled(null));
	}

	private JournalArticle _getJournalArticle() {
		JournalArticle journalArticle = Mockito.mock(JournalArticle.class);

		DDMStructure ddmStructure = Mockito.mock(DDMStructure.class);

		Mockito.when(
			ddmStructure.getName(Mockito.any(Locale.class))
		).thenReturn(
			"subtype"
		);

		Mockito.when(
			journalArticle.getDDMStructure()
		).thenReturn(
			ddmStructure
		);

		Mockito.when(
			journalArticle.getDisplayDate()
		).thenReturn(
			new Date()
		);

		Mockito.when(
			journalArticle.getModifiedDate()
		).thenReturn(
			new Date()
		);

		Mockito.when(
			journalArticle.getExpirationDate()
		).thenReturn(
			new Date()
		);

		Mockito.when(
			journalArticle.getTitle(Mockito.any(Locale.class))
		).thenReturn(
			RandomTestUtil.randomString()
		);

		return journalArticle;
	}

}