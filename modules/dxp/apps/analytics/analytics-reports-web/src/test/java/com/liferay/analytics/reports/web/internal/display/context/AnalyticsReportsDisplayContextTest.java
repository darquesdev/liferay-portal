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

package com.liferay.analytics.reports.web.internal.display.context;

import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItem;
import com.liferay.analytics.reports.web.internal.data.provider.AnalyticsReportsDataProvider;
import com.liferay.analytics.reports.web.internal.model.TrafficSource;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.util.FastDateFormatFactoryImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author David Arques
 */
@RunWith(MockitoJUnitRunner.class)
public class AnalyticsReportsDisplayContextTest {

	@Before
	public void setUp() {
		new JSONFactoryUtil().setJSONFactory(new JSONFactoryImpl());
		new FastDateFormatFactoryUtil().setFastDateFormatFactory(
			new FastDateFormatFactoryImpl());
	}

	@Test
	public void testGetProps() {
		final int organicTrafficAmount = RandomTestUtil.randomInt();
		final float organicTrafficShare = _randomFloat(0F, 100F);

		final int paidTrafficAmount = RandomTestUtil.randomInt();
		final float paidTrafficShare = _randomFloat(0F, 100F);

		AnalyticsReportsDataProvider analyticsReportsDataProvider =
			_getAnalyticsReportsDataProvider(
				organicTrafficAmount, organicTrafficShare, paidTrafficAmount,
				paidTrafficShare);

		String authorName = RandomTestUtil.randomString();
		Locale locale = LocaleUtil.getDefault();
		String title = RandomTestUtil.randomString();

		AnalyticsReportsInfoItem analyticsReportsInfoItem =
			_getAnalyticsReportsItem(
				authorName, RandomTestUtil.nextDate(), title);

		String organicTitle = RandomTestUtil.randomString();
		String paidTitle = RandomTestUtil.randomString();
		String helpMessage = RandomTestUtil.randomString();

		ResourceBundle resourceBundle = _getResourceBundle(
			helpMessage, organicTitle, paidTitle);

		Date layoutPublishDate = RandomTestUtil.nextDate();

		ThemeDisplay themeDisplay = _getThemeDisplay(locale, layoutPublishDate);

		AnalyticsReportsDisplayContext analyticsReportsDisplayContext =
			new AnalyticsReportsDisplayContext(
				analyticsReportsDataProvider, analyticsReportsInfoItem, null,
				null, null, null, resourceBundle, themeDisplay);

		Map<String, Object> props = analyticsReportsDisplayContext.getProps();

		Assert.assertEquals(authorName, props.get("authorName"));

		Assert.assertEquals(layoutPublishDate, props.get("publishDate"));

		Assert.assertEquals(title, props.get("title"));

		JSONArray trafficSourcesJSONArray = (JSONArray)props.get(
			"trafficSources");

		Assert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"helpMessage", helpMessage
				).put(
					"name", _ORGANIC_TITLE_KEY
				).put(
					"share", organicTrafficShare
				).put(
					"title", organicTitle
				).put(
					"value", organicTrafficAmount
				),
				JSONUtil.put(
					"helpMessage", helpMessage
				).put(
					"name", _PAID_TITLE_KEY
				).put(
					"share", paidTrafficShare
				).put(
					"title", paidTitle
				).put(
					"value", paidTrafficAmount
				)
			).toJSONString(),
			trafficSourcesJSONArray.toJSONString());
	}

	private AnalyticsReportsDataProvider _getAnalyticsReportsDataProvider(
		int organicTrafficAmount, float organicTrafficShare,
		int paidTrafficAmount, float paidTrafficShare) {

		return new AnalyticsReportsDataProvider() {

			@Override
			public List<TrafficSource> getTrafficSources(
					long companyId, String url)
				throws PortalException {

				return Arrays.asList(
					new TrafficSource(
						"search", organicTrafficAmount, organicTrafficShare),
					new TrafficSource(
						_PAID_TITLE_KEY, paidTrafficAmount, paidTrafficShare));
			}

		};
	}

	private AnalyticsReportsInfoItem _getAnalyticsReportsItem(
		String authorName, Date publishDate, String title) {

		return new AnalyticsReportsInfoItem() {

			@Override
			public String getAuthorName(Object model) {
				return authorName;
			}

			@Override
			public Date getPublishDate(Object model) {
				return publishDate;
			}

			@Override
			public String getTitle(Object model, Locale locale) {
				return title;
			}

		};
	}

	private ResourceBundle _getResourceBundle(
		String helpMessage, String organicTitle, String paidTitle) {

		return new ResourceBundle() {

			@Override
			public Enumeration<String> getKeys() {
				return Collections.enumeration(
					Arrays.asList(
						_ORGANIC_TITLE_KEY, _PAID_TITLE_KEY,
						_HELP_MESSAGE_KEY));
			}

			@Override
			protected Object handleGetObject(String key) {
				if (Objects.equals(key, _ORGANIC_TITLE_KEY)) {
					return organicTitle;
				}
				else if (Objects.equals(key, _PAID_TITLE_KEY)) {
					return paidTitle;
				}
				else if (Objects.equals(key, _HELP_MESSAGE_KEY)) {
					return helpMessage;
				}

				return key;
			}

		};
	}

	private ThemeDisplay _getThemeDisplay(Locale locale, Date publishDate) {
		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.doReturn(
			locale
		).when(
			themeDisplay
		).getLocale();

		Layout layout = Mockito.mock(Layout.class);

		Mockito.doReturn(
			publishDate
		).when(
			layout
		).getPublishDate();

		Mockito.doReturn(
			layout
		).when(
			themeDisplay
		).getLayout();

		return themeDisplay;
	}

	private float _randomFloat(float min, float max) {
		return min + new Random().nextFloat() * (max - min);
	}

	private static final String _HELP_MESSAGE_KEY =
		"this-number-refers-to-the-volume-of-people-that-find-your-page-" +
			"through-a-search-engine";

	private static final String _ORGANIC_TITLE_KEY = "organic";

	private static final String _PAID_TITLE_KEY = "paid";

}