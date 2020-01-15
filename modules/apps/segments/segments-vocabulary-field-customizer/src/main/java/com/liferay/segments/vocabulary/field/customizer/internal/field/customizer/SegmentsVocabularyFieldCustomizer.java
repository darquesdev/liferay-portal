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

package com.liferay.segments.vocabulary.field.customizer.internal.field.customizer;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.segments.field.Field;
import com.liferay.segments.field.customizer.SegmentsFieldCustomizer;
import com.liferay.segments.vocabulary.field.customizer.internal.configuration.SegmentsVocabularyFieldCustomizerConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.segments.vocabulary.field.customizer.internal.configuration.SegmentsVocabularyFieldCustomizerConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	property = "segments.field.customizer.key=" + SegmentsVocabularyFieldCustomizer.KEY,
	service = SegmentsFieldCustomizer.class
)
public class SegmentsVocabularyFieldCustomizer
	implements SegmentsFieldCustomizer {

	public static final String KEY = "vocabulary";

	@Override
	public List<String> getFieldNames() {
		return _fieldNames;
	}

	public String getFieldValueName(String fieldValue, Locale locale) {
		return fieldValue;
	}

	@Override
	public String getKey() {
		return KEY;
	}

	public String getLabel(String fieldName, Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		String label = LanguageUtil.get(
			resourceBundle, "field." + CamelCaseUtil.fromCamelCase(fieldName));

		if (label.startsWith(_DYNAMIC_FIELD_PREFIX)) {
			label = label.substring(_DYNAMIC_FIELD_PREFIX.length());

			label = LanguageUtil.format(
				resourceBundle, "request-parameter-x", label);
		}

		return label;
	}

	public List<Field.Option> getOptions(Locale locale) {
		Long companyId = CompanyThreadLocal.getCompanyId();

		if (companyId == null) {
			return Collections.emptyList();
		}

		Group companyGroup = _groupLocalService.fetchCompanyGroup(companyId);

		AssetVocabulary vocabulary =
			_assetVocabularyLocalService.fetchGroupVocabulary(
				companyGroup.getGroupId(),
				_segmentsVocabularyFieldCustomizerConfiguration.
					vocabularyName());

		if (vocabulary == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"No vocabulary was found with name ",
						_segmentsVocabularyFieldCustomizerConfiguration.
							vocabularyName(),
						" in company ", String.valueOf(companyId)));
			}

			return Collections.emptyList();
		}

		List<AssetCategory> categories = vocabulary.getCategories();

		List<Field.Option> options = new ArrayList<>();

		for (AssetCategory category : categories) {
			Field.Option option = new Field.Option(
				category.getTitle(locale), category.getName());

			options.add(option);
		}

		return options;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_segmentsVocabularyFieldCustomizerConfiguration =
			ConfigurableUtil.createConfigurable(
				SegmentsVocabularyFieldCustomizerConfiguration.class,
				properties);

		_fieldNames = ListUtil.fromArray(
			_segmentsVocabularyFieldCustomizerConfiguration.fieldName());
	}

	private static final String _DYNAMIC_FIELD_PREFIX = "field.custom-context/";

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsVocabularyFieldCustomizer.class);

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	private volatile List<String> _fieldNames;

	@Reference
	private GroupLocalService _groupLocalService;

	private volatile SegmentsVocabularyFieldCustomizerConfiguration
		_segmentsVocabularyFieldCustomizerConfiguration;

}