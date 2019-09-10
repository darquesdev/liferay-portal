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
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.service.SegmentsExperimentRelService;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(
	immediate = true, service = AddSegmentsExperienceMVCCommandHelper.class
)
public class AddSegmentsExperienceMVCCommandHelper {

	protected Map<Long, String> copyFragmentEntryLinksEditableValues(
			long groupId, long classNameId, long classPK,
			long sourceSegmentsExperienceId, long targetSegmentsExperienceId)
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
									sourceSegmentsExperienceId);

						editableProcessorJSONObject.put(
							SegmentsExperienceConstants.ID_PREFIX +
								targetSegmentsExperienceId,
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
								sourceSegmentsExperienceId)) {

						valueJSONObject = editableJSONObject.getJSONObject(
							SegmentsExperienceConstants.ID_PREFIX +
								sourceSegmentsExperienceId);
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
							targetSegmentsExperienceId,
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

	protected String copyLayoutData(
			long groupId, long classNameId, long classPK,
			long sourceSegmentsExperienceId, long targetSegmentsExperienceId)
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureService.
				fetchLayoutPageTemplateStructure(
					groupId, classNameId, classPK, true);

		String data = layoutPageTemplateStructure.getData(
			sourceSegmentsExperienceId);

		_layoutPageTemplateStructureService.updateLayoutPageTemplateStructure(
			groupId, classNameId, classPK, targetSegmentsExperienceId, data);

		return data;
	}

	protected void copySegmentsExperienceData(
			long groupId, long classNameId, long classPK,
			long sourceSegmentsExperienceId, long targetSegmentsExperienceId)
		throws PortalException {

		copyLayoutData(
			groupId, classNameId, classPK, sourceSegmentsExperienceId,
			targetSegmentsExperienceId);

		copyFragmentEntryLinksEditableValues(
			groupId, classNameId, classPK, sourceSegmentsExperienceId,
			targetSegmentsExperienceId);
	}

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentEntryLinkService _fragmentEntryLinkService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureService;

	@Reference
	private SegmentsExperimentRelService _segmentsExperimentRelService;

}