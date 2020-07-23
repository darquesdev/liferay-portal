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

import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.item.action.ContentDashboardItemActionProvider;
import com.liferay.content.dashboard.item.action.ContentDashboardItemActionTracker;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

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
	public List<ContentDashboardItemAction> getContentDashboardItemActions(
		String className, long classPK, HttpServletRequest httpServletRequest,
		Locale locale) {

		Collection<ContentDashboardItemActionProvider>
			contentDashboardItemActionProviders =
				_contentDashboardItemActionProviders.values();

		Stream<ContentDashboardItemActionProvider> stream =
			contentDashboardItemActionProviders.stream();

		return stream.map(
			contentDashboardItemActionProvider ->
				contentDashboardItemActionProvider.
					getContentDashboardItemAction(
						className, classPK, httpServletRequest, locale)
		).filter(
			Optional::isPresent
		).map(
			Optional::get
		).collect(
			Collectors.collectingAndThen(
				Collectors.toList(), Collections::unmodifiableList)
		);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setContentDashboardItemActionProvider(
		ContentDashboardItemActionProvider contentDashboardItemActionProvider) {

		_contentDashboardItemActionProviders.put(
			contentDashboardItemActionProvider.getKey(),
			contentDashboardItemActionProvider);
	}

	protected void unsetContentDashboardItemActionProvider(
		ContentDashboardItemActionProvider contentDashboardItemActionProvider) {

		_contentDashboardItemActionProviders.remove(
			contentDashboardItemActionProvider.getKey());
	}

	private final Map<String, ContentDashboardItemActionProvider>
		_contentDashboardItemActionProviders = new ConcurrentHashMap<>();

}