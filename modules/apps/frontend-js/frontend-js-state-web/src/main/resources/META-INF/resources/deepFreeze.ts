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

export default function deepFreeze<T>(value: T): Immutable<T> {
	if (process.env.NODE_ENV === 'development') {
		if (Array.isArray(value)) {
			value.forEach(deepFreeze);
		}
		else if (value && typeof value === 'object') {
			Object.values(value).forEach(deepFreeze);
		}

		Object.freeze(value);
	}

	return value as Immutable<T>;
}
