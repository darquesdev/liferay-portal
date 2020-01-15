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

package com.liferay.segments.vocabulary.field.customizer.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Raymond Augé
 */
@ExtendedObjectClassDefinition(
	category = "segments",
	factoryInstanceLabelAttribute = "request.context.contributor.key"
)
@Meta.OCD(
	description = "segments-vocabulary-request-context-contributor-configuration-description",
	factory = true,
	id = "com.liferay.segments.vocabulary.field.customizer.internal.configuration.SegmentsVocabularyRequestContextContributorConfiguration",
	localization = "content/Language",
	name = "segments-vocabulary-request-context-contributor-configuration-name"
)
public interface SegmentsVocabularyRequestContextContributorConfiguration {

	@Meta.AD(
		description = "segments-vocabulary-request-context-contributor-parameter-name-description",
		id = "request.context.contributor.key",
		name = "segments-vocabulary-request-context-contributor-parameter-name"
	)
	public String parameterName();

}