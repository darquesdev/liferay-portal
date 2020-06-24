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

package com.liferay.content.dashboard.web.internal.dao.search;

import com.liferay.content.dashboard.web.internal.item.ContentDashboardItem;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItemFactory;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItemFactoryTracker;
import com.liferay.content.dashboard.web.internal.search.request.ContentDashboardSearchContextBuilder;
import com.liferay.content.dashboard.web.internal.searcher.ContentDashboardSearchRequestBuilderFactory;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.HitsImpl;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SearchResultUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Cristina González
 */
public class ContentDashboardItemSearchContainerFactory {

	public static ContentDashboardItemSearchContainerFactory getInstance(
		ContentDashboardItemFactoryTracker contentDashboardItemFactoryTracker,
		ContentDashboardSearchRequestBuilderFactory
			contentDashboardSearchRequestBuilderFactory,
		Portal portal, RenderRequest renderRequest,
		RenderResponse renderResponse, Searcher searcher) {

		return new ContentDashboardItemSearchContainerFactory(
			contentDashboardItemFactoryTracker,
			contentDashboardSearchRequestBuilderFactory, portal, renderRequest,
			renderResponse, searcher);
	}

	public SearchContainer<ContentDashboardItem<?>> create()
		throws PortletException {

		SearchContainer<ContentDashboardItem<?>> searchContainer =
			new SearchContainer<>(
				_renderRequest,
				PortletURLUtil.clone(
					PortletURLUtil.getCurrent(_renderRequest, _renderResponse),
					_renderResponse),
				null, "there-is-no-content");

		searchContainer.setOrderByCol(_getOrderByCol());

		searchContainer.setOrderByType(_getOrderByType());

		Hits hits = _getHits(
			searchContainer.getEnd(), searchContainer.getStart());

		searchContainer.setResults(_getContentDashboardItems(hits));
		searchContainer.setTotal(hits.getLength());

		return searchContainer;
	}

	private ContentDashboardItemSearchContainerFactory(
		ContentDashboardItemFactoryTracker contentDashboardItemFactoryTracker,
		ContentDashboardSearchRequestBuilderFactory
			contentDashboardSearchRequestBuilderFactory,
		Portal portal, RenderRequest renderRequest,
		RenderResponse renderResponse, Searcher searcher) {

		_contentDashboardItemFactoryTracker =
			contentDashboardItemFactoryTracker;
		_contentDashboardSearchRequestBuilderFactory =
			contentDashboardSearchRequestBuilderFactory;
		_portal = portal;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_searcher = searcher;

		_locale = _portal.getLocale(_renderRequest);
	}

	private List<ContentDashboardItem<?>> _getContentDashboardItems(Hits hits) {
		List<SearchResult> searchResults = SearchResultUtil.getSearchResults(
			hits, _locale);

		Stream<SearchResult> stream = searchResults.stream();

		return stream.map(
			this::_toContentDashboardItemOptional
		).filter(
			Optional::isPresent
		).map(
			Optional::get
		).collect(
			Collectors.toList()
		);
	}

	private Hits _getHits(int end, int start) {
		return _toHits(
			_searcher.search(
				_contentDashboardSearchRequestBuilderFactory.builder(
					new ContentDashboardSearchContextBuilder(
						_portal.getHttpServletRequest(_renderRequest)
					).withEnd(
						end
					).withSort(
						_getSort(_getOrderByCol(), _getOrderByType())
					).withStart(
						start
					).build()
				).build()));
	}

	private String _getOrderByCol() {
		return ParamUtil.getString(
			_renderRequest, SearchContainer.DEFAULT_ORDER_BY_COL_PARAM,
			"modified-date");
	}

	private String _getOrderByType() {
		return ParamUtil.getString(
			_renderRequest, SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM,
			"desc");
	}

	private Sort _getSort(String orderByCol, String orderByType) {
		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		if (orderByCol.equals("title")) {
			String sortFieldName = Field.getSortableFieldName(
				"localized_title_".concat(LocaleUtil.toLanguageId(_locale)));

			return new Sort(sortFieldName, Sort.STRING_TYPE, !orderByAsc);
		}

		return new Sort(Field.MODIFIED_DATE, Sort.LONG_TYPE, !orderByAsc);
	}

	private Optional<ContentDashboardItem<?>> _toContentDashboardItemOptional(
		ContentDashboardItemFactory<?> contentDashboardItemFactory,
		SearchResult searchResult) {

		try {
			return Optional.of(
				contentDashboardItemFactory.create(searchResult.getClassPK()));
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return Optional.empty();
		}
	}

	private Optional<ContentDashboardItem<?>> _toContentDashboardItemOptional(
		SearchResult searchResult) {

		Optional<ContentDashboardItemFactory<?>>
			contentDashboardItemFactoryOptional =
				_contentDashboardItemFactoryTracker.
					getContentDashboardItemFactoryOptional(
						searchResult.getClassName());

		return contentDashboardItemFactoryOptional.flatMap(
			contentDashboardItemFactory -> _toContentDashboardItemOptional(
				contentDashboardItemFactory, searchResult));
	}

	private Hits _toHits(SearchResponse searchResponse) {
		Hits hits = new HitsImpl();

		List<Document> documents = searchResponse.getDocuments71();

		hits.setDocs(documents.toArray(new Document[0]));

		hits.setLength(searchResponse.getTotalHits());

		return hits;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentDashboardItemSearchContainerFactory.class);

	private final ContentDashboardItemFactoryTracker
		_contentDashboardItemFactoryTracker;
	private final ContentDashboardSearchRequestBuilderFactory
		_contentDashboardSearchRequestBuilderFactory;
	private final Locale _locale;
	private final Portal _portal;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final Searcher _searcher;

}