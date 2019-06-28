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

package com.liferay.segments.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperienceModel;
import com.liferay.segments.model.SegmentsExperienceSoap;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model implementation for the SegmentsExperience service. Represents a row in the &quot;SegmentsExperience&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface </code>SegmentsExperienceModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link SegmentsExperienceImpl}.
 * </p>
 *
 * @author Eduardo Garcia
 * @see SegmentsExperienceImpl
 * @generated
 */
@JSON(strict = true)
@ProviderType
public class SegmentsExperienceModelImpl
	extends BaseModelImpl<SegmentsExperience>
	implements SegmentsExperienceModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a segments experience model instance should use the <code>SegmentsExperience</code> interface instead.
	 */
	public static final String TABLE_NAME = "SegmentsExperience";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR}, {"segmentsExperienceId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"segmentsExperienceKey", Types.VARCHAR},
		{"segmentsEntryId", Types.BIGINT}, {"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT}, {"name", Types.VARCHAR},
		{"priority", Types.INTEGER}, {"active_", Types.BOOLEAN},
		{"approved", Types.BOOLEAN}, {"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("segmentsExperienceId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("segmentsExperienceKey", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("segmentsEntryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("priority", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("active_", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("approved", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);
	}

	public static final String TABLE_SQL_CREATE =
		"create table SegmentsExperience (uuid_ VARCHAR(75) null,segmentsExperienceId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,segmentsExperienceKey VARCHAR(75) null,segmentsEntryId LONG,classNameId LONG,classPK LONG,name STRING null,priority INTEGER,active_ BOOLEAN,approved BOOLEAN,lastPublishDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table SegmentsExperience";

	public static final String ORDER_BY_JPQL =
		" ORDER BY segmentsExperience.priority DESC";

	public static final String ORDER_BY_SQL =
		" ORDER BY SegmentsExperience.priority DESC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.segments.service.util.ServiceProps.get(
			"value.object.entity.cache.enabled.com.liferay.segments.model.SegmentsExperience"),
		true);

	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.segments.service.util.ServiceProps.get(
			"value.object.finder.cache.enabled.com.liferay.segments.model.SegmentsExperience"),
		true);

	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(
		com.liferay.segments.service.util.ServiceProps.get(
			"value.object.column.bitmask.enabled.com.liferay.segments.model.SegmentsExperience"),
		true);

	public static final long ACTIVE_COLUMN_BITMASK = 1L;

	public static final long APPROVED_COLUMN_BITMASK = 2L;

	public static final long CLASSNAMEID_COLUMN_BITMASK = 4L;

	public static final long CLASSPK_COLUMN_BITMASK = 8L;

	public static final long COMPANYID_COLUMN_BITMASK = 16L;

	public static final long GROUPID_COLUMN_BITMASK = 32L;

	public static final long PRIORITY_COLUMN_BITMASK = 64L;

	public static final long SEGMENTSENTRYID_COLUMN_BITMASK = 128L;

	public static final long SEGMENTSEXPERIENCEKEY_COLUMN_BITMASK = 256L;

	public static final long UUID_COLUMN_BITMASK = 512L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static SegmentsExperience toModel(SegmentsExperienceSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		SegmentsExperience model = new SegmentsExperienceImpl();

		model.setUuid(soapModel.getUuid());
		model.setSegmentsExperienceId(soapModel.getSegmentsExperienceId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setSegmentsExperienceKey(soapModel.getSegmentsExperienceKey());
		model.setSegmentsEntryId(soapModel.getSegmentsEntryId());
		model.setClassNameId(soapModel.getClassNameId());
		model.setClassPK(soapModel.getClassPK());
		model.setName(soapModel.getName());
		model.setPriority(soapModel.getPriority());
		model.setActive(soapModel.isActive());
		model.setApproved(soapModel.isApproved());
		model.setLastPublishDate(soapModel.getLastPublishDate());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<SegmentsExperience> toModels(
		SegmentsExperienceSoap[] soapModels) {

		if (soapModels == null) {
			return null;
		}

		List<SegmentsExperience> models = new ArrayList<SegmentsExperience>(
			soapModels.length);

		for (SegmentsExperienceSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.segments.service.util.ServiceProps.get(
			"lock.expiration.time.com.liferay.segments.model.SegmentsExperience"));

	public SegmentsExperienceModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _segmentsExperienceId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setSegmentsExperienceId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _segmentsExperienceId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return SegmentsExperience.class;
	}

	@Override
	public String getModelClassName() {
		return SegmentsExperience.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<SegmentsExperience, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<SegmentsExperience, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<SegmentsExperience, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((SegmentsExperience)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<SegmentsExperience, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<SegmentsExperience, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(SegmentsExperience)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<SegmentsExperience, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<SegmentsExperience, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, SegmentsExperience>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			SegmentsExperience.class.getClassLoader(), SegmentsExperience.class,
			ModelWrapper.class);

		try {
			Constructor<SegmentsExperience> constructor =
				(Constructor<SegmentsExperience>)proxyClass.getConstructor(
					InvocationHandler.class);

			return invocationHandler -> {
				try {
					return constructor.newInstance(invocationHandler);
				}
				catch (ReflectiveOperationException roe) {
					throw new InternalError(roe);
				}
			};
		}
		catch (NoSuchMethodException nsme) {
			throw new InternalError(nsme);
		}
	}

	private static final Map<String, Function<SegmentsExperience, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<SegmentsExperience, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<SegmentsExperience, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<SegmentsExperience, Object>>();
		Map<String, BiConsumer<SegmentsExperience, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap<String, BiConsumer<SegmentsExperience, ?>>();

		attributeGetterFunctions.put("uuid", SegmentsExperience::getUuid);
		attributeSetterBiConsumers.put(
			"uuid",
			(BiConsumer<SegmentsExperience, String>)
				SegmentsExperience::setUuid);
		attributeGetterFunctions.put(
			"segmentsExperienceId",
			SegmentsExperience::getSegmentsExperienceId);
		attributeSetterBiConsumers.put(
			"segmentsExperienceId",
			(BiConsumer<SegmentsExperience, Long>)
				SegmentsExperience::setSegmentsExperienceId);
		attributeGetterFunctions.put("groupId", SegmentsExperience::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId",
			(BiConsumer<SegmentsExperience, Long>)
				SegmentsExperience::setGroupId);
		attributeGetterFunctions.put(
			"companyId", SegmentsExperience::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<SegmentsExperience, Long>)
				SegmentsExperience::setCompanyId);
		attributeGetterFunctions.put("userId", SegmentsExperience::getUserId);
		attributeSetterBiConsumers.put(
			"userId",
			(BiConsumer<SegmentsExperience, Long>)
				SegmentsExperience::setUserId);
		attributeGetterFunctions.put(
			"userName", SegmentsExperience::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<SegmentsExperience, String>)
				SegmentsExperience::setUserName);
		attributeGetterFunctions.put(
			"createDate", SegmentsExperience::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<SegmentsExperience, Date>)
				SegmentsExperience::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", SegmentsExperience::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<SegmentsExperience, Date>)
				SegmentsExperience::setModifiedDate);
		attributeGetterFunctions.put(
			"segmentsExperienceKey",
			SegmentsExperience::getSegmentsExperienceKey);
		attributeSetterBiConsumers.put(
			"segmentsExperienceKey",
			(BiConsumer<SegmentsExperience, String>)
				SegmentsExperience::setSegmentsExperienceKey);
		attributeGetterFunctions.put(
			"segmentsEntryId", SegmentsExperience::getSegmentsEntryId);
		attributeSetterBiConsumers.put(
			"segmentsEntryId",
			(BiConsumer<SegmentsExperience, Long>)
				SegmentsExperience::setSegmentsEntryId);
		attributeGetterFunctions.put(
			"classNameId", SegmentsExperience::getClassNameId);
		attributeSetterBiConsumers.put(
			"classNameId",
			(BiConsumer<SegmentsExperience, Long>)
				SegmentsExperience::setClassNameId);
		attributeGetterFunctions.put("classPK", SegmentsExperience::getClassPK);
		attributeSetterBiConsumers.put(
			"classPK",
			(BiConsumer<SegmentsExperience, Long>)
				SegmentsExperience::setClassPK);
		attributeGetterFunctions.put("name", SegmentsExperience::getName);
		attributeSetterBiConsumers.put(
			"name",
			(BiConsumer<SegmentsExperience, String>)
				SegmentsExperience::setName);
		attributeGetterFunctions.put(
			"priority", SegmentsExperience::getPriority);
		attributeSetterBiConsumers.put(
			"priority",
			(BiConsumer<SegmentsExperience, Integer>)
				SegmentsExperience::setPriority);
		attributeGetterFunctions.put("active", SegmentsExperience::getActive);
		attributeSetterBiConsumers.put(
			"active",
			(BiConsumer<SegmentsExperience, Boolean>)
				SegmentsExperience::setActive);
		attributeGetterFunctions.put(
			"approved", SegmentsExperience::getApproved);
		attributeSetterBiConsumers.put(
			"approved",
			(BiConsumer<SegmentsExperience, Boolean>)
				SegmentsExperience::setApproved);
		attributeGetterFunctions.put(
			"lastPublishDate", SegmentsExperience::getLastPublishDate);
		attributeSetterBiConsumers.put(
			"lastPublishDate",
			(BiConsumer<SegmentsExperience, Date>)
				SegmentsExperience::setLastPublishDate);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@JSON
	@Override
	public String getUuid() {
		if (_uuid == null) {
			return "";
		}
		else {
			return _uuid;
		}
	}

	@Override
	public void setUuid(String uuid) {
		_columnBitmask |= UUID_COLUMN_BITMASK;

		if (_originalUuid == null) {
			_originalUuid = _uuid;
		}

		_uuid = uuid;
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	@JSON
	@Override
	public long getSegmentsExperienceId() {
		return _segmentsExperienceId;
	}

	@Override
	public void setSegmentsExperienceId(long segmentsExperienceId) {
		_segmentsExperienceId = segmentsExperienceId;
	}

	@JSON
	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		_columnBitmask |= GROUPID_COLUMN_BITMASK;

		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = _groupId;
		}

		_groupId = groupId;
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
	}

	@JSON
	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_columnBitmask |= COMPANYID_COLUMN_BITMASK;

		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = _companyId;
		}

		_companyId = companyId;
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	@JSON
	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	@JSON
	@Override
	public String getUserName() {
		if (_userName == null) {
			return "";
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;
	}

	@JSON
	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@JSON
	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		_modifiedDate = modifiedDate;
	}

	@JSON
	@Override
	public String getSegmentsExperienceKey() {
		if (_segmentsExperienceKey == null) {
			return "";
		}
		else {
			return _segmentsExperienceKey;
		}
	}

	@Override
	public void setSegmentsExperienceKey(String segmentsExperienceKey) {
		_columnBitmask |= SEGMENTSEXPERIENCEKEY_COLUMN_BITMASK;

		if (_originalSegmentsExperienceKey == null) {
			_originalSegmentsExperienceKey = _segmentsExperienceKey;
		}

		_segmentsExperienceKey = segmentsExperienceKey;
	}

	public String getOriginalSegmentsExperienceKey() {
		return GetterUtil.getString(_originalSegmentsExperienceKey);
	}

	@JSON
	@Override
	public long getSegmentsEntryId() {
		return _segmentsEntryId;
	}

	@Override
	public void setSegmentsEntryId(long segmentsEntryId) {
		_columnBitmask |= SEGMENTSENTRYID_COLUMN_BITMASK;

		if (!_setOriginalSegmentsEntryId) {
			_setOriginalSegmentsEntryId = true;

			_originalSegmentsEntryId = _segmentsEntryId;
		}

		_segmentsEntryId = segmentsEntryId;
	}

	public long getOriginalSegmentsEntryId() {
		return _originalSegmentsEntryId;
	}

	@Override
	public String getClassName() {
		if (getClassNameId() <= 0) {
			return "";
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	@Override
	public void setClassName(String className) {
		long classNameId = 0;

		if (Validator.isNotNull(className)) {
			classNameId = PortalUtil.getClassNameId(className);
		}

		setClassNameId(classNameId);
	}

	@JSON
	@Override
	public long getClassNameId() {
		return _classNameId;
	}

	@Override
	public void setClassNameId(long classNameId) {
		_columnBitmask |= CLASSNAMEID_COLUMN_BITMASK;

		if (!_setOriginalClassNameId) {
			_setOriginalClassNameId = true;

			_originalClassNameId = _classNameId;
		}

		_classNameId = classNameId;
	}

	public long getOriginalClassNameId() {
		return _originalClassNameId;
	}

	@JSON
	@Override
	public long getClassPK() {
		return _classPK;
	}

	@Override
	public void setClassPK(long classPK) {
		_columnBitmask |= CLASSPK_COLUMN_BITMASK;

		if (!_setOriginalClassPK) {
			_setOriginalClassPK = true;

			_originalClassPK = _classPK;
		}

		_classPK = classPK;
	}

	public long getOriginalClassPK() {
		return _originalClassPK;
	}

	@JSON
	@Override
	public String getName() {
		if (_name == null) {
			return "";
		}
		else {
			return _name;
		}
	}

	@Override
	public String getName(Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getName(languageId);
	}

	@Override
	public String getName(Locale locale, boolean useDefault) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getName(languageId, useDefault);
	}

	@Override
	public String getName(String languageId) {
		return LocalizationUtil.getLocalization(getName(), languageId);
	}

	@Override
	public String getName(String languageId, boolean useDefault) {
		return LocalizationUtil.getLocalization(
			getName(), languageId, useDefault);
	}

	@Override
	public String getNameCurrentLanguageId() {
		return _nameCurrentLanguageId;
	}

	@JSON
	@Override
	public String getNameCurrentValue() {
		Locale locale = getLocale(_nameCurrentLanguageId);

		return getName(locale);
	}

	@Override
	public Map<Locale, String> getNameMap() {
		return LocalizationUtil.getLocalizationMap(getName());
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public void setName(String name, Locale locale) {
		setName(name, locale, LocaleUtil.getSiteDefault());
	}

	@Override
	public void setName(String name, Locale locale, Locale defaultLocale) {
		String languageId = LocaleUtil.toLanguageId(locale);
		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		if (Validator.isNotNull(name)) {
			setName(
				LocalizationUtil.updateLocalization(
					getName(), "Name", name, languageId, defaultLanguageId));
		}
		else {
			setName(
				LocalizationUtil.removeLocalization(
					getName(), "Name", languageId));
		}
	}

	@Override
	public void setNameCurrentLanguageId(String languageId) {
		_nameCurrentLanguageId = languageId;
	}

	@Override
	public void setNameMap(Map<Locale, String> nameMap) {
		setNameMap(nameMap, LocaleUtil.getSiteDefault());
	}

	@Override
	public void setNameMap(Map<Locale, String> nameMap, Locale defaultLocale) {
		if (nameMap == null) {
			return;
		}

		setName(
			LocalizationUtil.updateLocalization(
				nameMap, getName(), "Name",
				LocaleUtil.toLanguageId(defaultLocale)));
	}

	@JSON
	@Override
	public int getPriority() {
		return _priority;
	}

	@Override
	public void setPriority(int priority) {
		_columnBitmask = -1L;

		if (!_setOriginalPriority) {
			_setOriginalPriority = true;

			_originalPriority = _priority;
		}

		_priority = priority;
	}

	public int getOriginalPriority() {
		return _originalPriority;
	}

	@JSON
	@Override
	public boolean getActive() {
		return _active;
	}

	@JSON
	@Override
	public boolean isActive() {
		return _active;
	}

	@Override
	public void setActive(boolean active) {
		_columnBitmask |= ACTIVE_COLUMN_BITMASK;

		if (!_setOriginalActive) {
			_setOriginalActive = true;

			_originalActive = _active;
		}

		_active = active;
	}

	public boolean getOriginalActive() {
		return _originalActive;
	}

	@JSON
	@Override
	public boolean getApproved() {
		return _approved;
	}

	@JSON
	@Override
	public boolean isApproved() {
		return _approved;
	}

	@Override
	public void setApproved(boolean approved) {
		_columnBitmask |= APPROVED_COLUMN_BITMASK;

		if (!_setOriginalApproved) {
			_setOriginalApproved = true;

			_originalApproved = _approved;
		}

		_approved = approved;
	}

	public boolean getOriginalApproved() {
		return _originalApproved;
	}

	@JSON
	@Override
	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(
			PortalUtil.getClassNameId(SegmentsExperience.class.getName()),
			getClassNameId());
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), SegmentsExperience.class.getName(),
			getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public String[] getAvailableLanguageIds() {
		Set<String> availableLanguageIds = new TreeSet<String>();

		Map<Locale, String> nameMap = getNameMap();

		for (Map.Entry<Locale, String> entry : nameMap.entrySet()) {
			Locale locale = entry.getKey();
			String value = entry.getValue();

			if (Validator.isNotNull(value)) {
				availableLanguageIds.add(LocaleUtil.toLanguageId(locale));
			}
		}

		return availableLanguageIds.toArray(
			new String[availableLanguageIds.size()]);
	}

	@Override
	public String getDefaultLanguageId() {
		String xml = getName();

		if (xml == null) {
			return "";
		}

		Locale defaultLocale = LocaleUtil.getSiteDefault();

		return LocalizationUtil.getDefaultLanguageId(xml, defaultLocale);
	}

	@Override
	public void prepareLocalizedFieldsForImport() throws LocaleException {
		Locale defaultLocale = LocaleUtil.fromLanguageId(
			getDefaultLanguageId());

		Locale[] availableLocales = LocaleUtil.fromLanguageIds(
			getAvailableLanguageIds());

		Locale defaultImportLocale = LocalizationUtil.getDefaultImportLocale(
			SegmentsExperience.class.getName(), getPrimaryKey(), defaultLocale,
			availableLocales);

		prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	@SuppressWarnings("unused")
	public void prepareLocalizedFieldsForImport(Locale defaultImportLocale)
		throws LocaleException {

		Locale defaultLocale = LocaleUtil.getSiteDefault();

		String modelDefaultLanguageId = getDefaultLanguageId();

		String name = getName(defaultLocale);

		if (Validator.isNull(name)) {
			setName(getName(modelDefaultLanguageId), defaultLocale);
		}
		else {
			setName(getName(defaultLocale), defaultLocale, defaultLocale);
		}
	}

	@Override
	public SegmentsExperience toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, SegmentsExperience>
				escapedModelProxyProviderFunction =
					EscapedModelProxyProviderFunctionHolder.
						_escapedModelProxyProviderFunction;

			_escapedModel = escapedModelProxyProviderFunction.apply(
				new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		SegmentsExperienceImpl segmentsExperienceImpl =
			new SegmentsExperienceImpl();

		segmentsExperienceImpl.setUuid(getUuid());
		segmentsExperienceImpl.setSegmentsExperienceId(
			getSegmentsExperienceId());
		segmentsExperienceImpl.setGroupId(getGroupId());
		segmentsExperienceImpl.setCompanyId(getCompanyId());
		segmentsExperienceImpl.setUserId(getUserId());
		segmentsExperienceImpl.setUserName(getUserName());
		segmentsExperienceImpl.setCreateDate(getCreateDate());
		segmentsExperienceImpl.setModifiedDate(getModifiedDate());
		segmentsExperienceImpl.setSegmentsExperienceKey(
			getSegmentsExperienceKey());
		segmentsExperienceImpl.setSegmentsEntryId(getSegmentsEntryId());
		segmentsExperienceImpl.setClassNameId(getClassNameId());
		segmentsExperienceImpl.setClassPK(getClassPK());
		segmentsExperienceImpl.setName(getName());
		segmentsExperienceImpl.setPriority(getPriority());
		segmentsExperienceImpl.setActive(isActive());
		segmentsExperienceImpl.setApproved(isApproved());
		segmentsExperienceImpl.setLastPublishDate(getLastPublishDate());

		segmentsExperienceImpl.resetOriginalValues();

		return segmentsExperienceImpl;
	}

	@Override
	public int compareTo(SegmentsExperience segmentsExperience) {
		int value = 0;

		if (getPriority() < segmentsExperience.getPriority()) {
			value = -1;
		}
		else if (getPriority() > segmentsExperience.getPriority()) {
			value = 1;
		}
		else {
			value = 0;
		}

		value = value * -1;

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SegmentsExperience)) {
			return false;
		}

		SegmentsExperience segmentsExperience = (SegmentsExperience)obj;

		long primaryKey = segmentsExperience.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		SegmentsExperienceModelImpl segmentsExperienceModelImpl = this;

		segmentsExperienceModelImpl._originalUuid =
			segmentsExperienceModelImpl._uuid;

		segmentsExperienceModelImpl._originalGroupId =
			segmentsExperienceModelImpl._groupId;

		segmentsExperienceModelImpl._setOriginalGroupId = false;

		segmentsExperienceModelImpl._originalCompanyId =
			segmentsExperienceModelImpl._companyId;

		segmentsExperienceModelImpl._setOriginalCompanyId = false;

		segmentsExperienceModelImpl._setModifiedDate = false;

		segmentsExperienceModelImpl._originalSegmentsExperienceKey =
			segmentsExperienceModelImpl._segmentsExperienceKey;

		segmentsExperienceModelImpl._originalSegmentsEntryId =
			segmentsExperienceModelImpl._segmentsEntryId;

		segmentsExperienceModelImpl._setOriginalSegmentsEntryId = false;

		segmentsExperienceModelImpl._originalClassNameId =
			segmentsExperienceModelImpl._classNameId;

		segmentsExperienceModelImpl._setOriginalClassNameId = false;

		segmentsExperienceModelImpl._originalClassPK =
			segmentsExperienceModelImpl._classPK;

		segmentsExperienceModelImpl._setOriginalClassPK = false;

		segmentsExperienceModelImpl._originalPriority =
			segmentsExperienceModelImpl._priority;

		segmentsExperienceModelImpl._setOriginalPriority = false;

		segmentsExperienceModelImpl._originalActive =
			segmentsExperienceModelImpl._active;

		segmentsExperienceModelImpl._setOriginalActive = false;

		segmentsExperienceModelImpl._originalApproved =
			segmentsExperienceModelImpl._approved;

		segmentsExperienceModelImpl._setOriginalApproved = false;

		segmentsExperienceModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<SegmentsExperience> toCacheModel() {
		SegmentsExperienceCacheModel segmentsExperienceCacheModel =
			new SegmentsExperienceCacheModel();

		segmentsExperienceCacheModel.uuid = getUuid();

		String uuid = segmentsExperienceCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			segmentsExperienceCacheModel.uuid = null;
		}

		segmentsExperienceCacheModel.segmentsExperienceId =
			getSegmentsExperienceId();

		segmentsExperienceCacheModel.groupId = getGroupId();

		segmentsExperienceCacheModel.companyId = getCompanyId();

		segmentsExperienceCacheModel.userId = getUserId();

		segmentsExperienceCacheModel.userName = getUserName();

		String userName = segmentsExperienceCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			segmentsExperienceCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			segmentsExperienceCacheModel.createDate = createDate.getTime();
		}
		else {
			segmentsExperienceCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			segmentsExperienceCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			segmentsExperienceCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		segmentsExperienceCacheModel.segmentsExperienceKey =
			getSegmentsExperienceKey();

		String segmentsExperienceKey =
			segmentsExperienceCacheModel.segmentsExperienceKey;

		if ((segmentsExperienceKey != null) &&
			(segmentsExperienceKey.length() == 0)) {

			segmentsExperienceCacheModel.segmentsExperienceKey = null;
		}

		segmentsExperienceCacheModel.segmentsEntryId = getSegmentsEntryId();

		segmentsExperienceCacheModel.classNameId = getClassNameId();

		segmentsExperienceCacheModel.classPK = getClassPK();

		segmentsExperienceCacheModel.name = getName();

		String name = segmentsExperienceCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			segmentsExperienceCacheModel.name = null;
		}

		segmentsExperienceCacheModel.priority = getPriority();

		segmentsExperienceCacheModel.active = isActive();

		segmentsExperienceCacheModel.approved = isApproved();

		Date lastPublishDate = getLastPublishDate();

		if (lastPublishDate != null) {
			segmentsExperienceCacheModel.lastPublishDate =
				lastPublishDate.getTime();
		}
		else {
			segmentsExperienceCacheModel.lastPublishDate = Long.MIN_VALUE;
		}

		return segmentsExperienceCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<SegmentsExperience, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<SegmentsExperience, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<SegmentsExperience, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((SegmentsExperience)this));
			sb.append(", ");
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		Map<String, Function<SegmentsExperience, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<SegmentsExperience, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<SegmentsExperience, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((SegmentsExperience)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, SegmentsExperience>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private String _uuid;
	private String _originalUuid;
	private long _segmentsExperienceId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private String _segmentsExperienceKey;
	private String _originalSegmentsExperienceKey;
	private long _segmentsEntryId;
	private long _originalSegmentsEntryId;
	private boolean _setOriginalSegmentsEntryId;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private long _classPK;
	private long _originalClassPK;
	private boolean _setOriginalClassPK;
	private String _name;
	private String _nameCurrentLanguageId;
	private int _priority;
	private int _originalPriority;
	private boolean _setOriginalPriority;
	private boolean _active;
	private boolean _originalActive;
	private boolean _setOriginalActive;
	private boolean _approved;
	private boolean _originalApproved;
	private boolean _setOriginalApproved;
	private Date _lastPublishDate;
	private long _columnBitmask;
	private SegmentsExperience _escapedModel;

}