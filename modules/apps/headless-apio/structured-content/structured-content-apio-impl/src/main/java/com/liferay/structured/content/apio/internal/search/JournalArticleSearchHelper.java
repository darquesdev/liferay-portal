/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.structured.content.apio.internal.search;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ruben Pulido
 */
@Component(immediate = true)
public class JournalArticleSearchHelper {

	public Hits getHits(
		long companyId, long contentSpaceId, Query query,
		int startPosition, int endPosition)
		throws com.liferay.portal.kernel.search.SearchException {

		Indexer<JournalArticle> indexer = _indexerRegistry.nullSafeGetIndexer(
			JournalArticle.class);

		return indexer.search(
			_buildSearchContext(
				companyId, contentSpaceId, query, startPosition, endPosition));
	}

	private SearchContext _buildSearchContext(
		long companyId, long groupId, Query query, int start, int end) {

		SearchContext searchContext = new SearchContext();

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put(
			Field.CLASS_NAME_ID, JournalArticleConstants.CLASSNAME_ID_DEFAULT);
		attributes.put(Field.STATUS, WorkflowConstants.STATUS_APPROVED);

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {groupId});

		if (query != null) {
			BooleanClause booleanClause = BooleanClauseFactoryUtil.create(
				query, BooleanClauseOccur.MUST.getName());

			searchContext.setBooleanClauses(
				new BooleanClause[] {booleanClause});
		}

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);
		queryConfig.setSelectedFieldNames(
			Field.ARTICLE_ID, Field.SCOPE_GROUP_ID);

		searchContext.setStart(start);

		return searchContext;
	}

	@Reference
	private IndexerRegistry _indexerRegistry;

}
