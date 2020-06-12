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

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.content.dashboard.web.internal.model.AssetCategoryMetric;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author David Arques
 */
public class ContentDashboardDataProvider {

	public ContentDashboardDataProvider(
		Aggregations aggregations,
		AssetCategoryLocalService assetCategoryLocalService,
		AssetVocabularyLocalService assetVocabularyLocalService,
		SearchContext searchContext, Searcher searcher,
		SearchRequestBuilderFactory searchRequestBuilderFactory) {

		_aggregations = aggregations;
		_assetCategoryLocalService = assetCategoryLocalService;
		_assetVocabularyLocalService = assetVocabularyLocalService;
		_searchContext = searchContext;
		_searcher = searcher;
		_searchRequestBuilderFactory = searchRequestBuilderFactory;
	}

	public List<AssetCategoryMetric> getAssetCategoryMetrics(long companyId) {
		List<AssetVocabulary> assetVocabularies = new ArrayList<>(
			_assetVocabularyLocalService.getCompanyVocabularies(companyId));

		Collections.reverse(assetVocabularies);

		if (ListUtil.isEmpty(assetVocabularies)) {
			return Collections.emptyList();
		}

		Optional<TermsAggregation> termsAggregationOptional =
			_getTermsAggregationOptional(assetVocabularies.get(0));

		if (!termsAggregationOptional.isPresent()) {
			return Collections.emptyList();
		}

		TermsAggregation termsAggregation = termsAggregationOptional.get();

		if (assetVocabularies.size() > 1) {
			_getTermsAggregationOptional(
				assetVocabularies.get(1)
			).ifPresent(
				termsAggregation::addChildAggregation
			);
		}

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder(_searchContext);

		SearchRequest searchRequest = searchRequestBuilder.addAggregation(
			termsAggregation
		).companyId(
			companyId
		).emptySearchEnabled(
			true
		).entryClassNames(
			JournalArticle.class.getName()
		).highlightEnabled(
			false
		).size(
			0
		).withSearchContext(
			searchContext -> searchContext.setAttribute(
				"latest", _searchContext.getAttribute("latest"))
		).build();

		SearchResponse searchResponse = _searcher.search(searchRequest);

		return _toAssetCategoryMetrics(searchResponse);
	}

	private Optional<TermsAggregation> _getTermsAggregationOptional(
		AssetVocabulary assetVocabulary) {

		List<AssetCategory> assetCategories =
			_assetCategoryLocalService.getVocabularyCategories(
				assetVocabulary.getVocabularyId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		if (assetCategories.isEmpty()) {
			return Optional.empty();
		}

		Stream<AssetCategory> assetCategoryStream = assetCategories.stream();

		List<String> assetCategoryIds = assetCategoryStream.map(
			assetCategory -> String.valueOf(assetCategory.getCategoryId())
		).collect(
			Collectors.toList()
		);

		TermsAggregation termsAggregation = _aggregations.terms(
			"categories", "assetCategoryIds");

		termsAggregation.setIncludeExcludeClause(
			new IncludeExcludeClauseImpl(
				ArrayUtil.toStringArray(assetCategoryIds), new String[0]));

		return Optional.of(termsAggregation);
	}

	private List<AssetCategoryMetric> _toAssetCategoryMetrics(
		SearchResponse searchResponse) {

		List<AssetCategoryMetric> assetCategoryMetrics = new ArrayList<>();

		TermsAggregationResult termsAggregationResult =
			(TermsAggregationResult)searchResponse.getAggregationResult(
				"categories");

		Collection<Bucket> buckets = termsAggregationResult.getBuckets();

		buckets.forEach(
			bucket -> {
				AssetCategoryMetric assetCategoryMetric =
					new AssetCategoryMetric(
						bucket.getKey(), bucket.getDocCount());

				TermsAggregationResult childTermsAggregationResult =
					(TermsAggregationResult)bucket.getChildAggregationResult(
						"categories");

				List<AssetCategoryMetric> childAssetCategoryMetrics =
					new ArrayList<>();

				Collection<Bucket> childBuckets =
					childTermsAggregationResult.getBuckets();

				childBuckets.forEach(
					childBucket -> childAssetCategoryMetrics.add(
						new AssetCategoryMetric(
							childBucket.getKey(), childBucket.getDocCount())));

				assetCategoryMetric.setAssetCategoryMetrics(
					childAssetCategoryMetrics);

				assetCategoryMetrics.add(assetCategoryMetric);
			});

		return assetCategoryMetrics;
	}

	private final Aggregations _aggregations;
	private final AssetCategoryLocalService _assetCategoryLocalService;
	private final AssetVocabularyLocalService _assetVocabularyLocalService;
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