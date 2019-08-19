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

package com.liferay.segments.experiment.web.internal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.model.SegmentsExperimentRel;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author David Arques
 */
public class SegmentsExperimentUtil {

	public static boolean isAnalyticsEnabled(long companyId) {
		if (Validator.isNull(
				PrefsPropsUtil.getString(
					companyId, "liferayAnalyticsDataSourceId")) ||
			Validator.isNull(
				PrefsPropsUtil.getString(
					companyId,
					"liferayAnalyticsFaroBackendSecuritySignature")) ||
			Validator.isNull(
				PrefsPropsUtil.getString(
					companyId, "liferayAnalyticsFaroBackendURL"))) {

			return false;
		}

		return true;
	}

	public static JSONObject toSegmentsExperimentJSONObject(
			Locale locale, SegmentsExperiment segmentsExperiment)
		throws PortalException {

		if (segmentsExperiment == null) {
			return null;
		}

		UnicodeProperties typeSettingsProperties =
			segmentsExperiment.getTypeSettingsProperties();

		return JSONUtil.put(
			"description", segmentsExperiment.getDescription()
		).put(
			"goal", typeSettingsProperties.getProperty("goal")
		).put(
			"goalTarget", typeSettingsProperties.getProperty("goalTarget")
		).put(
			"name", segmentsExperiment.getName()
		).put(
			"segmentsEntryName", segmentsExperiment.getSegmentsEntryName(locale)
		).put(
			"segmentsExperienceId",
			String.valueOf(segmentsExperiment.getSegmentsExperienceId())
		).put(
			"segmentsExperimentId",
			String.valueOf(segmentsExperiment.getSegmentsExperimentId())
		).put(
			"status", toStatusJSONObject(locale, segmentsExperiment.getStatus())
		);
	}

	public static JSONObject toSegmentsExperimentRelJSONObject(
			SegmentsExperimentRel segmentsExperimentRel, Locale locale)
		throws PortalException {

		if (segmentsExperimentRel == null) {
			return null;
		}

		return JSONUtil.put(
			"control", segmentsExperimentRel.isControl()
		).put(
			"name", segmentsExperimentRel.getName(locale)
		).put(
			"segmentsExperienceId",
			String.valueOf(segmentsExperimentRel.getSegmentsExperienceId())
		).put(
			"segmentsExperimentId",
			String.valueOf(segmentsExperimentRel.getSegmentsExperimentId())
		).put(
			"segmentsExperimentRelId",
			String.valueOf(segmentsExperimentRel.getSegmentsExperimentRelId())
		);
	}

	public static JSONObject toStatusJSONObject(
		Locale locale, int statusValue) {

		SegmentsExperimentConstants.Status status =
			SegmentsExperimentConstants.Status.parse(statusValue);

		if (status == null) {
			return null;
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, SegmentsExperimentUtil.class);

		return JSONUtil.put(
			"label", LanguageUtil.get(resourceBundle, status.getLabel())
		).put(
			"value", status.getValue()
		);
	}

	private SegmentsExperimentUtil() {
	}

}