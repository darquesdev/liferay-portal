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

package com.liferay.content.dashboard.item.action;

import com.liferay.portal.kernel.test.util.RandomTestUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Eduardo García
 */
public class ContentDashboardItemActionTest {

	@Test
	public void testCreation() {
		String label = RandomTestUtil.randomString();
		String name = RandomTestUtil.randomString();
		String url = RandomTestUtil.randomString();

		ContentDashboardItemAction contentDashboardItemAction =
			new ContentDashboardItemAction(label, name, url);

		Assert.assertEquals(label, contentDashboardItemAction.getLabel());
		Assert.assertEquals(name, contentDashboardItemAction.getName());
		Assert.assertEquals(url, contentDashboardItemAction.getUrl());
	}

	@Test
	public void testCreationWithNullProperties() {
		ContentDashboardItemAction contentDashboardItemAction =
			new ContentDashboardItemAction(null, null, null);

		Assert.assertNull(contentDashboardItemAction.getLabel());
		Assert.assertNull(contentDashboardItemAction.getName());
		Assert.assertNull(contentDashboardItemAction.getUrl());
	}

}