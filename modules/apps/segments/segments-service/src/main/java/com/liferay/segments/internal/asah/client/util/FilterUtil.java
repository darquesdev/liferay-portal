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

package com.liferay.segments.internal.asah.client.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.time.Instant;

import java.util.Date;

/**
 * @author Matthew Kong
 * @author David Arques
 */
public class FilterUtil {

	public static String getBlankFilter(String fieldName, String operator) {
		return fieldName.concat(operator).concat(StringPool.DOUBLE_APOSTROPHE);
	}

	public static String getFieldName(
		String fieldName, String fieldNameContext) {

		if (Validator.isNull(fieldNameContext)) {
			return fieldName;
		}

		return StringUtil.replace(
			fieldNameContext, CharPool.QUESTION, fieldName);
	}

	public static String getFilter(
		String fieldName, String operator, Object value) {

		if (value == null) {
			return null;
		}

		if (value instanceof Date) {
			Date date = (Date)value;

			Instant instant = date.toInstant();

			value = instant.toString();
		}
		else {
			String valueString = String.valueOf(value);

			if (Validator.isBlank(valueString)) {
				return null;
			}

			value = StringUtil.quote(valueString, StringPool.APOSTROPHE);
		}

		if (FilterConstants.isStringFunction(operator)) {
			StringBundler sb = new StringBundler(6);

			sb.append(operator);
			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(fieldName);
			sb.append(StringPool.COMMA);
			sb.append(value);
			sb.append(StringPool.CLOSE_PARENTHESIS);

			return sb.toString();
		}

		StringBundler sb = new StringBundler(3);

		sb.append(fieldName);
		sb.append(operator);
		sb.append(value);

		return sb.toString();
	}

	public static String getNullFilter(String fieldName, String operator) {
		return fieldName.concat(operator).concat(StringPool.NULL);
	}

}