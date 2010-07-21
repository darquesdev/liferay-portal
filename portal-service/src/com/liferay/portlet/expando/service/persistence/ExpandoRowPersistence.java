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

package com.liferay.portlet.expando.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.expando.model.ExpandoRow;

/**
 * The persistence interface for the expando row service.
 *
 * <p>
 * Never modify this interface directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regnerate this interface.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoRowPersistenceImpl
 * @see ExpandoRowUtil
 * @generated
 */
public interface ExpandoRowPersistence extends BasePersistence<ExpandoRow> {
	/**
	* Caches the expando row in the entity cache if it is enabled.
	*
	* @param expandoRow the expando row to cache
	*/
	public void cacheResult(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow);

	/**
	* Caches the expando rows in the entity cache if it is enabled.
	*
	* @param expandoRows the expando rows to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.expando.model.ExpandoRow> expandoRows);

	/**
	* Creates a new expando row with the primary key.
	*
	* @param rowId the primary key for the new expando row
	* @return the new expando row
	*/
	public com.liferay.portlet.expando.model.ExpandoRow create(long rowId);

	/**
	* Removes the expando row with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param rowId the primary key of the expando row to remove
	* @return the expando row that was removed
	* @throws com.liferay.portlet.expando.NoSuchRowException if a expando row with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoRow remove(long rowId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchRowException;

	public com.liferay.portlet.expando.model.ExpandoRow updateImpl(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the expando row with the primary key or throws a {@link com.liferay.portlet.expando.NoSuchRowException} if it could not be found.
	*
	* @param rowId the primary key of the expando row to find
	* @return the expando row
	* @throws com.liferay.portlet.expando.NoSuchRowException if a expando row with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoRow findByPrimaryKey(
		long rowId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchRowException;

	/**
	* Finds the expando row with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param rowId the primary key of the expando row to find
	* @return the expando row, or <code>null</code> if a expando row with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoRow fetchByPrimaryKey(
		long rowId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the expando rows where tableId = &#63;.
	*
	* @param tableId the table id to search with
	* @return the matching expando rows
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> findByTableId(
		long tableId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the expando rows where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table id to search with
	* @param start the lower bound of the range of expando rows to return
	* @param end the upper bound of the range of expando rows to return (not inclusive)
	* @return the range of matching expando rows
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> findByTableId(
		long tableId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the expando rows where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table id to search with
	* @param start the lower bound of the range of expando rows to return
	* @param end the upper bound of the range of expando rows to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching expando rows
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> findByTableId(
		long tableId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first expando row in the ordered set where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching expando row
	* @throws com.liferay.portlet.expando.NoSuchRowException if a matching expando row could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoRow findByTableId_First(
		long tableId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchRowException;

	/**
	* Finds the last expando row in the ordered set where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the table id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching expando row
	* @throws com.liferay.portlet.expando.NoSuchRowException if a matching expando row could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoRow findByTableId_Last(
		long tableId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchRowException;

	/**
	* Finds the expando rows before and after the current expando row in the ordered set where tableId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param rowId the primary key of the current expando row
	* @param tableId the table id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next expando row
	* @throws com.liferay.portlet.expando.NoSuchRowException if a expando row with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoRow[] findByTableId_PrevAndNext(
		long rowId, long tableId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchRowException;

	/**
	* Finds the expando row where tableId = &#63; and classPK = &#63; or throws a {@link com.liferay.portlet.expando.NoSuchRowException} if it could not be found.
	*
	* @param tableId the table id to search with
	* @param classPK the class p k to search with
	* @return the matching expando row
	* @throws com.liferay.portlet.expando.NoSuchRowException if a matching expando row could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoRow findByT_C(
		long tableId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchRowException;

	/**
	* Finds the expando row where tableId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param tableId the table id to search with
	* @param classPK the class p k to search with
	* @return the matching expando row, or <code>null</code> if a matching expando row could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoRow fetchByT_C(
		long tableId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the expando row where tableId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param tableId the table id to search with
	* @param classPK the class p k to search with
	* @return the matching expando row, or <code>null</code> if a matching expando row could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.expando.model.ExpandoRow fetchByT_C(
		long tableId, long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the expando rows.
	*
	* @return the expando rows
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the expando rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of expando rows to return
	* @param end the upper bound of the range of expando rows to return (not inclusive)
	* @return the range of expando rows
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the expando rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of expando rows to return
	* @param end the upper bound of the range of expando rows to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of expando rows
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.expando.model.ExpandoRow> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the expando rows where tableId = &#63; from the database.
	*
	* @param tableId the table id to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByTableId(long tableId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the expando row where tableId = &#63; and classPK = &#63; from the database.
	*
	* @param tableId the table id to search with
	* @param classPK the class p k to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByT_C(long tableId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchRowException;

	/**
	* Removes all the expando rows from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the expando rows where tableId = &#63;.
	*
	* @param tableId the table id to search with
	* @return the number of matching expando rows
	* @throws SystemException if a system exception occurred
	*/
	public int countByTableId(long tableId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the expando rows where tableId = &#63; and classPK = &#63;.
	*
	* @param tableId the table id to search with
	* @param classPK the class p k to search with
	* @return the number of matching expando rows
	* @throws SystemException if a system exception occurred
	*/
	public int countByT_C(long tableId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the expando rows.
	*
	* @return the number of expando rows
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}