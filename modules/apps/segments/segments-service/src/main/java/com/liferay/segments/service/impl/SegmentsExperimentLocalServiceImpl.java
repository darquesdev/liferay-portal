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
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.exception.SegmentsExperimentNameException;
import com.liferay.segments.exception.SegmentsExperimentStatusException;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.base.SegmentsExperimentLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * The implementation of the segments experiment local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.segments.service.SegmentsExperimentLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Eduardo García
 * @see SegmentsExperimentLocalServiceBaseImpl
 */
public class SegmentsExperimentLocalServiceImpl
	extends SegmentsExperimentLocalServiceBaseImpl {

	@Override
	public SegmentsExperiment addSegmentsExperiment(
			long segmentsExperienceId, long classNameId, long classPK,
			String name, String description, ServiceContext serviceContext)
		throws PortalException {

		// Segments experiment

		User user = userLocalService.getUser(serviceContext.getUserId());

		long groupId = serviceContext.getScopeGroupId();

		long segmentsExperimentId = counterLocalService.increment();

		int status = SegmentsConstants.SEGMENTS_EXPERIMENT_STATUS_DRAFT;

		long publishedClassPK = _getPublishedLayoutClassPK(classPK);

		_validate(
			segmentsExperienceId, classNameId, publishedClassPK, name, status);

		SegmentsExperiment segmentsExperiment =
			segmentsExperimentPersistence.create(segmentsExperimentId);

		segmentsExperiment.setUuid(serviceContext.getUuid());
		segmentsExperiment.setGroupId(groupId);
		segmentsExperiment.setCompanyId(user.getCompanyId());
		segmentsExperiment.setUserId(user.getUserId());
		segmentsExperiment.setUserName(user.getFullName());
		segmentsExperiment.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		segmentsExperiment.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		segmentsExperiment.setSegmentsExperimentKey(
			String.valueOf(counterLocalService.increment()));
		segmentsExperiment.setSegmentsExperienceId(segmentsExperienceId);
		segmentsExperiment.setClassNameId(classNameId);
		segmentsExperiment.setClassPK(publishedClassPK);
		segmentsExperiment.setName(name);
		segmentsExperiment.setDescription(description);
		segmentsExperiment.setStatus(status);

		segmentsExperimentPersistence.update(segmentsExperiment);

		// Resources

		resourceLocalService.addModelResources(
			segmentsExperiment, serviceContext);

		return segmentsExperiment;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public SegmentsExperiment deleteSegmentsExperiment(
			SegmentsExperiment segmentsExperiment)
		throws PortalException {

		// Segments experiment

		segmentsExperimentPersistence.remove(segmentsExperiment);

		// Resources

		resourceLocalService.deleteResource(
			segmentsExperiment, ResourceConstants.SCOPE_INDIVIDUAL);

		return segmentsExperiment;
	}

	@Override
	public void deleteSegmentsExperiments(
			long segmentsExperienceId, long classNameId, long classPK)
		throws PortalException {

		List<SegmentsExperiment> segmentsExperiments =
			segmentsExperimentPersistence.findByS_C_C(
				segmentsExperienceId, classNameId, classPK);

		for (SegmentsExperiment segmentsExperiment : segmentsExperiments) {
			segmentsExperimentLocalService.deleteSegmentsExperiment(
				segmentsExperiment.getSegmentsExperimentId());
		}
	}

	@Override
	public List<SegmentsExperiment> getSegmentsExperiments(
			long groupId, long classNameId, long classPK)
		throws PortalException {

		return segmentsExperimentPersistence.findByG_C_C(
			groupId, classNameId, classPK);
	}

	@Override
	public List<SegmentsExperiment> getSegmentsExperimentsByExperience(
		long segmentsExperienceId, long classNameId, long classPK) {

		return segmentsExperimentPersistence.findByS_C_C(
			segmentsExperienceId, classNameId, classPK);
	}

	private long _getPublishedLayoutClassPK(long classPK) {
		Layout layout = layoutLocalService.fetchLayout(classPK);

		if ((layout != null) &&
			(layout.getClassNameId() == classNameLocalService.getClassNameId(
				Layout.class)) &&
			(layout.getClassPK() != 0)) {

			return layout.getClassPK();
		}

		return classPK;
	}

	private void _validate(
			long segmentsExperienceId, long classNameId, long classPK,
			String name, int status)
		throws PortalException {

		_validateName(name);
		_validateStatus(segmentsExperienceId, classNameId, classPK, status);
	}

	private void _validateName(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new SegmentsExperimentNameException();
		}
	}

	private void _validateStatus(
			long segmentsExperienceId, long classNameId, long classPK,
			int status)
		throws SegmentsExperimentStatusException {

		if (SegmentsConstants.SEGMENTS_EXPERIMENT_STATUS_DRAFT != status) {
			return;
		}

		if (ListUtil.isNotNull(
				segmentsExperimentPersistence.findByS_C_C_S(
					segmentsExperienceId, classNameId, classPK,
					SegmentsConstants.SEGMENTS_EXPERIMENT_STATUS_DRAFT))) {

			throw new SegmentsExperimentStatusException();
		}
	}

}