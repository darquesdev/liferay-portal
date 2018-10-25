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

package com.liferay.segments.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.util.SegmentsEntryHelper;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo Garcia
 */
@RunWith(Arquillian.class)
public class SegmentsEntryHelperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetSegmentsEntries() throws Exception {

		// TODO

		// Given users 1,2

		// And
		// a static segment with user 1
		// a dynamic segment matching user 1
		// a static segment with user 2
		// a dynamic segment with user 2

		// When _segmentsEntryHelper.getSegmentsEntries for user 1

		// Then assert result contains dynamic and static segments for 1

	}

	@Test
	public void testGetSegmentsEntryClassPKsWithCriteria() throws Exception {

		// TODO

		// Given users 1, 2

		// And a segment with criteria matching 1

		// When _segmentsEntryHelper.getSegmentsEntryClassPKs() for segment

		// Then assert result contains user 1 Id

	}

	@Test
	public void testGetSegmentsEntryClassPKsWithoutCriteria() throws Exception {

		// TODO

		// Given users 1, 2

		// Add segment static with 1

		// When _segmentsEntryHelper.getSegmentsEntryClassPKs() for segment

		// The assert contains user 1 Id

	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private SegmentsEntryHelper _segmentsEntryHelper;

	@Inject
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@DeleteAfterTestRun
	private User _user1;

	@DeleteAfterTestRun
	private User _user2;

	@Inject
	private UserLocalService _userLocalService;

}