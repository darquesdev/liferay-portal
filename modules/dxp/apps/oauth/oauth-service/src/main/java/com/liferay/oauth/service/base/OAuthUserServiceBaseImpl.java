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

package com.liferay.oauth.service.base;

import com.liferay.oauth.model.OAuthUser;
import com.liferay.oauth.service.OAuthUserService;
import com.liferay.oauth.service.persistence.OAuthApplicationPersistence;
import com.liferay.oauth.service.persistence.OAuthUserPersistence;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.service.BaseServiceImpl;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the o auth user remote service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.oauth.service.impl.OAuthUserServiceImpl}.
 * </p>
 *
 * @author Ivica Cardic
 * @see com.liferay.oauth.service.impl.OAuthUserServiceImpl
 * @generated
 */
public abstract class OAuthUserServiceBaseImpl
	extends BaseServiceImpl
	implements OAuthUserService, IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>OAuthUserService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.oauth.service.OAuthUserServiceUtil</code>.
	 */

	/**
	 * Returns the o auth application local service.
	 *
	 * @return the o auth application local service
	 */
	public com.liferay.oauth.service.OAuthApplicationLocalService
		getOAuthApplicationLocalService() {

		return oAuthApplicationLocalService;
	}

	/**
	 * Sets the o auth application local service.
	 *
	 * @param oAuthApplicationLocalService the o auth application local service
	 */
	public void setOAuthApplicationLocalService(
		com.liferay.oauth.service.OAuthApplicationLocalService
			oAuthApplicationLocalService) {

		this.oAuthApplicationLocalService = oAuthApplicationLocalService;
	}

	/**
	 * Returns the o auth application remote service.
	 *
	 * @return the o auth application remote service
	 */
	public com.liferay.oauth.service.OAuthApplicationService
		getOAuthApplicationService() {

		return oAuthApplicationService;
	}

	/**
	 * Sets the o auth application remote service.
	 *
	 * @param oAuthApplicationService the o auth application remote service
	 */
	public void setOAuthApplicationService(
		com.liferay.oauth.service.OAuthApplicationService
			oAuthApplicationService) {

		this.oAuthApplicationService = oAuthApplicationService;
	}

	/**
	 * Returns the o auth application persistence.
	 *
	 * @return the o auth application persistence
	 */
	public OAuthApplicationPersistence getOAuthApplicationPersistence() {
		return oAuthApplicationPersistence;
	}

	/**
	 * Sets the o auth application persistence.
	 *
	 * @param oAuthApplicationPersistence the o auth application persistence
	 */
	public void setOAuthApplicationPersistence(
		OAuthApplicationPersistence oAuthApplicationPersistence) {

		this.oAuthApplicationPersistence = oAuthApplicationPersistence;
	}

	/**
	 * Returns the o auth user local service.
	 *
	 * @return the o auth user local service
	 */
	public com.liferay.oauth.service.OAuthUserLocalService
		getOAuthUserLocalService() {

		return oAuthUserLocalService;
	}

	/**
	 * Sets the o auth user local service.
	 *
	 * @param oAuthUserLocalService the o auth user local service
	 */
	public void setOAuthUserLocalService(
		com.liferay.oauth.service.OAuthUserLocalService oAuthUserLocalService) {

		this.oAuthUserLocalService = oAuthUserLocalService;
	}

	/**
	 * Returns the o auth user remote service.
	 *
	 * @return the o auth user remote service
	 */
	public OAuthUserService getOAuthUserService() {
		return oAuthUserService;
	}

	/**
	 * Sets the o auth user remote service.
	 *
	 * @param oAuthUserService the o auth user remote service
	 */
	public void setOAuthUserService(OAuthUserService oAuthUserService) {
		this.oAuthUserService = oAuthUserService;
	}

	/**
	 * Returns the o auth user persistence.
	 *
	 * @return the o auth user persistence
	 */
	public OAuthUserPersistence getOAuthUserPersistence() {
		return oAuthUserPersistence;
	}

	/**
	 * Sets the o auth user persistence.
	 *
	 * @param oAuthUserPersistence the o auth user persistence
	 */
	public void setOAuthUserPersistence(
		OAuthUserPersistence oAuthUserPersistence) {

		this.oAuthUserPersistence = oAuthUserPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService
		getCounterLocalService() {

		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService
			counterLocalService) {

		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.kernel.service.ClassNameLocalService
		getClassNameLocalService() {

		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.kernel.service.ClassNameLocalService
			classNameLocalService) {

		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name remote service.
	 *
	 * @return the class name remote service
	 */
	public com.liferay.portal.kernel.service.ClassNameService
		getClassNameService() {

		return classNameService;
	}

	/**
	 * Sets the class name remote service.
	 *
	 * @param classNameService the class name remote service
	 */
	public void setClassNameService(
		com.liferay.portal.kernel.service.ClassNameService classNameService) {

		this.classNameService = classNameService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {

		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.kernel.service.ResourceLocalService
		getResourceLocalService() {

		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.kernel.service.ResourceLocalService
			resourceLocalService) {

		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.kernel.service.UserLocalService
		getUserLocalService() {

		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.kernel.service.UserLocalService userLocalService) {

		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public com.liferay.portal.kernel.service.UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(
		com.liferay.portal.kernel.service.UserService userService) {

		this.userService = userService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
	}

	public void destroy() {
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return OAuthUserService.class.getName();
	}

	protected Class<?> getModelClass() {
		return OAuthUser.class;
	}

	protected String getModelClassName() {
		return OAuthUser.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = oAuthUserPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
				dataSource, sql);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(
		type = com.liferay.oauth.service.OAuthApplicationLocalService.class
	)
	protected com.liferay.oauth.service.OAuthApplicationLocalService
		oAuthApplicationLocalService;

	@BeanReference(
		type = com.liferay.oauth.service.OAuthApplicationService.class
	)
	protected com.liferay.oauth.service.OAuthApplicationService
		oAuthApplicationService;

	@BeanReference(type = OAuthApplicationPersistence.class)
	protected OAuthApplicationPersistence oAuthApplicationPersistence;

	@BeanReference(type = com.liferay.oauth.service.OAuthUserLocalService.class)
	protected com.liferay.oauth.service.OAuthUserLocalService
		oAuthUserLocalService;

	@BeanReference(type = OAuthUserService.class)
	protected OAuthUserService oAuthUserService;

	@BeanReference(type = OAuthUserPersistence.class)
	protected OAuthUserPersistence oAuthUserPersistence;

	@ServiceReference(
		type = com.liferay.counter.kernel.service.CounterLocalService.class
	)
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	@ServiceReference(
		type = com.liferay.portal.kernel.service.ClassNameLocalService.class
	)
	protected com.liferay.portal.kernel.service.ClassNameLocalService
		classNameLocalService;

	@ServiceReference(
		type = com.liferay.portal.kernel.service.ClassNameService.class
	)
	protected com.liferay.portal.kernel.service.ClassNameService
		classNameService;

	@ServiceReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;

	@ServiceReference(
		type = com.liferay.portal.kernel.service.ResourceLocalService.class
	)
	protected com.liferay.portal.kernel.service.ResourceLocalService
		resourceLocalService;

	@ServiceReference(
		type = com.liferay.portal.kernel.service.UserLocalService.class
	)
	protected com.liferay.portal.kernel.service.UserLocalService
		userLocalService;

	@ServiceReference(
		type = com.liferay.portal.kernel.service.UserService.class
	)
	protected com.liferay.portal.kernel.service.UserService userService;

	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;

}