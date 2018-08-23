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

package com.liferay.structured.content.apio.internal.router;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.structured.content.apio.architect.filter.Filter;
import com.liferay.structured.content.apio.architect.form.StructuredContentCreatorForm;
import com.liferay.structured.content.apio.architect.form.StructuredContentUpdaterForm;
import com.liferay.structured.content.apio.architect.model.JournalArticleWrapper;
import com.liferay.structured.content.apio.architect.router.StructuredContentRouter;
import com.liferay.structured.content.apio.internal.search.QueryMapper;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.liferay.structured.content.apio.internal.search.JournalArticleSearchHelper;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Implementation for API for the Structured Content Controller.
 *
 * @author Cristina González
 */
@Component(immediate = true)
public class StructuredContentRouterImpl implements StructuredContentRouter {

	@Override
	public JournalArticleWrapper addJournalArticle(
			long contentSpaceId,
			StructuredContentCreatorForm structuredContentCreatorForm,
			ThemeDisplay themeDisplay)
		throws PortalException {

		Locale locale = themeDisplay.getLocale();

		ServiceContext serviceContext =
			structuredContentCreatorForm.getServiceContext(contentSpaceId);

		JournalArticle journalArticle = _journalArticleService.addArticle(
			contentSpaceId, 0, 0, 0, null, true,
			structuredContentCreatorForm.getTitleMap(locale),
			structuredContentCreatorForm.getDescriptionMap(locale),
			structuredContentCreatorForm.getText(),
			structuredContentCreatorForm.getStructure(),
			structuredContentCreatorForm.getTemplate(), null,
			structuredContentCreatorForm.getDisplayDateMonth(),
			structuredContentCreatorForm.getDisplayDateDay(),
			structuredContentCreatorForm.getDisplayDateYear(),
			structuredContentCreatorForm.getDisplayDateHour(),
			structuredContentCreatorForm.getDisplayDateMinute(), 0, 0, 0, 0, 0,
			true, 0, 0, 0, 0, 0, true, true, null, serviceContext);

		return _toJournalArticleWrapper(journalArticle, themeDisplay);
	}

	@Override
	public void deleteJournalArticle(long journalArticleId)
		throws PortalException {

		JournalArticle journalArticle = _journalArticleService.getArticle(
			journalArticleId);

		_journalArticleService.deleteArticle(
			journalArticle.getGroupId(), journalArticle.getArticleId(),
			journalArticle.getArticleResourceUuid(), new ServiceContext());
	}

	public PageItems<JournalArticle> getJournalArticles(
		long companyId, Pagination pagination,
		long contentSpaceId, String filter, Locale locale)
		throws PortalException {

		Query query = _queryMapper.map(filter, locale);

		Hits hits = _journalArticleSearchHelper.getHits(
			companyId, contentSpaceId, query,
			pagination.getStartPosition(), pagination.getEndPosition()
		);

		return new PageItems<>(hitsToJournalArticles(hits), hits.getLength());
	}

	@Override
	public PageItems<JournalArticleWrapper> getPageItems(
			Pagination pagination, long contentSpaceId, Filter filter,
			ThemeDisplay themeDisplay)
		throws PortalException {

		Optional<String> filterOptional = filter.getValue();

		Query query = _queryMapper.map(
			filterOptional.get(), themeDisplay.getLocale());

		Hits hits = _journalArticleSearchHelper.getHits(
			themeDisplay.getCompanyId(), contentSpaceId, query,
			pagination.getStartPosition(), pagination.getEndPosition()
		);

		List<JournalArticle> journalArticles = hitsToJournalArticles(hits);

		Stream<JournalArticle> journalArticleStream = journalArticles.stream();

		List<JournalArticleWrapper> journalArticleWrappers =
			journalArticleStream.map(
				journalArticle -> _toJournalArticleWrapper(
					journalArticle, themeDisplay
				)
			).filter(
				journalArticleWrapper -> journalArticleWrapper != null
			).collect(
				Collectors.toList()
			);

		return new PageItems<>(journalArticleWrappers, hits.getLength());
	}

	private List<JournalArticle> hitsToJournalArticles(Hits hits) {
		return Stream.of(
			hits.toList()
		).flatMap(
			List::stream
		).map(
			document -> _fetchArticle(document)
		).collect(
			Collectors.toList()
		);
	}

	@Override
	public JournalArticleWrapper updateJournalArticle(
			long journalArticleId,
			StructuredContentUpdaterForm structuredContentUpdaterForm,
			ThemeDisplay themeDisplay)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(structuredContentUpdaterForm.getGroup());

		JournalArticle journalArticle = _journalArticleService.updateArticle(
			structuredContentUpdaterForm.getUser(),
			structuredContentUpdaterForm.getGroup(), 0,
			String.valueOf(journalArticleId),
			structuredContentUpdaterForm.getVersion(),
			structuredContentUpdaterForm.getTitleMap(),
			structuredContentUpdaterForm.getDescriptionMap(),
			structuredContentUpdaterForm.getText(), null, serviceContext);

		return _toJournalArticleWrapper(journalArticle, themeDisplay);
	}

	private JournalArticle _fetchArticle(String articleId, long groupId) {

		try {
			JournalArticle journalArticle = _journalArticleService.fetchArticle(
				groupId, articleId);

			return journalArticle;
		}
		catch (PortalException pe) {
			_log.error("Unable to obtain Journal Article", pe);
		}

		return null;
	}

	private JournalArticle _fetchArticle(Document document) {

		String articleId = document.get(Field.ARTICLE_ID);
		long groupId = GetterUtil.getLong(document.get(Field.SCOPE_GROUP_ID));

		return _fetchArticle(articleId, groupId);

	}

	protected JournalArticleWrapper _fetchArticle(
		JournalArticle journalArticle, ThemeDisplay themeDisplay) {

		return _toJournalArticleWrapper(journalArticle, themeDisplay);
	}

	private JournalArticleWrapper _toJournalArticleWrapper(
		JournalArticle journalArticle, ThemeDisplay themeDisplay) {
		return new JournalArticleWrapper(journalArticle, themeDisplay);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StructuredContentRouterImpl.class);

	@Reference
	private JournalArticleSearchHelper _journalArticleSearchHelper;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private QueryMapper _queryMapper;

}