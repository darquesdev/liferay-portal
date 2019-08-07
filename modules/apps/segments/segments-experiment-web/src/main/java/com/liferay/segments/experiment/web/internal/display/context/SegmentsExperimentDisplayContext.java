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

package com.liferay.segments.experiment.web.internal.display.context;

import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.experiment.web.internal.util.SegmentsExperimentUtil;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.model.SegmentsExperimentRel;
import com.liferay.segments.service.SegmentsExperienceService;
import com.liferay.segments.service.SegmentsExperimentRelService;
import com.liferay.segments.service.SegmentsExperimentService;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo García
 */
public class SegmentsExperimentDisplayContext {

	public SegmentsExperimentDisplayContext(
		HttpServletRequest httpServletRequest, RenderResponse renderResponse,
		Portal portal, SegmentsExperienceService segmentsExperienceService,
		SegmentsExperimentService segmentsExperimentService,
		SegmentsExperimentRelService segmentsExperimentRelService) {

		_httpServletRequest = httpServletRequest;
		_renderResponse = renderResponse;
		_portal = portal;
		_segmentsExperienceService = segmentsExperienceService;
		_segmentsExperimentService = segmentsExperimentService;
		_segmentsExperimentRelService = segmentsExperimentRelService;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getContentPageEditorPortletNamespace() {
		return _portal.getPortletNamespace(
			ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET);
	}

	public String getCreateSegmentsExperimentURL() {
		PortletURL actionURL = _renderResponse.createActionURL();

		actionURL.setParameter(
			ActionRequest.ACTION_NAME, "/add_segments_experiment");

		return HttpUtil.addParameter(
			actionURL.toString(), "p_l_mode", Constants.VIEW);
	}

	public String getCreateSegmentsVariantURL() {
		return _getContentPageEditorActionURL(
			"/content_layout/add_segments_experience");
	}

	public JSONArray getSegmentsExperiencesJSONArray(Locale locale)
		throws PortalException {

		List<SegmentsExperience> segmentsExperiences =
			_segmentsExperienceService.getSegmentsExperiences(
				_themeDisplay.getScopeGroupId(),
				_portal.getClassNameId(Layout.class), _themeDisplay.getPlid(),
				true);

		JSONArray segmentsExperiencesJSONArray =
			JSONFactoryUtil.createJSONArray();

		for (SegmentsExperience segmentsExperience : segmentsExperiences) {
			segmentsExperiencesJSONArray.put(
				JSONUtil.put(
					"name", segmentsExperience.getName(locale)
				).put(
					"segmentsExperienceId",
					String.valueOf(segmentsExperience.getSegmentsExperienceId())
				).put(
					"segmentsExperiment",
					SegmentsExperimentUtil.toSegmentsExperimentJSONObject(
						_getDraftSegmentsExperimentOptional(
							segmentsExperience.getSegmentsExperienceId()
						).orElse(
							null
						))
				));
		}

		segmentsExperiencesJSONArray.put(
			JSONUtil.put(
				"name",
				SegmentsExperienceConstants.getDefaultSegmentsExperienceName(
					locale)
			).put(
				"segmentsExperienceId",
				String.valueOf(SegmentsExperienceConstants.ID_DEFAULT)
			).put(
				"segmentsExperiment",
				SegmentsExperimentUtil.toSegmentsExperimentJSONObject(
					_getDraftSegmentsExperimentOptional(
						SegmentsExperienceConstants.ID_DEFAULT
					).orElse(
						null
					))
			));

		return segmentsExperiencesJSONArray;
	}

	public JSONObject getSegmentsExperimentJSONObject() throws PortalException {
		return SegmentsExperimentUtil.toSegmentsExperimentJSONObject(
			_getSegmentsExperiment());
	}

	public JSONArray getSegmentsExperimentRelsJSONArray(Locale locale)
		throws PortalException {

		SegmentsExperiment segmentsExperiment = _getSegmentsExperiment();

		JSONArray segmentsExperimentRelsJSONArray =
			JSONFactoryUtil.createJSONArray();

		if (segmentsExperiment == null) {
			return segmentsExperimentRelsJSONArray;
		}

		List<SegmentsExperimentRel> segmentsExperimentRels =
			_segmentsExperimentRelService.getSegmentsExperimentRels(
				segmentsExperiment.getSegmentsExperimentId());

		for (SegmentsExperimentRel segmentsExperimentRel :
				segmentsExperimentRels) {

			segmentsExperimentRelsJSONArray.put(
				SegmentsExperimentUtil.toSegmentsExperimentRelJSONObject(
					segmentsExperimentRel, locale));
		}

		return segmentsExperimentRelsJSONArray;
	}

	public long getSelectedSegmentsExperienceId() {
		if (Validator.isNotNull(_segmentsExperienceId)) {
			return _segmentsExperienceId;
		}

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(_httpServletRequest);

		_segmentsExperienceId = ParamUtil.getLong(
			originalHttpServletRequest, "segmentsExperienceId",
			SegmentsExperienceConstants.ID_DEFAULT);

		return _segmentsExperienceId;
	}

	private String _getContentPageEditorActionURL(String action) {
		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(_renderResponse);

		PortletURL actionURL = liferayPortletResponse.createActionURL(
			ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET);

		actionURL.setParameter(ActionRequest.ACTION_NAME, action);

		return HttpUtil.addParameter(
			actionURL.toString(), "p_l_mode", Constants.EDIT);
	}

	private Optional<SegmentsExperiment> _getDraftSegmentsExperimentOptional(
			long segmentsExperienceId)
		throws PortalException {

		List<SegmentsExperiment> segmentsExperienceSegmentsExperiments =
			_segmentsExperimentService.getSegmentsExperienceSegmentsExperiments(
				segmentsExperienceId, _portal.getClassNameId(Layout.class),
				_themeDisplay.getPlid(),
				SegmentsExperimentConstants.STATUS_DRAFT);

		Stream<SegmentsExperiment> segmentsExperienceSegmentsExperimentsStream =
			segmentsExperienceSegmentsExperiments.stream();

		return segmentsExperienceSegmentsExperimentsStream.findFirst();
	}

	private SegmentsExperiment _getSegmentsExperiment() throws PortalException {
		if (_segmentsExperiment != null) {
			return _segmentsExperiment;
		}

		_segmentsExperiment = _getDraftSegmentsExperimentOptional(
			getSelectedSegmentsExperienceId()
		).orElse(
			null
		);

		return _segmentsExperiment;
	}

	private final HttpServletRequest _httpServletRequest;
	private final Portal _portal;
	private final RenderResponse _renderResponse;
	private Long _segmentsExperienceId;
	private final SegmentsExperienceService _segmentsExperienceService;
	private SegmentsExperiment _segmentsExperiment;
	private final SegmentsExperimentRelService _segmentsExperimentRelService;
	private final SegmentsExperimentService _segmentsExperimentService;
	private final ThemeDisplay _themeDisplay;

}