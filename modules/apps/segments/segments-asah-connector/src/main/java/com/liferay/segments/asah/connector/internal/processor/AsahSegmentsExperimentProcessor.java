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
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClient;
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClientFactory;
import com.liferay.segments.asah.connector.internal.client.converter.ExperimentDTOConverter;
import com.liferay.segments.asah.connector.internal.client.model.Experiment;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.service.SegmentsExperimentLocalService;

import java.util.Optional;

/**
 * @author Sarai Díaz
 * @author David Arques
 */
public class AsahSegmentsExperimentProcessor {

	public AsahSegmentsExperimentProcessor(
		Portal portal, CompanyLocalService companyLocalService,
		GroupLocalService groupLocalService,
		AsahFaroBackendClientFactory asahFaroBackendClientFactory,
		LayoutLocalService layoutLocalService,
		SegmentsEntryLocalService segmentsEntryLocalService,
		SegmentsExperienceLocalService segmentsExperienceLocalService,
		SegmentsExperimentLocalService segmentsExperimentLocalService) {

		_asahFaroBackendClientFactory = asahFaroBackendClientFactory;
		_experimentDTOConverter = new ExperimentDTOConverter(
			portal, companyLocalService, groupLocalService, layoutLocalService,
			segmentsEntryLocalService, segmentsExperienceLocalService);
		_segmentsExperimentLocalService = segmentsExperimentLocalService;
	}

	public void processAddSegmentsExperiment(
			SegmentsExperiment segmentsExperiment)
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
			_experimentDTOConverter.toDTO(
				segmentsExperiment, _asahFaroBackendClient.getDataSourceId()));

		segmentsExperiment.setSegmentsExperimentKey(experiment.getId());

		_segmentsExperimentLocalService.updateSegmentsExperiment(
			segmentsExperiment);
	}

	private AsahFaroBackendClient _asahFaroBackendClient;
	private final AsahFaroBackendClientFactory _asahFaroBackendClientFactory;
	private final ExperimentDTOConverter _experimentDTOConverter;
	private final SegmentsExperimentLocalService
		_segmentsExperimentLocalService;

}