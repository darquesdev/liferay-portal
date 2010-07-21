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

package com.liferay.portlet.messageboards.model;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;

/**
 * The base model interface for the MBStatsUser service. Represents a row in the &quot;MBStatsUser&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portlet.messageboards.model.impl.MBStatsUserModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.portlet.messageboards.model.impl.MBStatsUserImpl}.
 * </p>
 *
 * <p>
 * Never modify or reference this interface directly. All methods that expect a message boards stats user model instance should use the {@link MBStatsUser} interface instead.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBStatsUser
 * @see com.liferay.portlet.messageboards.model.impl.MBStatsUserImpl
 * @see com.liferay.portlet.messageboards.model.impl.MBStatsUserModelImpl
 * @generated
 */
public interface MBStatsUserModel extends BaseModel<MBStatsUser> {
	/**
	 * Gets the primary key of this message boards stats user.
	 *
	 * @return the primary key of this message boards stats user
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this message boards stats user
	 *
	 * @param pk the primary key of this message boards stats user
	 */
	public void setPrimaryKey(long pk);

	/**
	 * Gets the stats user id of this message boards stats user.
	 *
	 * @return the stats user id of this message boards stats user
	 */
	public long getStatsUserId();

	/**
	 * Sets the stats user id of this message boards stats user.
	 *
	 * @param statsUserId the stats user id of this message boards stats user
	 */
	public void setStatsUserId(long statsUserId);

	/**
	 * Gets the stats user uuid of this message boards stats user.
	 *
	 * @return the stats user uuid of this message boards stats user
	 * @throws SystemException if a system exception occurred
	 */
	public String getStatsUserUuid() throws SystemException;

	/**
	 * Sets the stats user uuid of this message boards stats user.
	 *
	 * @param statsUserUuid the stats user uuid of this message boards stats user
	 */
	public void setStatsUserUuid(String statsUserUuid);

	/**
	 * Gets the group id of this message boards stats user.
	 *
	 * @return the group id of this message boards stats user
	 */
	public long getGroupId();

	/**
	 * Sets the group id of this message boards stats user.
	 *
	 * @param groupId the group id of this message boards stats user
	 */
	public void setGroupId(long groupId);

	/**
	 * Gets the user id of this message boards stats user.
	 *
	 * @return the user id of this message boards stats user
	 */
	public long getUserId();

	/**
	 * Sets the user id of this message boards stats user.
	 *
	 * @param userId the user id of this message boards stats user
	 */
	public void setUserId(long userId);

	/**
	 * Gets the user uuid of this message boards stats user.
	 *
	 * @return the user uuid of this message boards stats user
	 * @throws SystemException if a system exception occurred
	 */
	public String getUserUuid() throws SystemException;

	/**
	 * Sets the user uuid of this message boards stats user.
	 *
	 * @param userUuid the user uuid of this message boards stats user
	 */
	public void setUserUuid(String userUuid);

	/**
	 * Gets the message count of this message boards stats user.
	 *
	 * @return the message count of this message boards stats user
	 */
	public int getMessageCount();

	/**
	 * Sets the message count of this message boards stats user.
	 *
	 * @param messageCount the message count of this message boards stats user
	 */
	public void setMessageCount(int messageCount);

	/**
	 * Gets the last post date of this message boards stats user.
	 *
	 * @return the last post date of this message boards stats user
	 */
	public Date getLastPostDate();

	/**
	 * Sets the last post date of this message boards stats user.
	 *
	 * @param lastPostDate the last post date of this message boards stats user
	 */
	public void setLastPostDate(Date lastPostDate);

	/**
	 * Gets a copy of this message boards stats user as an escaped model instance by wrapping it with an {@link com.liferay.portal.kernel.bean.AutoEscapeBeanHandler}.
	 *
	 * @return the escaped model instance
	 * @see com.liferay.portal.kernel.bean.AutoEscapeBeanHandler
	 */
	public MBStatsUser toEscapedModel();

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

	public int compareTo(MBStatsUser mbStatsUser);

	public int hashCode();

	public String toString();

	public String toXmlString();
}