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

package com.liferay.analytics.reports.web.internal.model;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Collections;
import java.util.Map;
import java.util.Random;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author David Arques
 */
public class TrafficSourceTest {

	@BeforeClass
	public static void setUpClass() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	@Test
	public void testToJSONObject() {
		TrafficSource trafficSource = new TrafficSource(
			RandomTestUtil.randomString(), RandomTestUtil.randomInt(),
			_randomFloat(0F, 100F));

		String helpMessage = RandomTestUtil.randomString();

		Map<String, String> titleMap = Collections.singletonMap(
			trafficSource.getName(), RandomTestUtil.randomString());

		Assert.assertEquals(
			JSONUtil.put(
				"helpMessage", helpMessage
			).put(
				"name", trafficSource.getName()
			).put(
				"share", trafficSource.getTrafficShare()
			).put(
				"title", titleMap.get(trafficSource.getName())
			).put(
				"value", trafficSource.getTrafficAmount()
			).toString(),
			String.valueOf(trafficSource.toJSONObject(helpMessage, titleMap)));
	}

	private float _randomFloat(float min, float max) {
		return min + new Random().nextFloat() * (max - min);
	}

}