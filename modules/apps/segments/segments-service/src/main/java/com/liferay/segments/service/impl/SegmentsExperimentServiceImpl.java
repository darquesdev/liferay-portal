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

package com.liferay.segments.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.segments.constants.SegmentsActionKeys;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperienceModel;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.base.SegmentsExperimentServiceBaseImpl;

import java.util.List;
import java.util.stream.Stream;

/**
 * The implementation of the segments experiment remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.segments.service.SegmentsExperimentService</code> interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Eduardo García
 * @see SegmentsExperimentServiceBaseImpl
 */
public class SegmentsExperimentServiceImpl
	extends SegmentsExperimentServiceBaseImpl {

	@Override
	public SegmentsExperiment addSegmentsExperiment(
			long segmentsExperienceId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			SegmentsActionKeys.MANAGE_SEGMENTS_ENTRIES);

		return segmentsExperimentLocalService.addSegmentsExperiment(
			segmentsExperienceId, name, description, serviceContext);
	}

	@Override
	public SegmentsExperiment getSegmentsExperiment(long segmentsExperimentId)
		throws PortalException {

		SegmentsExperiment segmentsExperiment =
			segmentsExperimentLocalService.getSegmentsExperiment(
				segmentsExperimentId);

		_segmentsExperimentResourcePermission.check(
			getPermissionChecker(), segmentsExperiment, ActionKeys.VIEW);

		return segmentsExperiment;
	}

	@Override
	public List<SegmentsExperiment> getSegmentsExperiments(
			long groupId, long classNameId, long classPK)
		throws PortalException {

		long[] segmentsExperienceIds = ArrayUtil.append(
			_getSegmentsExperienceIds(groupId, classNameId, classPK),
			SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT);

		return segmentsExperimentPersistence.filterFindByG_S(
			groupId, segmentsExperienceIds);
	}

	private long[] _getSegmentsExperienceIds(
			long groupId, long classNameId, long classPK)
		throws PortalException {

		List<SegmentsExperience> segmentsExperiences =
			segmentsExperienceService.getSegmentsExperiences(
				groupId, classNameId, classPK, true);

		Stream<SegmentsExperience> stream = segmentsExperiences.stream();

		return stream.mapToLong(
			SegmentsExperienceModel::getSegmentsExperienceId
		).toArray();
	}

	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				SegmentsExperimentServiceImpl.class,
				"_portletResourcePermission", SegmentsConstants.RESOURCE_NAME);
	private static volatile ModelResourcePermission<SegmentsExperiment>
		_segmentsExperimentResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				SegmentsExperimentServiceImpl.class,
				"_segmentsExperimentResourcePermission",
				SegmentsExperiment.class);

}