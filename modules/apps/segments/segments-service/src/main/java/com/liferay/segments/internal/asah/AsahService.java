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

package com.liferay.segments.internal.asah;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.segments.internal.asah.client.AsahClient;
import com.liferay.segments.internal.asah.client.model.IndividualSegment;
import com.liferay.segments.internal.asah.client.model.Results;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(immediate = true, service = AsahService.class)
public class AsahService {

	public List<IndividualSegment> getIndividualSegments() {
		return getIndividualSegments(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<IndividualSegment> getIndividualSegments(int start, int end) {
		if (start == QueryUtil.ALL_POS) {
			start = 0;
		}

		if (end == QueryUtil.ALL_POS) {
			end = _MAX_TOTAL_ELEMENTS;
		}

		if (start < 0) {
			start = 0;
		}

		if (end < start) {
			end = start;
		}

		if (start == end) {
			return Collections.emptyList();
		}

		Results<IndividualSegment> results = _asahClient.getIndividualSegments(
			null, null, Collections.emptyList(), null, null, "ACTIVE",
			start + 1, end - start, Collections.emptyList());

		return results.getItems();
	}

	private static final int _MAX_TOTAL_ELEMENTS = 1000;

	@Reference
	private AsahClient _asahClient;

}