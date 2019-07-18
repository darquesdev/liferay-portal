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

package com.liferay.segments.asah.connector.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.segments.asah.connector.internal.processor.AsahSegmentsExperimentsProcessor;
import com.liferay.segments.model.SegmentsExperiment;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sarai Díaz
 * @author David Arques
 */
@Component(immediate = true, service = ModelListener.class)
public class SegmentsExperimentModelListener
	extends BaseModelListener<SegmentsExperiment> {

	@Override
	public void onAfterCreate(SegmentsExperiment segmentsExperiment)
		throws ModelListenerException {

		try {
			_asahSegmentsExperimentsProcessor.addExperiment(segmentsExperiment);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Reference
	private AsahSegmentsExperimentsProcessor _asahSegmentsExperimentsProcessor;

}