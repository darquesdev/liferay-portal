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

package com.liferay.segments.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;

import org.osgi.service.component.annotations.Component;

/**
 * @author David Arques
 */
@Component(immediate = true, service = ModelListener.class)
public class UserModelListener extends BaseModelListener<User> {

	@Override
	public void onAfterCreate(User model) throws ModelListenerException {
		if (_log.isDebugEnabled()) {
			_log.debug("onAfterCreate");
		}
	}

	@Override
	public void onAfterRemove(User user) throws ModelListenerException {
		if (_log.isDebugEnabled()) {
			_log.debug("onAfterRemove");
		}
	}

	@Override
	public void onAfterUpdate(User model) throws ModelListenerException {
		if (_log.isDebugEnabled()) {
			_log.debug("onAfterUpdate");
		}
	}

	@Override
	public void onBeforeRemove(User model) throws ModelListenerException {
		if (_log.isDebugEnabled()) {
			_log.debug("onBeforeRemove");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserModelListener.class);

}