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

package com.liferay.content.dashboard.web.internal.data.provider;

import com.liferay.content.dashboard.web.internal.model.AssetCategoryMetric;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.IncludeExcludeClause;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author David Arques
 */
public class ContentDashboardDataProvider {

	public ContentDashboardDataProvider(
		Aggregations aggregations, SearchContext searchContext,
		Searcher searcher,
		SearchRequestBuilderFactory searchRequestBuilderFactory) {

		_aggregations = aggregations;
		_searchContext = searchContext;
		_searcher = searcher;
		_searchRequestBuilderFactory = searchRequestBuilderFactory;
	}

	public List<AssetCategoryMetric> getAssetCategoryMetrics(
		Map<String, String[]> assetCategoriesMap) {

		String[] audienceAssetCategoryIds = assetCategoriesMap.get("audience");
		String[] stageAssetCategoryIds = assetCategoriesMap.get("stage");

		if (ArrayUtil.isEmpty(audienceAssetCategoryIds) &&
			ArrayUtil.isEmpty(stageAssetCategoryIds)) {

			return Collections.emptyList();
		}

		TermsAggregation termsAggregation = null;

		if (!ArrayUtil.isEmpty(audienceAssetCategoryIds)) {
			termsAggregation = _getTermsAggregation(audienceAssetCategoryIds);

			if (!ArrayUtil.isEmpty(stageAssetCategoryIds)) {
				termsAggregation.addChildAggregation(
					_getTermsAggregation(stageAssetCategoryIds));
			}
		}
		else {
			termsAggregation = _getTermsAggregation(stageAssetCategoryIds);
		}

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder(_searchContext);

		SearchRequest searchRequest = searchRequestBuilder.addAggregation(
			termsAggregation
		).emptySearchEnabled(
			true
		).highlightEnabled(
			false
		).size(
			0
		).build();

		return _toAssetCategoryMetrics(_searcher.search(searchRequest));
	}

	private TermsAggregation _getTermsAggregation(String[] assetCategoryIds) {
		TermsAggregation termsAggregation = _aggregations.terms(
			"categories", "assetCategoryIds");

		termsAggregation.setIncludeExcludeClause(
			new IncludeExcludeClauseImpl(assetCategoryIds, new String[0]));

		return termsAggregation;
	}

	private List<AssetCategoryMetric> _toAssetCategoryMetrics(
		SearchResponse searchResponse) {

		List<AssetCategoryMetric> assetCategoryMetrics = new ArrayList<>();

		TermsAggregationResult termsAggregationResult =
			(TermsAggregationResult)searchResponse.getAggregationResult(
				"categories");

		for (Bucket bucket : termsAggregationResult.getBuckets()) {
			AssetCategoryMetric assetCategoryMetric = new AssetCategoryMetric(
				bucket.getKey(), bucket.getDocCount());

			TermsAggregationResult childTermsAggregationResult =
				(TermsAggregationResult)bucket.getChildAggregationResult(
					"categories");

			List<AssetCategoryMetric> childAssetCategoryMetrics =
				new ArrayList<>();

			for (Bucket childBucket :
					childTermsAggregationResult.getBuckets()) {

				childAssetCategoryMetrics.add(
					new AssetCategoryMetric(
						childBucket.getKey(), childBucket.getDocCount()));
			}

			assetCategoryMetric.setAssetCategoryMetrics(
				childAssetCategoryMetrics);

			assetCategoryMetrics.add(assetCategoryMetric);
		}

		return assetCategoryMetrics;
	}

	private final Aggregations _aggregations;
	private final SearchContext _searchContext;
	private final Searcher _searcher;
	private final SearchRequestBuilderFactory _searchRequestBuilderFactory;

	private static class IncludeExcludeClauseImpl
		implements IncludeExcludeClause {

		public IncludeExcludeClauseImpl(
			String includeRegex, String excludeRegex) {

			_includeRegex = includeRegex;
			_excludeRegex = excludeRegex;
		}

		public IncludeExcludeClauseImpl(
			String[] includedValues, String[] excludedValues) {

			_includedValues = includedValues;
			_excludedValues = excludedValues;
		}

		@Override
		public String[] getExcludedValues() {
			return _excludedValues;
		}

		@Override
		public String getExcludeRegex() {
			return _excludeRegex;
		}

		@Override
		public String[] getIncludedValues() {
			return _includedValues;
		}

		@Override
		public String getIncludeRegex() {
			return _includeRegex;
		}

		private String[] _excludedValues;
		private String _excludeRegex;
		private String[] _includedValues;
		private String _includeRegex;

	}

}