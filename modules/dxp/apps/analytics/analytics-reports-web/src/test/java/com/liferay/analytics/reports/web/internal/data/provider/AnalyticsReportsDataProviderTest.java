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

package com.liferay.analytics.reports.web.internal.data.provider;

import com.liferay.analytics.reports.web.internal.client.AsahFaroBackendClient;
import com.liferay.analytics.reports.web.internal.model.TrafficSource;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.exception.NestableRuntimeException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author David Arques
 */
public class AnalyticsReportsDataProviderTest {

	@BeforeClass
	public static void setUpClass() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	@Test
	public void testGetTotalReads() throws Exception {
		AnalyticsReportsDataProvider analyticsReportsDataProvider =
			new AnalyticsReportsDataProvider(
				new AsahFaroBackendClient() {

					@Override
					public String doGet(long companyId, String path) {
						return "12345";
					}

				});

		Long totalReads = analyticsReportsDataProvider.getTotalReads(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString());

		Assert.assertEquals(Long.valueOf(12345), totalReads);
	}

	@Test(expected = PortalException.class)
	public void testGetTotalReadsWithAsahFaroBackendError() throws Exception {
		AnalyticsReportsDataProvider analyticsReportsDataProvider =
			new AnalyticsReportsDataProvider(
				new AsahFaroBackendClient() {

					@Override
					public String doGet(long companyId, String path) {
						throw new NestableRuntimeException();
					}

				});

		analyticsReportsDataProvider.getTotalReads(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString());
	}

	@Test
	public void testGetTotalViews() throws Exception {
		AnalyticsReportsDataProvider analyticsReportsDataProvider =
			new AnalyticsReportsDataProvider(
				new AsahFaroBackendClient() {

					@Override
					public String doGet(long companyId, String path) {
						return "12345";
					}

				});

		Long totalViews = analyticsReportsDataProvider.getTotalViews(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString());

		Assert.assertEquals(Long.valueOf(12345), totalViews);
	}

	@Test(expected = PortalException.class)
	public void testGetTotalViewsWithAsahFaroBackendError() throws Exception {
		AnalyticsReportsDataProvider analyticsReportsDataProvider =
			new AnalyticsReportsDataProvider(
				new AsahFaroBackendClient() {

					@Override
					public String doGet(long companyId, String path) {
						throw new NestableRuntimeException();
					}

				});

		analyticsReportsDataProvider.getTotalViews(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString());
	}

	@Test
	public void testGetTrafficSources() throws Exception {
		AnalyticsReportsDataProvider analyticsReportsDataProvider =
			new AnalyticsReportsDataProvider(
				new AsahFaroBackendClient() {

					@Override
					public String doGet(long companyId, String path) {
						return JSONUtil.putAll(
							JSONUtil.put(
								"name", "search"
							).put(
								"trafficAmount", 3849
							).put(
								"trafficShare", 94.25D
							),
							JSONUtil.put(
								"name", "paid"
							).put(
								"trafficAmount", 235
							).put(
								"trafficShare", 5.75D
							)
						).toString();
					}

				});

		List<TrafficSource> trafficSources =
			analyticsReportsDataProvider.getTrafficSources(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString());

		Assert.assertEquals(
			trafficSources.toString(), 2, trafficSources.size());
		Assert.assertEquals(
			new TrafficSource("search", 3849, 94.25D), trafficSources.get(0));
		Assert.assertEquals(
			new TrafficSource("paid", 235, 5.75D), trafficSources.get(1));
	}

	@Test(expected = PortalException.class)
	public void testGetTrafficSourcesWithAsahFaroBackendError()
		throws Exception {

		AnalyticsReportsDataProvider analyticsReportsDataProvider =
			new AnalyticsReportsDataProvider(
				new AsahFaroBackendClient() {

					@Override
					public String doGet(long companyId, String path) {
						throw new NestableRuntimeException();
					}

				});

		analyticsReportsDataProvider.getTrafficSources(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString());
	}

}