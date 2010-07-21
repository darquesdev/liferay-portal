/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.model;

import com.liferay.portal.kernel.annotation.AutoEscape;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

/**
 * The base model interface for the SocialEquitySetting service. Represents a row in the &quot;SocialEquitySetting&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portlet.social.model.impl.SocialEquitySettingModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.portlet.social.model.impl.SocialEquitySettingImpl}.
 * </p>
 *
 * <p>
 * Never modify or reference this interface directly. All methods that expect a social equity setting model instance should use the {@link SocialEquitySetting} interface instead.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialEquitySetting
 * @see com.liferay.portlet.social.model.impl.SocialEquitySettingImpl
 * @see com.liferay.portlet.social.model.impl.SocialEquitySettingModelImpl
 * @generated
 */
public interface SocialEquitySettingModel extends BaseModel<SocialEquitySetting> {
	/**
	 * Gets the primary key of this social equity setting.
	 *
	 * @return the primary key of this social equity setting
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this social equity setting
	 *
	 * @param pk the primary key of this social equity setting
	 */
	public void setPrimaryKey(long pk);

	/**
	 * Gets the equity setting id of this social equity setting.
	 *
	 * @return the equity setting id of this social equity setting
	 */
	public long getEquitySettingId();

	/**
	 * Sets the equity setting id of this social equity setting.
	 *
	 * @param equitySettingId the equity setting id of this social equity setting
	 */
	public void setEquitySettingId(long equitySettingId);

	/**
	 * Gets the group id of this social equity setting.
	 *
	 * @return the group id of this social equity setting
	 */
	public long getGroupId();

	/**
	 * Sets the group id of this social equity setting.
	 *
	 * @param groupId the group id of this social equity setting
	 */
	public void setGroupId(long groupId);

	/**
	 * Gets the company id of this social equity setting.
	 *
	 * @return the company id of this social equity setting
	 */
	public long getCompanyId();

	/**
	 * Sets the company id of this social equity setting.
	 *
	 * @param companyId the company id of this social equity setting
	 */
	public void setCompanyId(long companyId);

	/**
	 * Gets the class name of the model instance this social equity setting is polymorphically associated with.
	 *
	 * @return the class name of the model instance this social equity setting is polymorphically associated with
	 */
	public String getClassName();

	/**
	 * Gets the class name id of this social equity setting.
	 *
	 * @return the class name id of this social equity setting
	 */
	public long getClassNameId();

	/**
	 * Sets the class name id of this social equity setting.
	 *
	 * @param classNameId the class name id of this social equity setting
	 */
	public void setClassNameId(long classNameId);

	/**
	 * Gets the action id of this social equity setting.
	 *
	 * @return the action id of this social equity setting
	 */
	@AutoEscape
	public String getActionId();

	/**
	 * Sets the action id of this social equity setting.
	 *
	 * @param actionId the action id of this social equity setting
	 */
	public void setActionId(String actionId);

	/**
	 * Gets the daily limit of this social equity setting.
	 *
	 * @return the daily limit of this social equity setting
	 */
	public int getDailyLimit();

	/**
	 * Sets the daily limit of this social equity setting.
	 *
	 * @param dailyLimit the daily limit of this social equity setting
	 */
	public void setDailyLimit(int dailyLimit);

	/**
	 * Gets the lifespan of this social equity setting.
	 *
	 * @return the lifespan of this social equity setting
	 */
	public int getLifespan();

	/**
	 * Sets the lifespan of this social equity setting.
	 *
	 * @param lifespan the lifespan of this social equity setting
	 */
	public void setLifespan(int lifespan);

	/**
	 * Gets the type of this social equity setting.
	 *
	 * @return the type of this social equity setting
	 */
	public int getType();

	/**
	 * Sets the type of this social equity setting.
	 *
	 * @param type the type of this social equity setting
	 */
	public void setType(int type);

	/**
	 * Gets the unique entry of this social equity setting.
	 *
	 * @return the unique entry of this social equity setting
	 */
	public boolean getUniqueEntry();

	/**
	 * Determines whether this social equity setting is unique entry.
	 *
	 * @return whether this social equity setting is unique entry
	 */
	public boolean isUniqueEntry();

	/**
	 * Sets whether this {$entity.humanName} is unique entry.
	 *
	 * @param uniqueEntry the unique entry of this social equity setting
	 */
	public void setUniqueEntry(boolean uniqueEntry);

	/**
	 * Gets the value of this social equity setting.
	 *
	 * @return the value of this social equity setting
	 */
	public int getValue();

	/**
	 * Sets the value of this social equity setting.
	 *
	 * @param value the value of this social equity setting
	 */
	public void setValue(int value);

	/**
	 * Gets a copy of this social equity setting as an escaped model instance by wrapping it with an {@link com.liferay.portal.kernel.bean.AutoEscapeBeanHandler}.
	 *
	 * @return the escaped model instance
	 * @see com.liferay.portal.kernel.bean.AutoEscapeBeanHandler
	 */
	public SocialEquitySetting toEscapedModel();

	public boolean isNew();

	public void setNew(boolean n);

	public boolean isCachedModel();

	public void setCachedModel(boolean cachedModel);

	public boolean isEscapedModel();

	public void setEscapedModel(boolean escapedModel);

	public Serializable getPrimaryKeyObj();

	public ExpandoBridge getExpandoBridge();

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public Object clone();

	public int compareTo(SocialEquitySetting socialEquitySetting);

	public int hashCode();

	public String toString();

	public String toXmlString();
}