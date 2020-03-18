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

package com.liferay.segments.context.vocabulary.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Raymond Augé
 */
@ExtendedObjectClassDefinition(
	category = "segments", factoryInstanceLabelAttribute = "contextName"
)
@Meta.OCD(
	description = "segments-context-vocabulary-configuration-description",
	factory = true,
	id = "com.liferay.segments.context.vocabulary.internal.configuration.SegmentsContextVocabularyConfiguration",
	localization = "content/Language",
	name = "segments-context-vocabulary-configuration-name"
)
public interface SegmentsContextVocabularyConfiguration {

	@Meta.AD(
		description = "segments-context-vocabulary-configuration-context-name-description",
		name = "segments-context-vocabulary-configuration-context-name-name"
	)
	public String contextName();

	@Meta.AD(
		description = "segments-context-vocabulary-configuration-vocabulary-name-description",
		name = "segments-context-vocabulary-configuration-vocabulary-name-name"
	)
	public String vocabularyName();

}