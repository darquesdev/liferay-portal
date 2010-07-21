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

package com.liferay.portlet.shopping.model;

/**
 * The model interface for the ShoppingCoupon service. Represents a row in the &quot;ShoppingCoupon&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.shopping.model.impl.ShoppingCouponImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingCouponModel
 * @see com.liferay.portlet.shopping.model.impl.ShoppingCouponImpl
 * @see com.liferay.portlet.shopping.model.impl.ShoppingCouponModelImpl
 * @generated
 */
public interface ShoppingCoupon extends ShoppingCouponModel {
	public boolean hasValidDateRange();

	public boolean hasValidEndDate();

	public boolean hasValidStartDate();
}