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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.exception.SegmentsExperimentNameException;
import com.liferay.segments.exception.SegmentsExperimentStatusException;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.SegmentsExperimentLocalService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.List;

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
public class SegmentsExperimentLocalServiceTest {

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
	public void testAddSegmentsExperiment() throws Exception {
		SegmentsExperience segmentsExperience = _addSegmentsExperience();

		SegmentsExperiment expectedSegmentsExperiment =
			_segmentsExperimentLocalService.addSegmentsExperiment(
				segmentsExperience.getSegmentsExperienceId(),
				segmentsExperience.getClassNameId(),
				segmentsExperience.getClassPK(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		SegmentsExperiment actualSegmentsExperiment =
			_segmentsExperimentLocalService.getSegmentsExperiment(
				expectedSegmentsExperiment.getSegmentsExperimentId());

		Assert.assertNotNull(actualSegmentsExperiment);
		Assert.assertEquals(
			expectedSegmentsExperiment.getSegmentsExperimentKey(),
			actualSegmentsExperiment.getSegmentsExperimentKey());
		Assert.assertEquals(
			expectedSegmentsExperiment.getSegmentsExperienceId(),
			actualSegmentsExperiment.getSegmentsExperienceId());
		Assert.assertEquals(
			expectedSegmentsExperiment.getSegmentsEntryId(),
			actualSegmentsExperiment.getSegmentsEntryId());
		Assert.assertEquals(
			expectedSegmentsExperiment.getName(),
			actualSegmentsExperiment.getName());
		Assert.assertEquals(
			expectedSegmentsExperiment.getDescription(),
			actualSegmentsExperiment.getDescription());
		Assert.assertEquals(
			expectedSegmentsExperiment.getStatus(),
			actualSegmentsExperiment.getStatus());
		Assert.assertEquals(0, actualSegmentsExperiment.getStatus());
		Assert.assertEquals(
			expectedSegmentsExperiment.getTypeSettings(),
			actualSegmentsExperiment.getTypeSettings());
	}

	@Test(expected = SegmentsExperimentNameException.class)
	public void testAddSegmentsExperimentWithEmptyName() throws Exception {
		SegmentsExperience segmentsExperience = _addSegmentsExperience();

		_segmentsExperimentLocalService.addSegmentsExperiment(
			segmentsExperience.getSegmentsExperienceId(),
			segmentsExperience.getClassNameId(),
			segmentsExperience.getClassPK(), StringPool.BLANK,
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testAddSegmentsExperimentWithExistingExperimentInDraft()
		throws Exception {

		SegmentsExperience segmentsExperience = _addSegmentsExperience();

		_segmentsExperimentLocalService.addSegmentsExperiment(
			segmentsExperience.getSegmentsExperienceId(),
			segmentsExperience.getClassNameId(),
			segmentsExperience.getClassPK(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_segmentsExperimentLocalService.addSegmentsExperiment(
			segmentsExperience.getSegmentsExperienceId(),
			segmentsExperience.getClassNameId(),
			segmentsExperience.getClassPK(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test(expected = SegmentsExperimentNameException.class)
	public void testAddSegmentsExperimentWithNullName() throws Exception {
		SegmentsExperience segmentsExperience = _addSegmentsExperience();

		_segmentsExperimentLocalService.addSegmentsExperiment(
			segmentsExperience.getSegmentsExperienceId(),
			segmentsExperience.getClassNameId(),
			segmentsExperience.getClassPK(), null,
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test
	public void testDeleteSegmentsExperiments() throws Exception {
		SegmentsExperience segmentsExperience = _addSegmentsExperience();

		_segmentsExperimentLocalService.addSegmentsExperiment(
			segmentsExperience.getSegmentsExperienceId(),
			segmentsExperience.getClassNameId(),
			segmentsExperience.getClassPK(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_segmentsExperimentLocalService.deleteSegmentsExperiments(
			segmentsExperience.getSegmentsExperienceId(),
			segmentsExperience.getClassNameId(),
			segmentsExperience.getClassPK());

		Assert.assertTrue(
			ListUtil.isNull(
				_segmentsExperimentLocalService.getSegmentsExperiments(
					segmentsExperience.getSegmentsExperienceId(),
					segmentsExperience.getClassNameId(),
					segmentsExperience.getClassPK())));
	}

	@Test
	public void testGetSegmentsExperiments() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(
			Layout.class.getName());
		Layout layout = LayoutTestUtil.addLayout(_group);

		SegmentsExperience segmentsExperience1 =
			SegmentsTestUtil.addSegmentsExperience(
				_group.getGroupId(), classNameId, layout.getPlid());

		SegmentsExperience segmentsExperience2 =
			SegmentsTestUtil.addSegmentsExperience(
				_group.getGroupId(), classNameId, layout.getPlid());

		SegmentsExperiment segmentsExperiment1 =
			_segmentsExperimentLocalService.addSegmentsExperiment(
				SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT, classNameId,
				layout.getPlid(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		SegmentsExperiment segmentsExperiment2 =
			_segmentsExperimentLocalService.addSegmentsExperiment(
				segmentsExperience1.getSegmentsExperienceId(), classNameId,
				layout.getPlid(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		SegmentsExperiment segmentsExperiment3 =
			_segmentsExperimentLocalService.addSegmentsExperiment(
				segmentsExperience2.getSegmentsExperienceId(), classNameId,
				layout.getPlid(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		List<SegmentsExperiment> segmentsExperiments =
			_segmentsExperimentLocalService.getSegmentsExperiments(
				layout.getGroupId(), classNameId, layout.getPlid());

		Assert.assertEquals(
			segmentsExperiments.toString(), 3, segmentsExperiments.size());
		Assert.assertEquals(segmentsExperiment3, segmentsExperiments.get(0));
		Assert.assertEquals(segmentsExperiment2, segmentsExperiments.get(1));
		Assert.assertEquals(segmentsExperiment1, segmentsExperiments.get(2));
	}

	private SegmentsExperience _addSegmentsExperience() throws Exception {
		long classNameId = _classNameLocalService.getClassNameId(
			Layout.class.getName());
		Layout layout = LayoutTestUtil.addLayout(_group);

		return SegmentsTestUtil.addSegmentsExperience(
			_group.getGroupId(), classNameId, layout.getPlid());
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private SegmentsExperimentLocalService _segmentsExperimentLocalService;

}