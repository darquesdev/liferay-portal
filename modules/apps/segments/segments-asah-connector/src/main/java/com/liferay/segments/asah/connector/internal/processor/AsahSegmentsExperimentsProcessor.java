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

package com.liferay.segments.asah.connector.internal.processor;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClient;
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClientFactory;
import com.liferay.segments.asah.connector.internal.client.model.Experiment;
import com.liferay.segments.asah.connector.internal.client.model.ExperimentStatus;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sarai Díaz
 * @author David Arques
 */
@Component(immediate = true, service = AsahSegmentsExperimentsProcessor.class)
public class AsahSegmentsExperimentsProcessor {

	public void addExperiment(SegmentsExperiment segmentsExperiment)
		throws PortalException {

		if (segmentsExperiment == null) {
			return;
		}

		Optional<AsahFaroBackendClient> asahFaroBackendClientOptional =
			_asahFaroBackendClientFactory.createAsahFaroBackendClient();

		if (!asahFaroBackendClientOptional.isPresent()) {
			return;
		}

		_asahFaroBackendClient = asahFaroBackendClientOptional.get();

		Experiment experiment = _asahFaroBackendClient.addExperiment(
			_toDTO(segmentsExperiment));

		segmentsExperiment.setSegmentsExperimentKey(experiment.getId());
	}

	private ExperimentStatus _toDTO(int status) {
		//TODO
		if (status == SegmentsConstants.SEGMENTS_EXPERIMENT_STATUS_DRAFT) {
			return ExperimentStatus.DRAFT;
		}

		return ExperimentStatus.DRAFT;
	}

	private Experiment _toDTO(SegmentsExperiment segmentsExperiment)
		throws PortalException {

		Experiment experiment = new Experiment();

		experiment.setCreateDate(segmentsExperiment.getCreateDate());
		experiment.setModifiedDate(segmentsExperiment.getModifiedDate());
		experiment.setName(segmentsExperiment.getName());
		experiment.setDescription(segmentsExperiment.getDescription());

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.getSegmentsExperience(
				segmentsExperiment.getSegmentsExperienceId());

		experiment.setDXPExperienceId(
			segmentsExperience.getSegmentsExperienceKey());
		experiment.setDXPExperienceName(
			segmentsExperience.getName(LocaleUtil.getDefault()));

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.getSegmentsEntry(
				segmentsExperience.getSegmentsEntryId());

		experiment.setDXPSegmentId(segmentsEntry.getSegmentsEntryKey());
		experiment.setDXPSegmentName(
			segmentsEntry.getName(LocaleUtil.getDefault()));

		experiment.setExperimentStatus(_toDTO(segmentsExperiment.getStatus()));

		Layout layout = _layoutLocalService.getLayout(
			segmentsExperience.getClassPK());

		//TODO
		experiment.setPageURL(
			"http://www.example.com" + layout.getFriendlyURL());
		experiment.setPageTitle(layout.getTitle());

		experiment.setDataSourceId(_asahFaroBackendClient.getDataSourceId());

		return experiment;
	}

	private AsahFaroBackendClient _asahFaroBackendClient;

	@Reference
	private AsahFaroBackendClientFactory _asahFaroBackendClientFactory;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}