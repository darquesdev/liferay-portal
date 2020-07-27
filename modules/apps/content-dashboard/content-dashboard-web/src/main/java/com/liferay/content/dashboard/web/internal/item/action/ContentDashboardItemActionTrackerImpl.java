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

package com.liferay.content.dashboard.web.internal.item.action;

import com.liferay.content.dashboard.item.action.ContentDashboardItemActionProvider;
import com.liferay.content.dashboard.item.action.ContentDashboardItemActionTracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author David Arques
 */
@Component(immediate = true, service = ContentDashboardItemActionTracker.class)
public class ContentDashboardItemActionTrackerImpl
	implements ContentDashboardItemActionTracker {

	@Override
	public <T> List<ContentDashboardItemActionProvider<T>>
		getContentDashboardItemActionProviders(String className) {

		Map<String, ContentDashboardItemActionProvider<?>>
			contentDashboardItemActionProviders =
				_contentDashboardItemActionProvidersMap.get(className);

		return new ArrayList(contentDashboardItemActionProviders.values());
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setContentDashboardItemActionProvider(
		ContentDashboardItemActionProvider<?>
			contentDashboardItemActionProvider,
		Map<String, Object> properties) {

		String className = (String)properties.get("model.class.name");

		Map<String, ContentDashboardItemActionProvider<?>>
			contentDashboardItemActionProviders =
				_contentDashboardItemActionProvidersMap.get(className);

		if (contentDashboardItemActionProviders == null) {
			contentDashboardItemActionProviders = new HashMap<>();
		}

		contentDashboardItemActionProviders.put(
			contentDashboardItemActionProvider.getKey(),
			contentDashboardItemActionProvider);

		_contentDashboardItemActionProvidersMap.put(
			className, contentDashboardItemActionProviders);
	}

	protected void unsetContentDashboardItemActionProvider(
		ContentDashboardItemActionProvider<?>
			contentDashboardItemActionProvider,
		Map<String, Object> properties) {

		String className = (String)properties.get("model.class.name");

		Map<String, ContentDashboardItemActionProvider<?>>
			contentDashboardItemActionProviders =
				_contentDashboardItemActionProvidersMap.get(className);

		if (contentDashboardItemActionProviders == null) {
			return;
		}

		contentDashboardItemActionProviders.remove(
			contentDashboardItemActionProvider.getKey());

		if (contentDashboardItemActionProviders.isEmpty()) {
			_contentDashboardItemActionProvidersMap.remove(className);
		}
	}

	private final Map
		<String, Map<String, ContentDashboardItemActionProvider<?>>>
			_contentDashboardItemActionProvidersMap = new ConcurrentHashMap<>();

}