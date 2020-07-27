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

package com.liferay.analytics.reports.web.internal.item.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.constants.AssetDisplayPageWebKeys;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.item.action.ContentDashboardItemActionProvider;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class AnalyticsReportsContentDashboardItemActionProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		DDMStructure ddmStructure = _journalArticle.getDDMStructure();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				_group.getCreatorUserId(), _group.getGroupId(), 0,
				_portal.getClassNameId(JournalArticle.class.getName()),
				ddmStructure.getStructureId(), RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE, 0, true,
				0, 0, 0, 0, serviceContext);

		_assetDisplayPageEntryLocalService.addAssetDisplayPageEntry(
			_journalArticle.getUserId(), _group.getGroupId(),
			_portal.getClassNameId(JournalArticle.class.getName()),
			_journalArticle.getResourcePrimKey(),
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			AssetDisplayPageConstants.TYPE_DEFAULT, serviceContext);

		_layout = _layoutLocalService.getLayout(
			layoutPageTemplateEntry.getPlid());
	}

	@Test
	public void testGetContentDashboardItemAction() throws Exception {
		Optional<ContentDashboardItemAction>
			contentDashboardItemActionOptional =
				_contentDashboardItemActionProvider.
					getContentDashboardItemAction(
						JournalArticle.class.getName(),
						_journalArticle.getResourcePrimKey(),
						_getHttpServletRequest(TestPropsValues.getUser()),
						LocaleUtil.US);

		Assert.assertTrue(contentDashboardItemActionOptional.isPresent());
		ContentDashboardItemAction contentDashboardItemAction =
			contentDashboardItemActionOptional.get();

		Assert.assertEquals(
			"View Metrics", contentDashboardItemAction.getLabel());
		Assert.assertEquals(
			"viewMetrics", contentDashboardItemAction.getName());
	}

	@Test
	public void testGetContentDashboardItemActionWithUserWithoutEditPermission()
		throws Exception {

		User user = UserTestUtil.addUser();

		try {
			Assert.assertEquals(
				Optional.empty(),
				_contentDashboardItemActionProvider.
					getContentDashboardItemAction(
						JournalArticle.class.getName(),
						_journalArticle.getResourcePrimKey(),
						_getHttpServletRequest(user), LocaleUtil.US));
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetKey() {
		Assert.assertEquals(
			"com.liferay.analytics.reports.web.internal.item.action." +
				"AnalyticsReportsContentDashboardItemActionProvider",
			_contentDashboardItemActionProvider.getKey());
	}

	private HttpServletRequest _getHttpServletRequest(User user)
		throws PortalException {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			AssetDisplayPageWebKeys.INFO_DISPLAY_OBJECT_PROVIDER,
			_infoDisplayContributor.getInfoDisplayObjectProvider(
				_journalArticle.getResourcePrimKey()));

		mockHttpServletRequest.setAttribute(
			WebKeys.LAYOUT_ASSET_ENTRY,
			_assetEntryLocalService.getEntry(
				JournalArticle.class.getName(),
				_journalArticle.getResourcePrimKey()));

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY,
			_getThemeDisplay(mockHttpServletRequest, user));

		return mockHttpServletRequest;
	}

	private ThemeDisplay _getThemeDisplay(
			HttpServletRequest httpServletRequest, User user)
		throws PortalException {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLayout(_layout);
		themeDisplay.setLocale(LocaleUtil.US);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		themeDisplay.setPermissionChecker(permissionChecker);

		themeDisplay.setRequest(httpServletRequest);
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(user);

		return themeDisplay;
	}


	@Inject
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(
		filter = "component.name=com.liferay.analytics.reports.web.internal.item.action.AnalyticsReportsContentDashboardItemActionProvider"
	)
	private ContentDashboardItemActionProvider
		_contentDashboardItemActionProvider;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(filter = "component.name=*.JournalArticleInfoDisplayContributor")
	private InfoDisplayContributor<JournalArticle> _infoDisplayContributor;

	private JournalArticle _journalArticle;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private UserLocalService _userLocalService;

}