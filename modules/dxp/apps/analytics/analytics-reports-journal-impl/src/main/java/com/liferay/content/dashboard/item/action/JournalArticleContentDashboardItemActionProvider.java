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

package com.liferay.content.dashboard.item.action;

import com.liferay.analytics.reports.info.action.AnalyticsReportsContentDashboardItemActionProvider;
import com.liferay.journal.model.JournalArticle;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(
	property = "model.class.name=com.liferay.journal.model.JournalArticle",
	service = ContentDashboardItemActionProvider.class
)
public class JournalArticleContentDashboardItemActionProvider
	implements ContentDashboardItemActionProvider<JournalArticle> {

	@Override
	public ContentDashboardItemAction getContentDashboardItemAction(
			JournalArticle journalArticle,
			HttpServletRequest httpServletRequest)
		throws ContentDashboardItemActionException {

		return _analyticsReportsContentDashboardItemActionProvider.
			getContentDashboardItemAction(
				JournalArticle.class.getName(),
				journalArticle.getResourcePrimKey(), httpServletRequest);
	}

	@Override
	public String getKey() {
		Class<?> clazz = getClass();

		return clazz.getName();
	}

	@Reference
	private AnalyticsReportsContentDashboardItemActionProvider
		_analyticsReportsContentDashboardItemActionProvider;

}