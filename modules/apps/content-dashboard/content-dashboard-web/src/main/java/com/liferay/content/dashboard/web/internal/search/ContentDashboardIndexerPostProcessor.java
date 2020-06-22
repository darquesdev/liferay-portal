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

package com.liferay.content.dashboard.web.internal.search;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gregory Amerson
 */
@Component(
	property = "indexer.class.name=com.liferay.journal.model.JournalArticle",
	service = IndexerPostProcessor.class
)
public class ContentDashboardIndexerPostProcessor
	implements IndexerPostProcessor {

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter booleanFilter, SearchContext searchContext)
		throws Exception {

		if (GetterUtil.getBoolean(
				searchContext.getAttribute("contextDashboard"))) {

			List<BooleanClause<Filter>> booleanClauses =
				booleanFilter.getMustBooleanClauses();

			BooleanClause<Filter> booleanClause = _getBooleanClause(
				booleanClauses);

			if (booleanClause != null) {
				booleanClauses.remove(booleanClause);
			}
		}
	}

	@Override
	public void postProcessDocument(Document document, Object object)
		throws Exception {
	}

	@Override
	public void postProcessFullQuery(
			BooleanQuery fullQuery, SearchContext searchContext)
		throws Exception {
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter booleanFilter,
			SearchContext searchContext)
		throws Exception {
	}

	@Override
	public void postProcessSummary(
		Summary summary, Document document, Locale locale, String snippet) {
	}

	private BooleanClause<Filter> _getBooleanClause(
		List<BooleanClause<Filter>> booleanClauses) {

		for (BooleanClause<Filter> booleanClause : booleanClauses) {
			if (_isHeadFilter(booleanClause.getClause())) {
				return booleanClause;
			}
		}

		return null;
	}

	private boolean _isHeadFilter(Filter filter) {
		if (filter instanceof TermFilter) {
			return _isHeadFilter((TermFilter)filter);
		}

		return false;
	}

	private boolean _isHeadFilter(TermFilter termFilter) {
		if (Objects.equals("head", termFilter.getField())) {
			return true;
		}

		return false;
	}

}