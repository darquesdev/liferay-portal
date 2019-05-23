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

package com.liferay.segments.asah.connector.internal.provider.contributor;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.segments.asah.connector.internal.cache.AsahSegmentsCache;
import com.liferay.segments.asah.connector.internal.constants.AsahSegmentsDestinationNames;
import com.liferay.segments.context.Context;
import com.liferay.segments.provider.contributor.SegmentsEntryProviderContributor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(immediate = true, service = SegmentsEntryProviderContributor.class)
public class AsahSegmentsEntryProviderContributor
	implements SegmentsEntryProviderContributor {

	public long[] contribute(long[] segmentsEntryIds, Context context) {
		String userId = (String)context.get(Context.USER_ID);

		long[] cachedSegmentsEntryIds = _asahSegmentsCache.getSegmentsEntryIds(
			userId);

		if (cachedSegmentsEntryIds == null) {
			_sendMessage(userId);
		}

		return _appendSegmentsEntryIds(
			segmentsEntryIds, cachedSegmentsEntryIds);
	}

	@Activate
	protected void activate() {
		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_PARALLEL,
				AsahSegmentsDestinationNames.INDIVIDUAL_SEGMENTS);

		Destination destination = _destinationFactory.createDestination(
			destinationConfiguration);

		_messageBus.addDestination(destination);
	}

	@Deactivate
	protected void deactivate() {
		_messageBus.removeDestination(
			AsahSegmentsDestinationNames.INDIVIDUAL_SEGMENTS);
	}

	private long[] _appendSegmentsEntryIds(
		long[] segmentsEntryIds, long[] newSegmentsEntryIds) {

		List<Long> initialSegmentsEntryIds = ListUtil.toList(segmentsEntryIds);

		if (newSegmentsEntryIds == null) {
			newSegmentsEntryIds = new long[0];
		}

		List<Long> appendedSegmentsEntryIds = new ArrayList<>();

		Arrays.stream(
			newSegmentsEntryIds
		).filter(
			segmentsEntryId -> !initialSegmentsEntryIds.contains(
				segmentsEntryId)
		).forEach(
			appendedSegmentsEntryIds::add
		);

		return ArrayUtil.toLongArray(appendedSegmentsEntryIds);
	}

	private void _sendMessage(String userId) {
		Message message = new Message();

		message.setPayload(userId);

		_messageBus.sendMessage(
			AsahSegmentsDestinationNames.INDIVIDUAL_SEGMENTS, message);
	}

	@Reference
	private AsahSegmentsCache _asahSegmentsCache;

	@Reference
	private DestinationFactory _destinationFactory;

	@Reference
	private MessageBus _messageBus;

}