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

package com.liferay.content.dashboard.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.sort.Sorts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(service = {})
public class ContentDashboardAssetCategoriesAggregator {

	@Reference
	Portal _portal;

	public List<AssetCategoriesAggregation> aggregate(long companyId) {
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
			_searchRequestBuilderFactory.builder();

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
			searchContext -> searchContext.setAttribute("latest", Boolean.TRUE)
		).build();

		SearchResponse searchResponse = _searcher.search(searchRequest);

		return _toAssetCategoriesAggregations(searchResponse);
	}

	@Activate
	protected void activate() {
		List<AssetCategoriesAggregation> assetCategoriesAggregations =
			aggregate(_portal.getDefaultCompanyId());

		System.out.println(assetCategoriesAggregations);
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

	private List<AssetCategoriesAggregation> _toAssetCategoriesAggregations(
		SearchResponse searchResponse) {

		List<AssetCategoriesAggregation> assetCategoriesAggregations =
			new ArrayList<>();

		TermsAggregationResult termsAggregationResult =
			(TermsAggregationResult)searchResponse.getAggregationResult(
				"categories");

		Collection<Bucket> buckets = termsAggregationResult.getBuckets();

		buckets.forEach(
			bucket -> {
				AssetCategoriesAggregation assetCategoriesAggregation =
					new AssetCategoriesAggregation(
						bucket.getKey(), bucket.getDocCount());

				TermsAggregationResult childTermsAggregationResult =
					(TermsAggregationResult)bucket.getChildAggregationResult(
						"categories");

				List<AssetCategoriesAggregation>
					childAssetCategoriesAggregations = new ArrayList<>();

				Collection<Bucket> childBuckets =
					childTermsAggregationResult.getBuckets();

				childBuckets.forEach(
					childBucket -> childAssetCategoriesAggregations.add(
						new AssetCategoriesAggregation(
							childBucket.getKey(), childBucket.getDocCount())));

				assetCategoriesAggregation.setChildAggregations(
					childAssetCategoriesAggregations);

				assetCategoriesAggregations.add(assetCategoriesAggregation);
			});

		return assetCategoriesAggregations;
	}

	@Reference
	private Aggregations _aggregations;

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private Queries _queries;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private Sorts _sorts;

}