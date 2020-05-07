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

package com.liferay.analytics.reports.journal.internal.info.item;

import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItem;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.util.comparator.ArticleVersionComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(service = AnalyticsReportsInfoItem.class)
public class JournalArticleAnalyticsReportsInfoItem
	implements AnalyticsReportsInfoItem<JournalArticle> {

	@Override
	public String getAuthorName(JournalArticle journalArticle) {
		List<JournalArticle> journalArticles =
			_journalArticleLocalService.getArticles(
				journalArticle.getGroupId(), journalArticle.getArticleId(), 0,
				1, new ArticleVersionComparator(true));

		journalArticle = journalArticles.get(0);

		User user = _userLocalService.fetchUser(journalArticle.getUserId());

		if (user != null) {
			return user.getFullName();
		}

		return StringPool.BLANK;
	}

	@Override
	public Date getPublishDate(JournalArticle journalArticle) {
		return journalArticle.getDisplayDate();
	}

	@Override
	public String getTitle(JournalArticle journalArticle, Locale locale) {
		return journalArticle.getTitle(locale);
	}

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}