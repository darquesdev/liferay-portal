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

package com.liferay.layout.content.page.editor.web.internal.model.listener;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLinkService;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.util.SegmentsExperiencePortletUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sarai Díaz
 * @author David Arques
 */
@Component(immediate = true, service = ModelListener.class)
public class SegmentsExperimentModelListener
	extends BaseModelListener<SegmentsExperiment> {

	@Override
	public void onAfterUpdate(SegmentsExperiment segmentsExperiment)
		throws ModelListenerException {

		if (!_requiresDefaultExperienceReplacement(segmentsExperiment)) {
			return;
		}

		try {
			_copySegmentsExperienceData(
				segmentsExperiment.getGroupId(),
				segmentsExperiment.getClassNameId(),
				segmentsExperiment.getClassPK(),
				segmentsExperiment.getWinnerSegmentsExperienceId(),
				SegmentsExperienceConstants.ID_DEFAULT);

			Layout draftLayout = _layoutLocalService.fetchLayout(
				_portal.getClassNameId(Layout.class.getName()),
				segmentsExperiment.getClassPK());

			if (draftLayout != null) {
				_copySegmentsExperienceData(
					segmentsExperiment.getGroupId(),
					draftLayout.getClassNameId(), draftLayout.getPlid(),
					segmentsExperiment.getWinnerSegmentsExperienceId(),
					SegmentsExperienceConstants.ID_DEFAULT);
			}
		}
		catch (PortalException pe) {
			throw new ModelListenerException(
				"Unable to update segments experiment " +
					segmentsExperiment.getSegmentsExperimentId(),
				pe);
		}
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

	private void _copyPortletPreferences(
		long plid, long sourceSegmentsExperienceId,
		long targetSegmentsExperienceId) {

		List<PortletPreferences> portletPreferencesList =
			_portletPreferencesLocalService.getPortletPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid);

		for (PortletPreferences portletPreferences : portletPreferencesList) {
			Portlet portlet = _portletLocalService.getPortletById(
				portletPreferences.getPortletId());

			if ((portlet == null) || portlet.isUndeployedPortlet()) {
				continue;
			}

			long segmentsExperienceId =
				SegmentsExperiencePortletUtil.getSegmentsExperienceId(
					portletPreferences.getPortletId());

			if (segmentsExperienceId == sourceSegmentsExperienceId) {
				String newPortletId =
					SegmentsExperiencePortletUtil.setSegmentsExperienceId(
						portletPreferences.getPortletId(),
						targetSegmentsExperienceId);

				PortletPreferences existingPortletPreferences =
					_portletPreferencesLocalService.fetchPortletPreferences(
						portletPreferences.getOwnerId(),
						portletPreferences.getOwnerType(), plid, newPortletId);

				if (existingPortletPreferences == null) {
					_portletPreferencesLocalService.addPortletPreferences(
						portletPreferences.getCompanyId(),
						portletPreferences.getOwnerId(),
						portletPreferences.getOwnerType(), plid, newPortletId,
						portlet, portletPreferences.getPreferences());
				}
				else {
					existingPortletPreferences.setPreferences(
						portletPreferences.getPreferences());

					_portletPreferencesLocalService.updatePortletPreferences(
						existingPortletPreferences);
				}
			}
		}
	}

	private void _copySegmentsExperienceData(
			long groupId, long classNameId, long classPK,
			long sourceSegmentsExperienceId, long targetSegmentsExperienceId)
		throws PortalException {

		_addLayoutData(
			groupId, classNameId, classPK, targetSegmentsExperienceId,
			sourceSegmentsExperienceId);

		_updateFragmentEntryLinksEditableValues(
			groupId, classNameId, classPK, targetSegmentsExperienceId,
			sourceSegmentsExperienceId);

		_copyPortletPreferences(
			classPK, sourceSegmentsExperienceId, targetSegmentsExperienceId);
	}

	private boolean _requiresDefaultExperienceReplacement(
		SegmentsExperiment segmentsExperiment) {

		if ((segmentsExperiment.getStatus() ==
				SegmentsExperimentConstants.STATUS_COMPLETED) &&
			(segmentsExperiment.getSegmentsExperienceId() ==
				SegmentsExperienceConstants.ID_DEFAULT) &&
			(segmentsExperiment.getWinnerSegmentsExperienceId() !=
				SegmentsExperienceConstants.ID_DEFAULT)) {

			return true;
		}

		return false;
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

					if (editableKey.startsWith(
							SegmentsExperienceConstants.ID_PREFIX)) {

						JSONObject baseExperienceValueJSONObject =
							editableProcessorJSONObject.getJSONObject(
								SegmentsExperienceConstants.ID_PREFIX +
									baseSegmentsExperienceId);

						editableProcessorJSONObject.put(
							SegmentsExperienceConstants.ID_PREFIX +
								segmentsExperienceId,
							baseExperienceValueJSONObject);

						editableValuesJSONObject.put(
							editableProcessorKey, editableProcessorJSONObject);

						break;
					}

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

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentEntryLinkService _fragmentEntryLinkService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

}