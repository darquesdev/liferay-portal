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

package com.liferay.segments.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.segments.model.SegmentsEntry;

import java.util.List;

/**
 * Provides methods for obtaining {@link SegmentsEntry} objects and their
 * related entities.
 *
 * @author Eduardo Garcia
 * @review
 */
public interface SegmentsEntryHelper {

	/**
	 * Returns the list of active {@link SegmentsEntry} objects related with the
	 * entity.
	 *
	 * @param  groupId the primary key of the segment's group
	 * @param  className the class name of the entity
	 * @param  classPK the primary key of the entity
	 * @return the list of active {@link SegmentsEntry} objects related to the
	 *         entity
	 * @throws PortalException
	 * @review
	 */
	public List<SegmentsEntry> getSegmentsEntries(
			long groupId, String className, long classPK)
		throws PortalException;

	/**
	 * Returns the primaryKeys of entities related with the segment.
	 *
	 * @param  segmentsEntryId the id of the segment
	 * @return the primaryKeys of entities related with the segment
	 * @throws PortalException
	 * @review
	 */
	public long[] getSegmentsEntryClassPKs(long segmentsEntryId)
		throws PortalException;

}