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
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author David Arques
 */
public class TrafficSourceTest {

	@Before
	public void setUp() {
		new JSONFactoryUtil().setJSONFactory(new JSONFactoryImpl());
	}

	@Test
	public void testToJSONObject() {
		String name = RandomTestUtil.randomString();
		int trafficAmount = RandomTestUtil.randomInt();
		float trafficShare = _randomFloat(0F, 100F);

		TrafficSource trafficSource = new TrafficSource(
			name, trafficAmount, trafficShare);

		String helpMessage = RandomTestUtil.randomString();

		Map<String, String> titleMap = HashMapBuilder.put(
			name, RandomTestUtil.randomString()
		).build();

		JSONObject jsonObject = trafficSource.toJSONObject(
			helpMessage, titleMap);

		Assert.assertEquals(
			JSONUtil.put(
				"helpMessage", helpMessage
			).put(
				"name", name
			).put(
				"share", trafficShare
			).put(
				"title", titleMap.get(name)
			).put(
				"value", trafficAmount
			).toString(),
			jsonObject.toString());
	}

	private float _randomFloat(float min, float max) {
		return min + new Random().nextFloat() * (max - min);
	}

}