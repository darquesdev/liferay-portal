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

package com.liferay.segments.internal.util;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsEntryRel;
import com.liferay.segments.odata.retriever.ODataRetriever;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsEntryRelLocalService;
import com.liferay.segments.util.SegmentsEntryHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(immediate = true, service = SegmentsEntryHelper.class)
public class SegmentsEntryHelperImpl implements SegmentsEntryHelper {

	@Activate
	public void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ODataRetriever.class, "model.class.name");
	}

	@Deactivate
	public void deactivate() {
		_serviceTrackerMap.close();
	}

	@Override
	public List<SegmentsEntry> getSegmentsEntries(
			long groupId, String className, long classPK)
		throws PortalException {

		List<SegmentsEntry> segmentsEntries =
			_segmentsEntryLocalService.getSegmentsEntries(
				groupId, true, className, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null);

		if (segmentsEntries.isEmpty()) {
			return Collections.emptyList();
		}

		ODataRetriever oDataRetriever = _serviceTrackerMap.getService(
			className);

		List<SegmentsEntry> allSegmentsEntries = new ArrayList();

		for (SegmentsEntry segmentsEntry : segmentsEntries) {
			if (Validator.isNotNull(segmentsEntry.getCriteria()) &&
				(oDataRetriever != null)) {

				String filterString = String.format(
					"(%s) and (classPK eq '%s')", segmentsEntry.getCriteria(),
					classPK);

				if (oDataRetriever.getResultsCount(
						segmentsEntry.getCompanyId(), filterString,
						Locale.getDefault()) == 0) {

					continue;
				}
			}
			else if (!_segmentsEntryRelLocalService.hasSegmentsEntryRel(
						segmentsEntry.getSegmentsEntryId(),
						_portal.getClassNameId(className), classPK)) {

				continue;
			}

			allSegmentsEntries.add(segmentsEntry);
		}

		return allSegmentsEntries;
	}

	@Override
	public long[] getSegmentsEntryClassPKs(long segmentsEntryId)
		throws PortalException {

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.fetchSegmentsEntry(segmentsEntryId);

		if (segmentsEntry == null) {
			return new long[0];
		}

		if (Validator.isNotNull(segmentsEntry.getCriteria())) {
			ODataRetriever oDataRetriever = _serviceTrackerMap.getService(
				segmentsEntry.getType());

			List<BaseModel<?>> results = oDataRetriever.getResults(
				segmentsEntry.getCompanyId(), segmentsEntry.getCriteria(),
				Locale.getDefault(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			Stream<BaseModel<?>> stream = results.stream();

			return stream.mapToLong(
				baseModel -> (Long)baseModel.getPrimaryKeyObj()
			).toArray();
		}
		else {
			List<SegmentsEntryRel> segmentsEntryRels =
				_segmentsEntryRelLocalService.getSegmentsEntryRels(
					segmentsEntryId);

			Stream<SegmentsEntryRel> stream = segmentsEntryRels.stream();

			return stream.mapToLong(
				SegmentsEntryRel::getClassPK
			).toArray();
		}
	}

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Reference
	private SegmentsEntryRelLocalService _segmentsEntryRelLocalService;

	private ServiceTrackerMap<String, ODataRetriever> _serviceTrackerMap;

}