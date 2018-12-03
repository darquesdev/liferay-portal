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

package com.liferay.segments.internal.asah.client;

import com.liferay.segments.internal.asah.client.model.IndividualSegment;
import com.liferay.segments.internal.asah.client.model.Results;

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author David Arques
 */
public class AsahJSONMapperTest {

	@Test
	public void testMapToIndividualSegment() throws Exception {
		AsahJSONMapper asahJSONMapper = new AsahJSONMapper();

		String json = _read("get-individual-segment.json");

		IndividualSegment object = asahJSONMapper.mapToIndividualSegment(json);

		Assert.assertNotNull(object);
	}

	@Test
	public void testMapToIndividualSegmentResults() throws Exception {
		AsahJSONMapper asahJSONMapper = new AsahJSONMapper();

		String json = _read("get-individual-segments.json");

		Results<IndividualSegment> results =
			asahJSONMapper.mapToIndividualSegmentResults(json);

		Assert.assertEquals(2, results.getTotal());
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		URL url = clazz.getResource(fileName);

		byte[] bytes = Files.readAllBytes(Paths.get(url.toURI()));

		return new String(bytes, "UTF-8");
	}

}