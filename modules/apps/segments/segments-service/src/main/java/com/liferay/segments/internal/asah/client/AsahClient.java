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

package com.liferay.segments.internal.asah.client;

import com.liferay.segments.internal.asah.client.model.IndividualSegment;
import com.liferay.segments.internal.asah.client.model.Results;
import com.liferay.segments.internal.asah.client.util.OrderByField;

import java.util.List;

/**
 * @author Shinn Lok
 * @author David Arques
 */
public interface AsahClient {

	/**
	 * Returns active individual segments with members
	 *
	 * @param cur
	 * @param delta
	 * @param orderByFields
	 * @return
	 */
	public Results<IndividualSegment> getIndividualSegments(
		int cur, int delta, List<OrderByField> orderByFields);

	/**
	 * Returns individuals belonging to the current company that are associated
	 * to the given segment
	 *
	 * @param individualSegmentId
	 * @param cur
	 * @param delta
	 * @param orderByFields
	 * @return
	 */
	public Results<Individual> getIndividualSegmentIndividuals(
		String individualSegmentId, int cur, int delta,
		List<OrderByField> orderByFields);

	// Filters individuals by dataSourcePK, if available

}