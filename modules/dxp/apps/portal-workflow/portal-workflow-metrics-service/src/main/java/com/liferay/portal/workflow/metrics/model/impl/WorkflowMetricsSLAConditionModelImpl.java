/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACondition;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLAConditionModel;

import java.io.Serializable;

import java.sql.Types;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the WorkflowMetricsSLACondition service. Represents a row in the &quot;WorkflowMetricsSLACondition&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface </code>WorkflowMetricsSLAConditionModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link WorkflowMetricsSLAConditionImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowMetricsSLAConditionImpl
 * @generated
 */
@ProviderType
public class WorkflowMetricsSLAConditionModelImpl
	extends BaseModelImpl<WorkflowMetricsSLACondition>
	implements WorkflowMetricsSLAConditionModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a workflow metrics sla condition model instance should use the <code>WorkflowMetricsSLACondition</code> interface instead.
	 */
	public static final String TABLE_NAME = "WorkflowMetricsSLACondition";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"uuid_", Types.VARCHAR},
		{"workflowMetricsSLAConditionId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"workflowMetricsSLADefinitionId", Types.BIGINT}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("workflowMetricsSLAConditionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("workflowMetricsSLADefinitionId", Types.BIGINT);
	}

	public static final String TABLE_SQL_CREATE =
		"create table WorkflowMetricsSLACondition (mvccVersion LONG default 0 not null,uuid_ VARCHAR(75) null,workflowMetricsSLAConditionId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,workflowMetricsSLADefinitionId LONG)";

	public static final String TABLE_SQL_DROP =
		"drop table WorkflowMetricsSLACondition";

	public static final String ORDER_BY_JPQL =
		" ORDER BY workflowMetricsSLACondition.workflowMetricsSLAConditionId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY WorkflowMetricsSLACondition.workflowMetricsSLAConditionId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.workflow.metrics.service.util.ServiceProps.get(
			"value.object.entity.cache.enabled.com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACondition"),
		true);

	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.workflow.metrics.service.util.ServiceProps.get(
			"value.object.finder.cache.enabled.com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACondition"),
		true);

	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.workflow.metrics.service.util.ServiceProps.get(
			"value.object.column.bitmask.enabled.com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACondition"),
		true);

	public static final long COMPANYID_COLUMN_BITMASK = 1L;

	public static final long GROUPID_COLUMN_BITMASK = 2L;

	public static final long UUID_COLUMN_BITMASK = 4L;

	public static final long WORKFLOWMETRICSSLADEFINITIONID_COLUMN_BITMASK = 8L;

	public static final long WORKFLOWMETRICSSLACONDITIONID_COLUMN_BITMASK = 16L;

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.portal.workflow.metrics.service.util.ServiceProps.get(
			"lock.expiration.time.com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACondition"));

	public WorkflowMetricsSLAConditionModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _workflowMetricsSLAConditionId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setWorkflowMetricsSLAConditionId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _workflowMetricsSLAConditionId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return WorkflowMetricsSLACondition.class;
	}

	@Override
	public String getModelClassName() {
		return WorkflowMetricsSLACondition.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<WorkflowMetricsSLACondition, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<WorkflowMetricsSLACondition, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<WorkflowMetricsSLACondition, Object>
				attributeGetterFunction = entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply(
					(WorkflowMetricsSLACondition)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<WorkflowMetricsSLACondition, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<WorkflowMetricsSLACondition, Object>
				attributeSetterBiConsumer = attributeSetterBiConsumers.get(
					attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(WorkflowMetricsSLACondition)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<WorkflowMetricsSLACondition, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<WorkflowMetricsSLACondition, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static final Map
		<String, Function<WorkflowMetricsSLACondition, Object>>
			_attributeGetterFunctions;
	private static final Map
		<String, BiConsumer<WorkflowMetricsSLACondition, Object>>
			_attributeSetterBiConsumers;

	static {
		Map<String, Function<WorkflowMetricsSLACondition, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<WorkflowMetricsSLACondition, Object>>();
		Map<String, BiConsumer<WorkflowMetricsSLACondition, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap
					<String, BiConsumer<WorkflowMetricsSLACondition, ?>>();

		attributeGetterFunctions.put(
			"mvccVersion", WorkflowMetricsSLACondition::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<WorkflowMetricsSLACondition, Long>)
				WorkflowMetricsSLACondition::setMvccVersion);
		attributeGetterFunctions.put(
			"uuid", WorkflowMetricsSLACondition::getUuid);
		attributeSetterBiConsumers.put(
			"uuid",
			(BiConsumer<WorkflowMetricsSLACondition, String>)
				WorkflowMetricsSLACondition::setUuid);
		attributeGetterFunctions.put(
			"workflowMetricsSLAConditionId",
			WorkflowMetricsSLACondition::getWorkflowMetricsSLAConditionId);
		attributeSetterBiConsumers.put(
			"workflowMetricsSLAConditionId",
			(BiConsumer<WorkflowMetricsSLACondition, Long>)
				WorkflowMetricsSLACondition::setWorkflowMetricsSLAConditionId);
		attributeGetterFunctions.put(
			"groupId", WorkflowMetricsSLACondition::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId",
			(BiConsumer<WorkflowMetricsSLACondition, Long>)
				WorkflowMetricsSLACondition::setGroupId);
		attributeGetterFunctions.put(
			"companyId", WorkflowMetricsSLACondition::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<WorkflowMetricsSLACondition, Long>)
				WorkflowMetricsSLACondition::setCompanyId);
		attributeGetterFunctions.put(
			"userId", WorkflowMetricsSLACondition::getUserId);
		attributeSetterBiConsumers.put(
			"userId",
			(BiConsumer<WorkflowMetricsSLACondition, Long>)
				WorkflowMetricsSLACondition::setUserId);
		attributeGetterFunctions.put(
			"userName", WorkflowMetricsSLACondition::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<WorkflowMetricsSLACondition, String>)
				WorkflowMetricsSLACondition::setUserName);
		attributeGetterFunctions.put(
			"createDate", WorkflowMetricsSLACondition::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<WorkflowMetricsSLACondition, Date>)
				WorkflowMetricsSLACondition::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", WorkflowMetricsSLACondition::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<WorkflowMetricsSLACondition, Date>)
				WorkflowMetricsSLACondition::setModifiedDate);
		attributeGetterFunctions.put(
			"workflowMetricsSLADefinitionId",
			WorkflowMetricsSLACondition::getWorkflowMetricsSLADefinitionId);
		attributeSetterBiConsumers.put(
			"workflowMetricsSLADefinitionId",
			(BiConsumer<WorkflowMetricsSLACondition, Long>)
				WorkflowMetricsSLACondition::setWorkflowMetricsSLADefinitionId);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@Override
	public long getMvccVersion() {
		return _mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

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

	@Override
	public long getWorkflowMetricsSLAConditionId() {
		return _workflowMetricsSLAConditionId;
	}

	@Override
	public void setWorkflowMetricsSLAConditionId(
		long workflowMetricsSLAConditionId) {

		_workflowMetricsSLAConditionId = workflowMetricsSLAConditionId;
	}

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

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

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

	@Override
	public long getWorkflowMetricsSLADefinitionId() {
		return _workflowMetricsSLADefinitionId;
	}

	@Override
	public void setWorkflowMetricsSLADefinitionId(
		long workflowMetricsSLADefinitionId) {

		_columnBitmask |= WORKFLOWMETRICSSLADEFINITIONID_COLUMN_BITMASK;

		if (!_setOriginalWorkflowMetricsSLADefinitionId) {
			_setOriginalWorkflowMetricsSLADefinitionId = true;

			_originalWorkflowMetricsSLADefinitionId =
				_workflowMetricsSLADefinitionId;
		}

		_workflowMetricsSLADefinitionId = workflowMetricsSLADefinitionId;
	}

	public long getOriginalWorkflowMetricsSLADefinitionId() {
		return _originalWorkflowMetricsSLADefinitionId;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(
			PortalUtil.getClassNameId(
				WorkflowMetricsSLACondition.class.getName()));
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), WorkflowMetricsSLACondition.class.getName(),
			getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public WorkflowMetricsSLACondition toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel =
				(WorkflowMetricsSLACondition)ProxyUtil.newProxyInstance(
					_classLoader, _escapedModelInterfaces,
					new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		WorkflowMetricsSLAConditionImpl workflowMetricsSLAConditionImpl =
			new WorkflowMetricsSLAConditionImpl();

		workflowMetricsSLAConditionImpl.setMvccVersion(getMvccVersion());
		workflowMetricsSLAConditionImpl.setUuid(getUuid());
		workflowMetricsSLAConditionImpl.setWorkflowMetricsSLAConditionId(
			getWorkflowMetricsSLAConditionId());
		workflowMetricsSLAConditionImpl.setGroupId(getGroupId());
		workflowMetricsSLAConditionImpl.setCompanyId(getCompanyId());
		workflowMetricsSLAConditionImpl.setUserId(getUserId());
		workflowMetricsSLAConditionImpl.setUserName(getUserName());
		workflowMetricsSLAConditionImpl.setCreateDate(getCreateDate());
		workflowMetricsSLAConditionImpl.setModifiedDate(getModifiedDate());
		workflowMetricsSLAConditionImpl.setWorkflowMetricsSLADefinitionId(
			getWorkflowMetricsSLADefinitionId());

		workflowMetricsSLAConditionImpl.resetOriginalValues();

		return workflowMetricsSLAConditionImpl;
	}

	@Override
	public int compareTo(
		WorkflowMetricsSLACondition workflowMetricsSLACondition) {

		long primaryKey = workflowMetricsSLACondition.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof WorkflowMetricsSLACondition)) {
			return false;
		}

		WorkflowMetricsSLACondition workflowMetricsSLACondition =
			(WorkflowMetricsSLACondition)obj;

		long primaryKey = workflowMetricsSLACondition.getPrimaryKey();

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
		WorkflowMetricsSLAConditionModelImpl
			workflowMetricsSLAConditionModelImpl = this;

		workflowMetricsSLAConditionModelImpl._originalUuid =
			workflowMetricsSLAConditionModelImpl._uuid;

		workflowMetricsSLAConditionModelImpl._originalGroupId =
			workflowMetricsSLAConditionModelImpl._groupId;

		workflowMetricsSLAConditionModelImpl._setOriginalGroupId = false;

		workflowMetricsSLAConditionModelImpl._originalCompanyId =
			workflowMetricsSLAConditionModelImpl._companyId;

		workflowMetricsSLAConditionModelImpl._setOriginalCompanyId = false;

		workflowMetricsSLAConditionModelImpl._setModifiedDate = false;

		workflowMetricsSLAConditionModelImpl.
			_originalWorkflowMetricsSLADefinitionId =
				workflowMetricsSLAConditionModelImpl.
					_workflowMetricsSLADefinitionId;

		workflowMetricsSLAConditionModelImpl.
			_setOriginalWorkflowMetricsSLADefinitionId = false;

		workflowMetricsSLAConditionModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<WorkflowMetricsSLACondition> toCacheModel() {
		WorkflowMetricsSLAConditionCacheModel
			workflowMetricsSLAConditionCacheModel =
				new WorkflowMetricsSLAConditionCacheModel();

		workflowMetricsSLAConditionCacheModel.mvccVersion = getMvccVersion();

		workflowMetricsSLAConditionCacheModel.uuid = getUuid();

		String uuid = workflowMetricsSLAConditionCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			workflowMetricsSLAConditionCacheModel.uuid = null;
		}

		workflowMetricsSLAConditionCacheModel.workflowMetricsSLAConditionId =
			getWorkflowMetricsSLAConditionId();

		workflowMetricsSLAConditionCacheModel.groupId = getGroupId();

		workflowMetricsSLAConditionCacheModel.companyId = getCompanyId();

		workflowMetricsSLAConditionCacheModel.userId = getUserId();

		workflowMetricsSLAConditionCacheModel.userName = getUserName();

		String userName = workflowMetricsSLAConditionCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			workflowMetricsSLAConditionCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			workflowMetricsSLAConditionCacheModel.createDate =
				createDate.getTime();
		}
		else {
			workflowMetricsSLAConditionCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			workflowMetricsSLAConditionCacheModel.modifiedDate =
				modifiedDate.getTime();
		}
		else {
			workflowMetricsSLAConditionCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		workflowMetricsSLAConditionCacheModel.workflowMetricsSLADefinitionId =
			getWorkflowMetricsSLADefinitionId();

		return workflowMetricsSLAConditionCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<WorkflowMetricsSLACondition, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<WorkflowMetricsSLACondition, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<WorkflowMetricsSLACondition, Object>
				attributeGetterFunction = entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(
				attributeGetterFunction.apply(
					(WorkflowMetricsSLACondition)this));
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
		Map<String, Function<WorkflowMetricsSLACondition, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<WorkflowMetricsSLACondition, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<WorkflowMetricsSLACondition, Object>
				attributeGetterFunction = entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(
				attributeGetterFunction.apply(
					(WorkflowMetricsSLACondition)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader =
		WorkflowMetricsSLACondition.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
		WorkflowMetricsSLACondition.class, ModelWrapper.class
	};

	private long _mvccVersion;
	private String _uuid;
	private String _originalUuid;
	private long _workflowMetricsSLAConditionId;
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
	private long _workflowMetricsSLADefinitionId;
	private long _originalWorkflowMetricsSLADefinitionId;
	private boolean _setOriginalWorkflowMetricsSLADefinitionId;
	private long _columnBitmask;
	private WorkflowMetricsSLACondition _escapedModel;

}