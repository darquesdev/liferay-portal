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

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceService;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/content_layout/add_segments_experience"
	},
	service = MVCActionCommand.class
)
public class AddSegmentsExperienceMVCActionCommand
	extends BaseMVCActionCommand {

	protected SegmentsExperience addSegmentsExperience(
			ActionRequest actionRequest)
		throws PortalException {

		return _segmentsExperienceService.addSegmentsExperience(
			ParamUtil.getLong(actionRequest, "segmentsEntryId"),
			ParamUtil.getLong(actionRequest, "classNameId"),
			ParamUtil.getLong(actionRequest, "classPK"),
			_getNameMap(
				LocaleUtil.getSiteDefault(),
				ParamUtil.getString(actionRequest, "name")),
			ParamUtil.getBoolean(actionRequest, "active", true),
			ServiceContextFactory.getInstance(actionRequest));
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Callable<SegmentsExperience> callable =
			new AddSegmentsExperienceCallable(actionRequest);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			SegmentsExperience segmentsExperience =
				TransactionInvokerUtil.invoke(_transactionConfig, callable);

			_populateSegmentsExperienceJSONObject(
				jsonObject, segmentsExperience);
		}
		catch (Throwable t) {
			_log.error(t, t);

			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(actionResponse);

			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);

			jsonObject.put(
				"error",
				LanguageUtil.get(
					themeDisplay.getRequest(), "an-unexpected-error-occurred"));
		}

		hideDefaultSuccessMessage(actionRequest);

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private Map<Locale, String> _getNameMap(Locale locale, String name) {
		HashMap<Locale, String> nameMap = new HashMap<>();

		nameMap.put(locale, name);

		return nameMap;
	}

	private void _populateSegmentsExperienceJSONObject(
		JSONObject jsonObject, SegmentsExperience segmentsExperience) {

		jsonObject.put(
			"active", segmentsExperience.isActive()
		).put(
			"name", segmentsExperience.getNameCurrentValue()
		).put(
			"priority", segmentsExperience.getPriority()
		).put(
			"segmentsEntryId", segmentsExperience.getSegmentsEntryId()
		).put(
			"segmentsExperienceId", segmentsExperience.getSegmentsExperienceId()
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddSegmentsExperienceMVCActionCommand.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsExperienceService _segmentsExperienceService;

	private class AddSegmentsExperienceCallable
		implements Callable<SegmentsExperience> {

		@Override
		public SegmentsExperience call() throws Exception {
			return addSegmentsExperience(_actionRequest);
		}

		private AddSegmentsExperienceCallable(ActionRequest actionRequest) {
			_actionRequest = actionRequest;
		}

		private final ActionRequest _actionRequest;

	}

}