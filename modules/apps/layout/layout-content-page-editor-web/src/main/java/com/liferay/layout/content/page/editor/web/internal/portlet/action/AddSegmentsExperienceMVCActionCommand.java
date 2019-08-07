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

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLinkService;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.model.SegmentsExperimentRel;
import com.liferay.segments.service.SegmentsExperienceService;
import com.liferay.segments.service.SegmentsExperimentRelService;
import com.liferay.segments.service.SegmentsExperimentService;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Callable<JSONObject> callable = new AddSegmentsExperienceCallable(
			actionRequest);

		JSONObject jsonObject = null;

		try {
			jsonObject = TransactionInvokerUtil.invoke(
				_transactionConfig, callable);
		}
		catch (Throwable t) {
			_log.error(t, t);

			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(actionResponse);

			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);

			jsonObject = JSONUtil.put(
				"error",
				LanguageUtil.get(
					themeDisplay.getRequest(), "an-unexpected-error-occurred"));
		}

		hideDefaultSuccessMessage(actionRequest);

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private String _addLayoutData(
			long groupId, long classNameId, long classPK,
			long segmentsExperienceId, long baseSegmentsExperienceId)
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureService.
				fetchLayoutPageTemplateStructure(
					groupId, classNameId, classPK, true);

		String data = layoutPageTemplateStructure.getData(
			baseSegmentsExperienceId);

		_layoutPageTemplateStructureService.updateLayoutPageTemplateStructure(
			groupId, classNameId, classPK, segmentsExperienceId, data);

		return data;
	}

	private JSONObject _addSegmentsExperience(ActionRequest actionRequest)
		throws PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		long classNameId = ParamUtil.getLong(actionRequest, "classNameId");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");
		long segmentsExperimentId = ParamUtil.getLong(
			actionRequest, "segmentsExperimentId");

		SegmentsExperiment segmentsExperiment = null;

		if (segmentsExperimentId != GetterUtil.DEFAULT_LONG) {
			segmentsExperiment =
				_segmentsExperimentService.getSegmentsExperiment(
					segmentsExperimentId);
		}

		SegmentsExperience segmentsExperience = _addSegmentsExperience(
			actionRequest, classNameId, classPK, segmentsExperiment);

		_populateSegmentsExperienceJSONObject(jsonObject, segmentsExperience);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long baseSegmentsExperienceId = _getBaseSegmentsExperienceId(
			segmentsExperiment);

		String layoutData = _addLayoutData(
			themeDisplay.getScopeGroupId(), classNameId, classPK,
			segmentsExperience.getSegmentsExperienceId(),
			baseSegmentsExperienceId);

		_populateLayoutDataJSONObject(jsonObject, layoutData);

		Map<Long, String> fragmentEntryLinksEditableValuesMap =
			_updateFragmentEntryLinksEditableValues(
				themeDisplay.getScopeGroupId(), classNameId, classPK,
				segmentsExperience.getSegmentsExperienceId(),
				baseSegmentsExperienceId);

		_populateFragmentEntryLinksJSONObject(
			jsonObject, fragmentEntryLinksEditableValuesMap);

		if (segmentsExperiment != null) {
			SegmentsExperimentRel segmentsExperimentRel =
				_addSegmentsExperimentRel(
					actionRequest, segmentsExperiment, segmentsExperience);

			_populateSegmentsSegmentsExperimentRelJSONObject(
				jsonObject, segmentsExperience, segmentsExperimentRel);
		}

		return jsonObject;
	}

	private SegmentsExperience _addSegmentsExperience(
			ActionRequest actionRequest, long classNameId, long classPK,
			SegmentsExperiment segmentsExperiment)
		throws PortalException {

		boolean active = ParamUtil.getBoolean(actionRequest, "active", true);

		long segmentsEntryId = ParamUtil.getLong(
			actionRequest, "segmentsEntryId");

		if (segmentsExperiment != null) {
			active = false;

			long segmentsExperienceId =
				segmentsExperiment.getSegmentsExperienceId();

			segmentsEntryId = SegmentsEntryConstants.ID_DEFAULT;

			if (segmentsExperienceId !=
					SegmentsExperienceConstants.ID_DEFAULT) {

				SegmentsExperience segmentsExperience =
					_segmentsExperienceService.getSegmentsExperience(
						segmentsExperienceId);

				segmentsEntryId = segmentsExperience.getSegmentsEntryId();
			}
		}

		return _segmentsExperienceService.addSegmentsExperience(
			segmentsEntryId, classNameId, classPK,
			new HashMap<Locale, String>() {
				{
					put(
						LocaleUtil.getSiteDefault(),
						ParamUtil.getString(actionRequest, "name"));
				}
			},
			active, ServiceContextFactory.getInstance(actionRequest));
	}

	private SegmentsExperimentRel _addSegmentsExperimentRel(
			ActionRequest actionRequest, SegmentsExperiment segmentsExperiment,
			SegmentsExperience segmentsExperience)
		throws PortalException {

		return _segmentsExperimentRelService.addSegmentsExperimentRel(
			segmentsExperiment.getSegmentsExperimentId(),
			segmentsExperience.getSegmentsExperienceId(),
			ServiceContextFactory.getInstance(actionRequest));
	}

	private long _getBaseSegmentsExperienceId(
		SegmentsExperiment segmentsExperiment) {

		if (segmentsExperiment == null) {
			return SegmentsExperienceConstants.ID_DEFAULT;
		}

		return segmentsExperiment.getSegmentsExperienceId();
	}

	private void _populateFragmentEntryLinksJSONObject(
			JSONObject jsonObject,
			Map<Long, String> fragmentEntryLinksEditableValuesMap)
		throws JSONException {

		JSONObject fragmentEntryLinksJSONObject =
			JSONFactoryUtil.createJSONObject();

		for (Map.Entry<Long, String> entry :
				fragmentEntryLinksEditableValuesMap.entrySet()) {

			fragmentEntryLinksJSONObject.put(
				String.valueOf(entry.getKey()),
				JSONFactoryUtil.createJSONObject(entry.getValue()));
		}

		jsonObject.put("fragmentEntryLinks", fragmentEntryLinksJSONObject);
	}

	private void _populateLayoutDataJSONObject(
			JSONObject jsonObject, String layoutData)
		throws JSONException {

		jsonObject.put(
			"layoutData", JSONFactoryUtil.createJSONObject(layoutData));
	}

	private void _populateSegmentsExperienceJSONObject(
		JSONObject jsonObject, SegmentsExperience segmentsExperience) {

		jsonObject.put(
			"segmentsExperience",
			JSONUtil.put(
				"active", segmentsExperience.isActive()
			).put(
				"name", segmentsExperience.getNameCurrentValue()
			).put(
				"priority", segmentsExperience.getPriority()
			).put(
				"segmentsEntryId", segmentsExperience.getSegmentsEntryId()
			).put(
				"segmentsExperienceId",
				segmentsExperience.getSegmentsExperienceId()
			));
	}

	private void _populateSegmentsSegmentsExperimentRelJSONObject(
		JSONObject jsonObject, SegmentsExperience segmentsExperience,
		SegmentsExperimentRel segmentsExperimentRel) {

		jsonObject.put(
			"segmentsExperimentRel",
			JSONUtil.put(
				"name", segmentsExperience.getNameCurrentValue()
			).put(
				"segmentsExperienceId",
				segmentsExperimentRel.getSegmentsExperienceId()
			).put(
				"segmentsExperimentId",
				segmentsExperimentRel.getSegmentsExperimentId()
			).put(
				"segmentsExperimentRelId",
				segmentsExperimentRel.getSegmentsExperimentRelId()
			));
	}

	private Map<Long, String> _updateFragmentEntryLinksEditableValues(
			long groupId, long classNameId, long classPK,
			long segmentsExperienceId, long baseSegmentsExperienceId)
		throws PortalException {

		Map<Long, String> fragmentEntryLinksEditableValuesMap = new HashMap<>();

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinks(
				groupId, classNameId, classPK);

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			JSONObject editableValuesJSONObject =
				JSONFactoryUtil.createJSONObject(
					fragmentEntryLink.getEditableValues());

			Iterator<String> keysIterator = editableValuesJSONObject.keys();

			while (keysIterator.hasNext()) {
				String editableProcessorKey = keysIterator.next();

				JSONObject editableProcessorJSONObject =
					editableValuesJSONObject.getJSONObject(
						editableProcessorKey);

				if (editableProcessorJSONObject == null) {
					continue;
				}

				Iterator<String> editableKeysIterator =
					editableProcessorJSONObject.keys();

				while (editableKeysIterator.hasNext()) {
					String editableKey = editableKeysIterator.next();

					JSONObject editableJSONObject =
						editableProcessorJSONObject.getJSONObject(editableKey);

					JSONObject valueJSONObject = null;

					if (editableJSONObject.has(
							SegmentsExperienceConstants.ID_PREFIX +
								baseSegmentsExperienceId)) {

						valueJSONObject = editableJSONObject.getJSONObject(
							SegmentsExperienceConstants.ID_PREFIX +
								baseSegmentsExperienceId);
					}
					else if (editableJSONObject.has("defaultValue")) {
						valueJSONObject = JSONUtil.put(
							"defaultValue",
							editableJSONObject.getString("defaultValue"));
					}
					else {
						continue;
					}

					editableJSONObject.put(
						SegmentsExperienceConstants.ID_PREFIX +
							segmentsExperienceId,
						valueJSONObject);

					editableProcessorJSONObject.put(
						editableKey, editableJSONObject);

					editableValuesJSONObject.put(
						editableProcessorKey, editableProcessorJSONObject);
				}
			}

			fragmentEntryLinksEditableValuesMap.put(
				fragmentEntryLink.getFragmentEntryLinkId(),
				editableValuesJSONObject.toString());
		}

		_fragmentEntryLinkService.updateFragmentEntryLinks(
			fragmentEntryLinksEditableValuesMap);

		return fragmentEntryLinksEditableValuesMap;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddSegmentsExperienceMVCActionCommand.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentEntryLinkService _fragmentEntryLinkService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureService;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsExperienceService _segmentsExperienceService;

	@Reference
	private SegmentsExperimentRelService _segmentsExperimentRelService;

	@Reference
	private SegmentsExperimentService _segmentsExperimentService;

	private class AddSegmentsExperienceCallable
		implements Callable<JSONObject> {

		@Override
		public JSONObject call() throws Exception {
			return _addSegmentsExperience(_actionRequest);
		}

		private AddSegmentsExperienceCallable(ActionRequest actionRequest) {
			_actionRequest = actionRequest;
		}

		private final ActionRequest _actionRequest;

	}

}