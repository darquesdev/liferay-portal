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

package com.liferay.analytics.reports.web.internal.portlet.test;

import com.liferay.analytics.reports.web.internal.portlet.action.test.util.MockHttpUtil;
import com.liferay.analytics.reports.web.internal.portlet.action.test.util.MockThemeDisplayUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.display.page.constants.AssetDisplayPageWebKeys;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.Locale;

import javax.portlet.Portlet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class AnalyticsReportsPortletTest {

	@ClassRule
	@Rule
	public static final TestRule testRule = new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addLayout(_group);
	}

	@Test
	public void testServeResponse() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_portlet, "_http",
			MockHttpUtil.geHttp(
				Collections.singletonMap(
					"api/seo/1.0/traffic-sources", () -> "...")));

		try {
			MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
				_getMockLiferayPortletRenderRequest();

			_portlet.render(
				mockLiferayPortletRenderRequest,
				_getMockLiferayPortletRenderResponse());

			Object analyticsReportsDisplayContext =
				mockLiferayPortletRenderRequest.getAttribute(
					"ANALYTICS_REPORTS_DISPLAY_CONTEXT");

			Assert.assertNotNull(analyticsReportsDisplayContext);
		}
		finally {
			ReflectionTestUtil.setFieldValue(_portlet, "_http", _http);
		}
	}

	private InfoDisplayObjectProvider _getInfoDisplayObjectProvider() {
		return new InfoDisplayObjectProvider() {

			@Override
			public long getClassNameId() {
				return _portal.getClassNameId(
					"com.liferay.journal.model.JournalArticle");
			}

			@Override
			public long getClassPK() {
				return 0;
			}

			@Override
			public long getClassTypeId() {
				return 0;
			}

			@Override
			public String getDescription(Locale locale) {
				return null;
			}

			@Override
			public Object getDisplayObject() {
				return null;
			}

			@Override
			public long getGroupId() {
				return 0;
			}

			@Override
			public String getKeywords(Locale locale) {
				return null;
			}

			@Override
			public String getTitle(Locale locale) {
				return null;
			}

			@Override
			public String getURLTitle(Locale locale) {
				return null;
			}

		};
	}

	private MockLiferayPortletRenderRequest
		_getMockLiferayPortletRenderRequest() {

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		try {
			mockLiferayPortletRenderRequest.setAttribute(
				AssetDisplayPageWebKeys.INFO_DISPLAY_OBJECT_PROVIDER,
				_getInfoDisplayObjectProvider());

			mockLiferayPortletRenderRequest.setAttribute(
				WebKeys.THEME_DISPLAY,
				MockThemeDisplayUtil.getThemeDisplay(
					_companyLocalService.getCompany(
						TestPropsValues.getCompanyId()),
					_group, _layout,
					_layoutSetLocalService.getLayoutSet(
						_group.getGroupId(), false)));

			return mockLiferayPortletRenderRequest;
		}
		catch (PortalException portalException) {
			throw new AssertionError(portalException);
		}
	}

	private MockLiferayPortletRenderResponse
		_getMockLiferayPortletRenderResponse() {

		return new MockLiferayPortletRenderResponse() {

			@Override
			public String getNamespace() {
				return "com_liferay_analytics_reports_web_internal_portlet_" +
					"AnalyticsReportsPortlet";
			}

		};
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private Http _http;

	private Layout _layout;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

	@Inject
	private Portal _portal;

	@Inject(
		filter = "component.name=com.liferay.analytics.reports.web.internal.portlet.AnalyticsReportsPortlet"
	)
	private Portlet _portlet;

}