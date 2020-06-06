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

package com.liferay.content.dashboard.web.internal.display.context;

import com.liferay.content.dashboard.web.internal.item.ContentDashboardItem;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import javax.portlet.RenderRequest;

/**
 * @author Cristina González
 */
public class ContentDashboardAdminDisplayContext {

	public ContentDashboardAdminDisplayContext(
		RenderRequest renderRequest,
		SearchContainer<ContentDashboardItem<?>> searchContainer) {

		_renderRequest = renderRequest;
		_searchContainer = searchContainer;
	}

	public SearchContainer<ContentDashboardItem<?>> getSearchContainer() {
		return _searchContainer;
	}

	public Integer getStatus() {
		if (_status != null) {
			return _status;
		}

		_status = ParamUtil.getInteger(
			_renderRequest, "status", WorkflowConstants.STATUS_ANY);

		return _status;
	}

	private final RenderRequest _renderRequest;
	private final SearchContainer<ContentDashboardItem<?>> _searchContainer;
	private Integer _status;

}