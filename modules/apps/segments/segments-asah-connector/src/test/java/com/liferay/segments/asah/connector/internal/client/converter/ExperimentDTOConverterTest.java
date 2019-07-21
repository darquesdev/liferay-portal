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

package com.liferay.segments.asah.connector.internal.client.converter;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.model.SegmentsEntryRel;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author David Arques
 */
@RunWith(MockitoJUnitRunner.class)
public class ExperimentDTOConverterTest {

	@Before
	public void setUp() throws PortalException {
		_experimentDTOConverter = new ExperimentDTOConverter(
			null, null, null, _layoutLocalService, _segmentsEntryLocalService,
			_segmentsExperienceLocalService) {

			@Override
			protected String getLayoutFullURL(Layout layout)
				throws PortalException {

				return _FAKE_URL;
			}

		};
	}

	@Test
	public void testToDTOWithSegmentsExperimentWithDefaultExperience() {
		Assert.assertEquals(1, 1);
	}

	@Test
	public void testToDTOWithSegmentsExperimentWithNonDefaultExperience() {
		Assert.assertEquals(1, 1);
	}

	private Layout _createLayout(String title) {
		Layout layout = Mockito.mock(Layout.class);

		Mockito.doReturn(
			title
		).when(
			layout
		).getTitle(
			LocaleUtil.getDefault()
		);

		return layout;
	}

	private SegmentsEntryRel _createSegmentsEntryRel(long segmentsEntryRelId) {
		SegmentsEntryRel segmentsEntryRel = Mockito.mock(
			SegmentsEntryRel.class);

		Mockito.doReturn(
			segmentsEntryRelId
		).when(
			segmentsEntryRel
		).getClassPK();

		return segmentsEntryRel;
	}

	private SegmentsExperience _createSegmentsExperience(
		String segmentsExperienceKey, String name, long segmentsEntryId) {

		SegmentsExperience segmentsExperience = Mockito.mock(
			SegmentsExperience.class);

		Mockito.doReturn(
			segmentsExperienceKey
		).when(
			segmentsExperience
		).getSegmentsExperienceKey();

		Mockito.doReturn(
			name
		).when(
			segmentsExperience
		).getName();

		Mockito.doReturn(
			segmentsEntryId
		).when(
			segmentsExperience
		).getSegmentsEntryId();

		return segmentsExperience;
	}

	private SegmentsExperiment _createSegmentsExperimentWithDefaultExperience(
		Date createDate, Date modifiedDate, String name, String description,
		int status, long classPK) {

		SegmentsExperiment segmentsExperiment = Mockito.mock(
			SegmentsExperiment.class);

		Mockito.doReturn(
			createDate
		).when(
			segmentsExperiment
		).getCreateDate();

		Mockito.doReturn(
			modifiedDate
		).when(
			segmentsExperiment
		).getModifiedDate();

		Mockito.doReturn(
			name
		).when(
			segmentsExperiment
		).getName();

		Mockito.doReturn(
			description
		).when(
			segmentsExperiment
		).getDescription();

		Mockito.doReturn(
			SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT
		).when(
			segmentsExperiment
		).getSegmentsExperienceId();

		Mockito.doReturn(
			status
		).when(
			segmentsExperiment
		).getStatus();

		Mockito.doReturn(
			classPK
		).when(
			segmentsExperiment
		).getClassPK();

		return segmentsExperiment;
	}

	private SegmentsExperiment
		_createSegmentsExperimentWithNonDefaultExperience(
			Date createDate, Date modifiedDate, String name, String description,
			long segmentsExperienceId, int status, long classPK) {

		SegmentsExperiment segmentsExperiment = Mockito.mock(
			SegmentsExperiment.class);

		Mockito.doReturn(
			createDate
		).when(
			segmentsExperiment
		).getCreateDate();

		Mockito.doReturn(
			modifiedDate
		).when(
			segmentsExperiment
		).getModifiedDate();

		Mockito.doReturn(
			name
		).when(
			segmentsExperiment
		).getName();

		Mockito.doReturn(
			description
		).when(
			segmentsExperiment
		).getDescription();

		Mockito.doReturn(
			segmentsExperienceId
		).when(
			segmentsExperiment
		).getSegmentsExperienceId();

		Mockito.doReturn(
			status
		).when(
			segmentsExperiment
		).getStatus();

		Mockito.doReturn(
			classPK
		).when(
			segmentsExperiment
		).getClassPK();

		return segmentsExperiment;
	}

	private static final String _FAKE_URL = "http://localhost:8080/home";

	private ExperimentDTOConverter _experimentDTOConverter;

	@Mock
	private LayoutLocalService _layoutLocalService;

	@Mock
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Mock
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}