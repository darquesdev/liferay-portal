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

package com.liferay.segments.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.exception.SegmentsEntryKeyException;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsEntryLocalServiceUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Arques
 */
@RunWith(Arquillian.class)
public class SegmentsEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddSegmentsEntry() throws PortalException {
		Assert.assertEquals(
			0,
			_segmentsEntryLocalService.getSegmentsEntriesCount(
				_group.getGroupId()));

		String nameDefaultLocale = RandomTestUtil.randomString();
		String descriptionDefaultLocale = RandomTestUtil.randomString();
		String criteria = RandomTestUtil.randomString();
		String key = RandomTestUtil.randomString();
		String type = RandomTestUtil.randomString();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.addSegmentsEntry(
				_createMapWithDefaultLocale(nameDefaultLocale),
				_createMapWithDefaultLocale(descriptionDefaultLocale), true,
				criteria, key, type, serviceContext);

		Assert.assertEquals(
			1,
			_segmentsEntryLocalService.getSegmentsEntriesCount(
				_group.getGroupId()));

		Assert.assertTrue(segmentsEntry.isActive());

		Assert.assertEquals(nameDefaultLocale, segmentsEntry.getName());
		Assert.assertEquals(
			nameDefaultLocale, segmentsEntry.getName(LocaleUtil.getDefault()));

		Assert.assertEquals(
			descriptionDefaultLocale, segmentsEntry.getDescription());
		Assert.assertEquals(
			descriptionDefaultLocale,
			segmentsEntry.getDescription(LocaleUtil.getDefault()));

		Assert.assertEquals(criteria, segmentsEntry.getCriteria());
		Assert.assertEquals(key, segmentsEntry.getKey());
		Assert.assertEquals(type, segmentsEntry.getType());
	}

	@Test(expected = SegmentsEntryKeyException.class)
	public void testAddSegmentsEntryWithExistingKey() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.addSegmentsEntry(
				_createMapWithDefaultLocale(RandomTestUtil.randomString()),
				_createMapWithDefaultLocale(RandomTestUtil.randomString()),
				true, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				serviceContext);

		_segmentsEntryLocalService.addSegmentsEntry(
			_createMapWithDefaultLocale(RandomTestUtil.randomString()),
			_createMapWithDefaultLocale(RandomTestUtil.randomString()), true,
			RandomTestUtil.randomString(), segmentsEntry.getKey(),
			RandomTestUtil.randomString(), serviceContext);
	}

	private Map<Locale, String> _createMapWithDefaultLocale(String value) {
		Map<Locale, String> map = new HashMap<>();

		map.put(LocaleUtil.getDefault(), value);

		return map;
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private SegmentsEntryLocalService _segmentsEntryLocalService;

}