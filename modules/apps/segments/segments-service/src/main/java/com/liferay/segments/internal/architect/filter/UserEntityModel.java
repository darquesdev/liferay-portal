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

package com.liferay.segments.internal.architect.filter;

import com.liferay.portal.kernel.search.Field;
import com.liferay.structured.content.apio.architect.entity.EntityField;
import com.liferay.structured.content.apio.architect.entity.EntityModel;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * Provides the entity data model from the Indexed Entity (User).
 *
 * @author David Arques
 *
 * @review
 */
@Component(
	immediate = true, property = "entity.model.name=" + UserEntityModel.NAME,
	service = EntityModel.class
)
public class UserEntityModel implements EntityModel {

	public static final String NAME = "User";

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldsMap;
	}

	@Override
	public String getName() {
		return NAME;
	}

	private static final Map<String, EntityField> _entityFieldsMap = Stream.of(
		new EntityField(
			"dateModified", EntityField.Type.DATE,
			locale -> Field.getSortableFieldName(Field.MODIFIED_DATE),
			locale -> Field.MODIFIED_DATE),
		new EntityField(
			"emailAddress", EntityField.Type.STRING, locale -> "emailAddress"),
		new EntityField(
			"firstName", EntityField.Type.STRING,
			locale -> Field.getSortableFieldName("firstName")),
		new EntityField(
			"groupId", EntityField.Type.STRING, locale -> "groupId"),
		new EntityField(
			"groupIds", EntityField.Type.STRING, locale -> "groupIds"),
		new EntityField(
			"lastName", EntityField.Type.STRING,
			locale -> Field.getSortableFieldName("lastName")),
		new EntityField(
			"screenName", EntityField.Type.STRING,
			locale -> Field.getSortableFieldName("screenName"),
			locale -> "screenName")
	).collect(
		Collectors.toMap(EntityField::getName, Function.identity())
	);

}