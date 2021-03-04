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

package com.liferay.analytics.reports.web.internal.model.util;

import com.liferay.analytics.reports.web.internal.model.LayoutAudit;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.util.FileImpl;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author David Arques
 */
public class LayoutAuditUtilTest {

	@BeforeClass
	public static void setUpClass() {
		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	@Test
	public void testToLayoutAudit() throws Exception {
		LayoutAudit layoutAudit = LayoutAuditUtil.toLayoutAudit(
			JSONFactoryUtil.createJSONObject(
				new String(
					FileUtil.getBytes(
						getClass(), "dependencies/pagespeed-response.json"))));

		Assert.assertEquals(3, layoutAudit.getAccessibilityIssuesCount());
		Assert.assertEquals(13, layoutAudit.getSeoIssuesCount());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testToLayoutAuditWithNullJSONObject() {
		LayoutAuditUtil.toLayoutAudit(null);
	}

}