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

package com.liferay.segments.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.segments.exception.NoSuchExperimentException;
import com.liferay.segments.model.SegmentsExperiment;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the segments experiment service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Eduardo Garcia
 * @see SegmentsExperimentUtil
 * @generated
 */
@ProviderType
public interface SegmentsExperimentPersistence
	extends BasePersistence<SegmentsExperiment> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SegmentsExperimentUtil} to access the segments experiment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the segments experiments where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching segments experiments
	 */
	public java.util.List<SegmentsExperiment> findByUuid(String uuid);

	/**
	 * Returns a range of all the segments experiments where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @return the range of matching segments experiments
	 */
	public java.util.List<SegmentsExperiment> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the segments experiments where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiments
	 */
	public java.util.List<SegmentsExperiment> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
			orderByComparator);

	/**
	 * Returns an ordered range of all the segments experiments where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching segments experiments
	 */
	public java.util.List<SegmentsExperiment> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first segments experiment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experiment
	 * @throws NoSuchExperimentException if a matching segments experiment could not be found
	 */
	public SegmentsExperiment findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
				orderByComparator)
		throws NoSuchExperimentException;

	/**
	 * Returns the first segments experiment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	public SegmentsExperiment fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
			orderByComparator);

	/**
	 * Returns the last segments experiment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experiment
	 * @throws NoSuchExperimentException if a matching segments experiment could not be found
	 */
	public SegmentsExperiment findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
				orderByComparator)
		throws NoSuchExperimentException;

	/**
	 * Returns the last segments experiment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	public SegmentsExperiment fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
			orderByComparator);

	/**
	 * Returns the segments experiments before and after the current segments experiment in the ordered set where uuid = &#63;.
	 *
	 * @param segmentsExperimentId the primary key of the current segments experiment
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experiment
	 * @throws NoSuchExperimentException if a segments experiment with the primary key could not be found
	 */
	public SegmentsExperiment[] findByUuid_PrevAndNext(
			long segmentsExperimentId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
				orderByComparator)
		throws NoSuchExperimentException;

	/**
	 * Removes all the segments experiments where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of segments experiments where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching segments experiments
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the segments experiment where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchExperimentException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching segments experiment
	 * @throws NoSuchExperimentException if a matching segments experiment could not be found
	 */
	public SegmentsExperiment findByUUID_G(String uuid, long groupId)
		throws NoSuchExperimentException;

	/**
	 * Returns the segments experiment where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	public SegmentsExperiment fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the segments experiment where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	public SegmentsExperiment fetchByUUID_G(
		String uuid, long groupId, boolean retrieveFromCache);

	/**
	 * Removes the segments experiment where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the segments experiment that was removed
	 */
	public SegmentsExperiment removeByUUID_G(String uuid, long groupId)
		throws NoSuchExperimentException;

	/**
	 * Returns the number of segments experiments where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching segments experiments
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the segments experiments where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching segments experiments
	 */
	public java.util.List<SegmentsExperiment> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the segments experiments where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @return the range of matching segments experiments
	 */
	public java.util.List<SegmentsExperiment> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the segments experiments where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiments
	 */
	public java.util.List<SegmentsExperiment> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
			orderByComparator);

	/**
	 * Returns an ordered range of all the segments experiments where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching segments experiments
	 */
	public java.util.List<SegmentsExperiment> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first segments experiment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experiment
	 * @throws NoSuchExperimentException if a matching segments experiment could not be found
	 */
	public SegmentsExperiment findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
				orderByComparator)
		throws NoSuchExperimentException;

	/**
	 * Returns the first segments experiment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	public SegmentsExperiment fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
			orderByComparator);

	/**
	 * Returns the last segments experiment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experiment
	 * @throws NoSuchExperimentException if a matching segments experiment could not be found
	 */
	public SegmentsExperiment findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
				orderByComparator)
		throws NoSuchExperimentException;

	/**
	 * Returns the last segments experiment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	public SegmentsExperiment fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
			orderByComparator);

	/**
	 * Returns the segments experiments before and after the current segments experiment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param segmentsExperimentId the primary key of the current segments experiment
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experiment
	 * @throws NoSuchExperimentException if a segments experiment with the primary key could not be found
	 */
	public SegmentsExperiment[] findByUuid_C_PrevAndNext(
			long segmentsExperimentId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
				orderByComparator)
		throws NoSuchExperimentException;

	/**
	 * Removes all the segments experiments where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of segments experiments where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching segments experiments
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the segments experiments where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching segments experiments
	 */
	public java.util.List<SegmentsExperiment> findByGroupId(long groupId);

	/**
	 * Returns a range of all the segments experiments where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @return the range of matching segments experiments
	 */
	public java.util.List<SegmentsExperiment> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the segments experiments where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiments
	 */
	public java.util.List<SegmentsExperiment> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
			orderByComparator);

	/**
	 * Returns an ordered range of all the segments experiments where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching segments experiments
	 */
	public java.util.List<SegmentsExperiment> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first segments experiment in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experiment
	 * @throws NoSuchExperimentException if a matching segments experiment could not be found
	 */
	public SegmentsExperiment findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
				orderByComparator)
		throws NoSuchExperimentException;

	/**
	 * Returns the first segments experiment in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	public SegmentsExperiment fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
			orderByComparator);

	/**
	 * Returns the last segments experiment in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experiment
	 * @throws NoSuchExperimentException if a matching segments experiment could not be found
	 */
	public SegmentsExperiment findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
				orderByComparator)
		throws NoSuchExperimentException;

	/**
	 * Returns the last segments experiment in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	public SegmentsExperiment fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
			orderByComparator);

	/**
	 * Returns the segments experiments before and after the current segments experiment in the ordered set where groupId = &#63;.
	 *
	 * @param segmentsExperimentId the primary key of the current segments experiment
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experiment
	 * @throws NoSuchExperimentException if a segments experiment with the primary key could not be found
	 */
	public SegmentsExperiment[] findByGroupId_PrevAndNext(
			long segmentsExperimentId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
				orderByComparator)
		throws NoSuchExperimentException;

	/**
	 * Returns all the segments experiments that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching segments experiments that the user has permission to view
	 */
	public java.util.List<SegmentsExperiment> filterFindByGroupId(long groupId);

	/**
	 * Returns a range of all the segments experiments that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @return the range of matching segments experiments that the user has permission to view
	 */
	public java.util.List<SegmentsExperiment> filterFindByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the segments experiments that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiments that the user has permission to view
	 */
	public java.util.List<SegmentsExperiment> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
			orderByComparator);

	/**
	 * Returns the segments experiments before and after the current segments experiment in the ordered set of segments experiments that the user has permission to view where groupId = &#63;.
	 *
	 * @param segmentsExperimentId the primary key of the current segments experiment
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experiment
	 * @throws NoSuchExperimentException if a segments experiment with the primary key could not be found
	 */
	public SegmentsExperiment[] filterFindByGroupId_PrevAndNext(
			long segmentsExperimentId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
				orderByComparator)
		throws NoSuchExperimentException;

	/**
	 * Removes all the segments experiments where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of segments experiments where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching segments experiments
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns the number of segments experiments that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching segments experiments that the user has permission to view
	 */
	public int filterCountByGroupId(long groupId);

	/**
	 * Returns the segments experiment where groupId = &#63; and segmentsExperimentKey = &#63; or throws a <code>NoSuchExperimentException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperimentKey the segments experiment key
	 * @return the matching segments experiment
	 * @throws NoSuchExperimentException if a matching segments experiment could not be found
	 */
	public SegmentsExperiment findByG_SE(
			long groupId, String segmentsExperimentKey)
		throws NoSuchExperimentException;

	/**
	 * Returns the segments experiment where groupId = &#63; and segmentsExperimentKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperimentKey the segments experiment key
	 * @return the matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	public SegmentsExperiment fetchByG_SE(
		long groupId, String segmentsExperimentKey);

	/**
	 * Returns the segments experiment where groupId = &#63; and segmentsExperimentKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperimentKey the segments experiment key
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	public SegmentsExperiment fetchByG_SE(
		long groupId, String segmentsExperimentKey, boolean retrieveFromCache);

	/**
	 * Removes the segments experiment where groupId = &#63; and segmentsExperimentKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperimentKey the segments experiment key
	 * @return the segments experiment that was removed
	 */
	public SegmentsExperiment removeByG_SE(
			long groupId, String segmentsExperimentKey)
		throws NoSuchExperimentException;

	/**
	 * Returns the number of segments experiments where groupId = &#63; and segmentsExperimentKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperimentKey the segments experiment key
	 * @return the number of matching segments experiments
	 */
	public int countByG_SE(long groupId, String segmentsExperimentKey);

	/**
	 * Returns all the segments experiments where groupId = &#63; and segmentsExperienceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @return the matching segments experiments
	 */
	public java.util.List<SegmentsExperiment> findByG_S(
		long groupId, long segmentsExperienceId);

	/**
	 * Returns a range of all the segments experiments where groupId = &#63; and segmentsExperienceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @return the range of matching segments experiments
	 */
	public java.util.List<SegmentsExperiment> findByG_S(
		long groupId, long segmentsExperienceId, int start, int end);

	/**
	 * Returns an ordered range of all the segments experiments where groupId = &#63; and segmentsExperienceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiments
	 */
	public java.util.List<SegmentsExperiment> findByG_S(
		long groupId, long segmentsExperienceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
			orderByComparator);

	/**
	 * Returns an ordered range of all the segments experiments where groupId = &#63; and segmentsExperienceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching segments experiments
	 */
	public java.util.List<SegmentsExperiment> findByG_S(
		long groupId, long segmentsExperienceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first segments experiment in the ordered set where groupId = &#63; and segmentsExperienceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experiment
	 * @throws NoSuchExperimentException if a matching segments experiment could not be found
	 */
	public SegmentsExperiment findByG_S_First(
			long groupId, long segmentsExperienceId,
			com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
				orderByComparator)
		throws NoSuchExperimentException;

	/**
	 * Returns the first segments experiment in the ordered set where groupId = &#63; and segmentsExperienceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	public SegmentsExperiment fetchByG_S_First(
		long groupId, long segmentsExperienceId,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
			orderByComparator);

	/**
	 * Returns the last segments experiment in the ordered set where groupId = &#63; and segmentsExperienceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experiment
	 * @throws NoSuchExperimentException if a matching segments experiment could not be found
	 */
	public SegmentsExperiment findByG_S_Last(
			long groupId, long segmentsExperienceId,
			com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
				orderByComparator)
		throws NoSuchExperimentException;

	/**
	 * Returns the last segments experiment in the ordered set where groupId = &#63; and segmentsExperienceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	public SegmentsExperiment fetchByG_S_Last(
		long groupId, long segmentsExperienceId,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
			orderByComparator);

	/**
	 * Returns the segments experiments before and after the current segments experiment in the ordered set where groupId = &#63; and segmentsExperienceId = &#63;.
	 *
	 * @param segmentsExperimentId the primary key of the current segments experiment
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experiment
	 * @throws NoSuchExperimentException if a segments experiment with the primary key could not be found
	 */
	public SegmentsExperiment[] findByG_S_PrevAndNext(
			long segmentsExperimentId, long groupId, long segmentsExperienceId,
			com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
				orderByComparator)
		throws NoSuchExperimentException;

	/**
	 * Returns all the segments experiments that the user has permission to view where groupId = &#63; and segmentsExperienceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @return the matching segments experiments that the user has permission to view
	 */
	public java.util.List<SegmentsExperiment> filterFindByG_S(
		long groupId, long segmentsExperienceId);

	/**
	 * Returns a range of all the segments experiments that the user has permission to view where groupId = &#63; and segmentsExperienceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @return the range of matching segments experiments that the user has permission to view
	 */
	public java.util.List<SegmentsExperiment> filterFindByG_S(
		long groupId, long segmentsExperienceId, int start, int end);

	/**
	 * Returns an ordered range of all the segments experiments that the user has permissions to view where groupId = &#63; and segmentsExperienceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiments that the user has permission to view
	 */
	public java.util.List<SegmentsExperiment> filterFindByG_S(
		long groupId, long segmentsExperienceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
			orderByComparator);

	/**
	 * Returns the segments experiments before and after the current segments experiment in the ordered set of segments experiments that the user has permission to view where groupId = &#63; and segmentsExperienceId = &#63;.
	 *
	 * @param segmentsExperimentId the primary key of the current segments experiment
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experiment
	 * @throws NoSuchExperimentException if a segments experiment with the primary key could not be found
	 */
	public SegmentsExperiment[] filterFindByG_S_PrevAndNext(
			long segmentsExperimentId, long groupId, long segmentsExperienceId,
			com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
				orderByComparator)
		throws NoSuchExperimentException;

	/**
	 * Returns all the segments experiments that the user has permission to view where groupId = &#63; and segmentsExperienceId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceIds the segments experience IDs
	 * @return the matching segments experiments that the user has permission to view
	 */
	public java.util.List<SegmentsExperiment> filterFindByG_S(
		long groupId, long[] segmentsExperienceIds);

	/**
	 * Returns a range of all the segments experiments that the user has permission to view where groupId = &#63; and segmentsExperienceId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceIds the segments experience IDs
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @return the range of matching segments experiments that the user has permission to view
	 */
	public java.util.List<SegmentsExperiment> filterFindByG_S(
		long groupId, long[] segmentsExperienceIds, int start, int end);

	/**
	 * Returns an ordered range of all the segments experiments that the user has permission to view where groupId = &#63; and segmentsExperienceId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceIds the segments experience IDs
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiments that the user has permission to view
	 */
	public java.util.List<SegmentsExperiment> filterFindByG_S(
		long groupId, long[] segmentsExperienceIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
			orderByComparator);

	/**
	 * Returns all the segments experiments where groupId = &#63; and segmentsExperienceId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceIds the segments experience IDs
	 * @return the matching segments experiments
	 */
	public java.util.List<SegmentsExperiment> findByG_S(
		long groupId, long[] segmentsExperienceIds);

	/**
	 * Returns a range of all the segments experiments where groupId = &#63; and segmentsExperienceId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceIds the segments experience IDs
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @return the range of matching segments experiments
	 */
	public java.util.List<SegmentsExperiment> findByG_S(
		long groupId, long[] segmentsExperienceIds, int start, int end);

	/**
	 * Returns an ordered range of all the segments experiments where groupId = &#63; and segmentsExperienceId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceIds the segments experience IDs
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiments
	 */
	public java.util.List<SegmentsExperiment> findByG_S(
		long groupId, long[] segmentsExperienceIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
			orderByComparator);

	/**
	 * Returns an ordered range of all the segments experiments where groupId = &#63; and segmentsExperienceId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching segments experiments
	 */
	public java.util.List<SegmentsExperiment> findByG_S(
		long groupId, long[] segmentsExperienceIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Removes all the segments experiments where groupId = &#63; and segmentsExperienceId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 */
	public void removeByG_S(long groupId, long segmentsExperienceId);

	/**
	 * Returns the number of segments experiments where groupId = &#63; and segmentsExperienceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @return the number of matching segments experiments
	 */
	public int countByG_S(long groupId, long segmentsExperienceId);

	/**
	 * Returns the number of segments experiments where groupId = &#63; and segmentsExperienceId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceIds the segments experience IDs
	 * @return the number of matching segments experiments
	 */
	public int countByG_S(long groupId, long[] segmentsExperienceIds);

	/**
	 * Returns the number of segments experiments that the user has permission to view where groupId = &#63; and segmentsExperienceId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @return the number of matching segments experiments that the user has permission to view
	 */
	public int filterCountByG_S(long groupId, long segmentsExperienceId);

	/**
	 * Returns the number of segments experiments that the user has permission to view where groupId = &#63; and segmentsExperienceId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceIds the segments experience IDs
	 * @return the number of matching segments experiments that the user has permission to view
	 */
	public int filterCountByG_S(long groupId, long[] segmentsExperienceIds);

	/**
	 * Caches the segments experiment in the entity cache if it is enabled.
	 *
	 * @param segmentsExperiment the segments experiment
	 */
	public void cacheResult(SegmentsExperiment segmentsExperiment);

	/**
	 * Caches the segments experiments in the entity cache if it is enabled.
	 *
	 * @param segmentsExperiments the segments experiments
	 */
	public void cacheResult(
		java.util.List<SegmentsExperiment> segmentsExperiments);

	/**
	 * Creates a new segments experiment with the primary key. Does not add the segments experiment to the database.
	 *
	 * @param segmentsExperimentId the primary key for the new segments experiment
	 * @return the new segments experiment
	 */
	public SegmentsExperiment create(long segmentsExperimentId);

	/**
	 * Removes the segments experiment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsExperimentId the primary key of the segments experiment
	 * @return the segments experiment that was removed
	 * @throws NoSuchExperimentException if a segments experiment with the primary key could not be found
	 */
	public SegmentsExperiment remove(long segmentsExperimentId)
		throws NoSuchExperimentException;

	public SegmentsExperiment updateImpl(SegmentsExperiment segmentsExperiment);

	/**
	 * Returns the segments experiment with the primary key or throws a <code>NoSuchExperimentException</code> if it could not be found.
	 *
	 * @param segmentsExperimentId the primary key of the segments experiment
	 * @return the segments experiment
	 * @throws NoSuchExperimentException if a segments experiment with the primary key could not be found
	 */
	public SegmentsExperiment findByPrimaryKey(long segmentsExperimentId)
		throws NoSuchExperimentException;

	/**
	 * Returns the segments experiment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param segmentsExperimentId the primary key of the segments experiment
	 * @return the segments experiment, or <code>null</code> if a segments experiment with the primary key could not be found
	 */
	public SegmentsExperiment fetchByPrimaryKey(long segmentsExperimentId);

	/**
	 * Returns all the segments experiments.
	 *
	 * @return the segments experiments
	 */
	public java.util.List<SegmentsExperiment> findAll();

	/**
	 * Returns a range of all the segments experiments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @return the range of segments experiments
	 */
	public java.util.List<SegmentsExperiment> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the segments experiments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of segments experiments
	 */
	public java.util.List<SegmentsExperiment> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
			orderByComparator);

	/**
	 * Returns an ordered range of all the segments experiments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of segments experiments
	 */
	public java.util.List<SegmentsExperiment> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SegmentsExperiment>
			orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Removes all the segments experiments from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of segments experiments.
	 *
	 * @return the number of segments experiments
	 */
	public int countAll();

}