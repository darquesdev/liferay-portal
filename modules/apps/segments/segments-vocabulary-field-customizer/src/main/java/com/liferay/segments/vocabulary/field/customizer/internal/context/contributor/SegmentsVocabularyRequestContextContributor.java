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

package com.liferay.segments.vocabulary.field.customizer.internal.context.contributor;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.segments.context.Context;
import com.liferay.segments.context.contributor.RequestContextContributor;
import com.liferay.segments.vocabulary.field.customizer.internal.configuration.SegmentsVocabularyRequestContextContributorConfiguration;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.segments.vocabulary.field.customizer.internal.configuration.SegmentsVocabularyRequestContextContributorConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	service = RequestContextContributor.class
)
public class SegmentsVocabularyRequestContextContributor
	implements RequestContextContributor {

	@Override
	public void contribute(
		Context context, HttpServletRequest httpServletRequest) {

		context.put(
			_parameterName,
			GetterUtil.getBoolean(
				httpServletRequest.getParameterValues(_parameterName)));
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_segmentsVocabularyRequestContextContributorConfiguration =
			ConfigurableUtil.createConfigurable(
				SegmentsVocabularyRequestContextContributorConfiguration.class,
				properties);

		_parameterName =
			_segmentsVocabularyRequestContextContributorConfiguration.
				parameterName();
	}

	private volatile String _parameterName;
	private volatile SegmentsVocabularyRequestContextContributorConfiguration
		_segmentsVocabularyRequestContextContributorConfiguration;

}