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

package com.liferay.segments.internal.provider;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsEntryRel;
import com.liferay.segments.odata.retriever.ODataRetriever;
import com.liferay.segments.provider.SegmentsEntryProvider;

import java.util.List;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = "segments.entry.provider.source=" + SegmentsEntryConstants.SOURCE_DEFAULT,
	service = SegmentsEntryProvider.class
)
public class DefaultSegmentsEntryProvider
	extends BaseSegmentsEntryProvider implements SegmentsEntryProvider {

	@Override
	public long[] getSegmentsEntryClassPKs(
			long segmentsEntryId, int start, int end)
		throws PortalException {

		SegmentsEntry segmentsEntry =
			segmentsEntryLocalService.fetchSegmentsEntry(segmentsEntryId);

		if (segmentsEntry == null) {
			return new long[0];
		}

		String filterString = getFilterString(
			segmentsEntry, Criteria.Type.MODEL);

		if (Validator.isNull(filterString)) {
			List<SegmentsEntryRel> segmentsEntryRels =
				segmentsEntryRelLocalService.getSegmentsEntryRels(
					segmentsEntryId, start, end, null);

			Stream<SegmentsEntryRel> stream = segmentsEntryRels.stream();

			return stream.mapToLong(
				SegmentsEntryRel::getClassPK
			).toArray();
		}

		ODataRetriever oDataRetriever = serviceTrackerMap.getService(
			segmentsEntry.getType());

		if (oDataRetriever == null) {
			return new long[0];
		}

		List<BaseModel<?>> results = oDataRetriever.getResults(
			segmentsEntry.getCompanyId(), filterString, LocaleUtil.getDefault(),
			start, end);

		Stream<BaseModel<?>> stream = results.stream();

		return stream.mapToLong(
			baseModel -> (Long)baseModel.getPrimaryKeyObj()
		).toArray();
	}

	@Override
	public int getSegmentsEntryClassPKsCount(long segmentsEntryId)
		throws PortalException {

		SegmentsEntry segmentsEntry =
			segmentsEntryLocalService.fetchSegmentsEntry(segmentsEntryId);

		if (segmentsEntry == null) {
			return 0;
		}

		String filterString = getFilterString(
			segmentsEntry, Criteria.Type.MODEL);

		if (Validator.isNull(filterString)) {
			return segmentsEntryRelLocalService.getSegmentsEntryRelsCount(
				segmentsEntryId);
		}

		ODataRetriever oDataRetriever = serviceTrackerMap.getService(
			segmentsEntry.getType());

		if (oDataRetriever == null) {
			return 0;
		}

		return oDataRetriever.getResultsCount(
			segmentsEntry.getCompanyId(), filterString,
			LocaleUtil.getDefault());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ODataRetriever.class, "model.class.name");
	}

	@Deactivate
	protected void deactivate() {
		serviceTrackerMap.close();
	}

	@Override
	protected String getSource() {
		return SegmentsEntryConstants.SOURCE_DEFAULT;
	}

}