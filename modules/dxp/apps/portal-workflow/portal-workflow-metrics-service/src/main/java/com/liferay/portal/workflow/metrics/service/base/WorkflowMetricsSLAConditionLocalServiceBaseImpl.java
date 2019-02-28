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

package com.liferay.portal.workflow.metrics.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACondition;
import com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLAConditionLocalService;
import com.liferay.portal.workflow.metrics.service.persistence.WorkflowMetricsSLACalendarPersistence;
import com.liferay.portal.workflow.metrics.service.persistence.WorkflowMetricsSLAConditionPersistence;
import com.liferay.portal.workflow.metrics.service.persistence.WorkflowMetricsSLADefinitionPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the workflow metrics sla condition local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portal.workflow.metrics.service.impl.WorkflowMetricsSLAConditionLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.workflow.metrics.service.impl.WorkflowMetricsSLAConditionLocalServiceImpl
 * @generated
 */
@ProviderType
public abstract class WorkflowMetricsSLAConditionLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements WorkflowMetricsSLAConditionLocalService,
			   IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>WorkflowMetricsSLAConditionLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLAConditionLocalServiceUtil</code>.
	 */

	/**
	 * Adds the workflow metrics sla condition to the database. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLACondition the workflow metrics sla condition
	 * @return the workflow metrics sla condition that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public WorkflowMetricsSLACondition addWorkflowMetricsSLACondition(
		WorkflowMetricsSLACondition workflowMetricsSLACondition) {

		workflowMetricsSLACondition.setNew(true);

		return workflowMetricsSLAConditionPersistence.update(
			workflowMetricsSLACondition);
	}

	/**
	 * Creates a new workflow metrics sla condition with the primary key. Does not add the workflow metrics sla condition to the database.
	 *
	 * @param workflowMetricsSLAConditionId the primary key for the new workflow metrics sla condition
	 * @return the new workflow metrics sla condition
	 */
	@Override
	@Transactional(enabled = false)
	public WorkflowMetricsSLACondition createWorkflowMetricsSLACondition(
		long workflowMetricsSLAConditionId) {

		return workflowMetricsSLAConditionPersistence.create(
			workflowMetricsSLAConditionId);
	}

	/**
	 * Deletes the workflow metrics sla condition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLAConditionId the primary key of the workflow metrics sla condition
	 * @return the workflow metrics sla condition that was removed
	 * @throws PortalException if a workflow metrics sla condition with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public WorkflowMetricsSLACondition deleteWorkflowMetricsSLACondition(
			long workflowMetricsSLAConditionId)
		throws PortalException {

		return workflowMetricsSLAConditionPersistence.remove(
			workflowMetricsSLAConditionId);
	}

	/**
	 * Deletes the workflow metrics sla condition from the database. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLACondition the workflow metrics sla condition
	 * @return the workflow metrics sla condition that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public WorkflowMetricsSLACondition deleteWorkflowMetricsSLACondition(
		WorkflowMetricsSLACondition workflowMetricsSLACondition) {

		return workflowMetricsSLAConditionPersistence.remove(
			workflowMetricsSLACondition);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(
			WorkflowMetricsSLACondition.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return workflowMetricsSLAConditionPersistence.findWithDynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLAConditionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return workflowMetricsSLAConditionPersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLAConditionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return workflowMetricsSLAConditionPersistence.findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return workflowMetricsSLAConditionPersistence.countWithDynamicQuery(
			dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection) {

		return workflowMetricsSLAConditionPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public WorkflowMetricsSLACondition fetchWorkflowMetricsSLACondition(
		long workflowMetricsSLAConditionId) {

		return workflowMetricsSLAConditionPersistence.fetchByPrimaryKey(
			workflowMetricsSLAConditionId);
	}

	/**
	 * Returns the workflow metrics sla condition matching the UUID and group.
	 *
	 * @param uuid the workflow metrics sla condition's UUID
	 * @param groupId the primary key of the group
	 * @return the matching workflow metrics sla condition, or <code>null</code> if a matching workflow metrics sla condition could not be found
	 */
	@Override
	public WorkflowMetricsSLACondition
		fetchWorkflowMetricsSLAConditionByUuidAndGroupId(
			String uuid, long groupId) {

		return workflowMetricsSLAConditionPersistence.fetchByUUID_G(
			uuid, groupId);
	}

	/**
	 * Returns the workflow metrics sla condition with the primary key.
	 *
	 * @param workflowMetricsSLAConditionId the primary key of the workflow metrics sla condition
	 * @return the workflow metrics sla condition
	 * @throws PortalException if a workflow metrics sla condition with the primary key could not be found
	 */
	@Override
	public WorkflowMetricsSLACondition getWorkflowMetricsSLACondition(
			long workflowMetricsSLAConditionId)
		throws PortalException {

		return workflowMetricsSLAConditionPersistence.findByPrimaryKey(
			workflowMetricsSLAConditionId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(
			workflowMetricsSLAConditionLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(WorkflowMetricsSLACondition.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"workflowMetricsSLAConditionId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			workflowMetricsSLAConditionLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(
			WorkflowMetricsSLACondition.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"workflowMetricsSLAConditionId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(
			workflowMetricsSLAConditionLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(WorkflowMetricsSLACondition.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"workflowMetricsSLAConditionId");
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		final ExportActionableDynamicQuery exportActionableDynamicQuery =
			new ExportActionableDynamicQuery() {

				@Override
				public long performCount() throws PortalException {
					ManifestSummary manifestSummary =
						portletDataContext.getManifestSummary();

					StagedModelType stagedModelType = getStagedModelType();

					long modelAdditionCount = super.performCount();

					manifestSummary.addModelAdditionCount(
						stagedModelType, modelAdditionCount);

					long modelDeletionCount =
						ExportImportHelperUtil.getModelDeletionCount(
							portletDataContext, stagedModelType);

					manifestSummary.addModelDeletionCount(
						stagedModelType, modelDeletionCount);

					return modelAdditionCount;
				}

			};

		initActionableDynamicQuery(exportActionableDynamicQuery);

		exportActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					portletDataContext.addDateRangeCriteria(
						dynamicQuery, "modifiedDate");
				}

			});

		exportActionableDynamicQuery.setCompanyId(
			portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<WorkflowMetricsSLACondition>() {

				@Override
				public void performAction(
						WorkflowMetricsSLACondition workflowMetricsSLACondition)
					throws PortalException {

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, workflowMetricsSLACondition);
				}

			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				PortalUtil.getClassNameId(
					WorkflowMetricsSLACondition.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return workflowMetricsSLAConditionLocalService.
			deleteWorkflowMetricsSLACondition(
				(WorkflowMetricsSLACondition)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return workflowMetricsSLAConditionPersistence.findByPrimaryKey(
			primaryKeyObj);
	}

	/**
	 * Returns all the workflow metrics sla conditions matching the UUID and company.
	 *
	 * @param uuid the UUID of the workflow metrics sla conditions
	 * @param companyId the primary key of the company
	 * @return the matching workflow metrics sla conditions, or an empty list if no matches were found
	 */
	@Override
	public List<WorkflowMetricsSLACondition>
		getWorkflowMetricsSLAConditionsByUuidAndCompanyId(
			String uuid, long companyId) {

		return workflowMetricsSLAConditionPersistence.findByUuid_C(
			uuid, companyId);
	}

	/**
	 * Returns a range of workflow metrics sla conditions matching the UUID and company.
	 *
	 * @param uuid the UUID of the workflow metrics sla conditions
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of workflow metrics sla conditions
	 * @param end the upper bound of the range of workflow metrics sla conditions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching workflow metrics sla conditions, or an empty list if no matches were found
	 */
	@Override
	public List<WorkflowMetricsSLACondition>
		getWorkflowMetricsSLAConditionsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			OrderByComparator<WorkflowMetricsSLACondition> orderByComparator) {

		return workflowMetricsSLAConditionPersistence.findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the workflow metrics sla condition matching the UUID and group.
	 *
	 * @param uuid the workflow metrics sla condition's UUID
	 * @param groupId the primary key of the group
	 * @return the matching workflow metrics sla condition
	 * @throws PortalException if a matching workflow metrics sla condition could not be found
	 */
	@Override
	public WorkflowMetricsSLACondition
			getWorkflowMetricsSLAConditionByUuidAndGroupId(
				String uuid, long groupId)
		throws PortalException {

		return workflowMetricsSLAConditionPersistence.findByUUID_G(
			uuid, groupId);
	}

	/**
	 * Returns a range of all the workflow metrics sla conditions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.metrics.model.impl.WorkflowMetricsSLAConditionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of workflow metrics sla conditions
	 * @param end the upper bound of the range of workflow metrics sla conditions (not inclusive)
	 * @return the range of workflow metrics sla conditions
	 */
	@Override
	public List<WorkflowMetricsSLACondition> getWorkflowMetricsSLAConditions(
		int start, int end) {

		return workflowMetricsSLAConditionPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of workflow metrics sla conditions.
	 *
	 * @return the number of workflow metrics sla conditions
	 */
	@Override
	public int getWorkflowMetricsSLAConditionsCount() {
		return workflowMetricsSLAConditionPersistence.countAll();
	}

	/**
	 * Updates the workflow metrics sla condition in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param workflowMetricsSLACondition the workflow metrics sla condition
	 * @return the workflow metrics sla condition that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public WorkflowMetricsSLACondition updateWorkflowMetricsSLACondition(
		WorkflowMetricsSLACondition workflowMetricsSLACondition) {

		return workflowMetricsSLAConditionPersistence.update(
			workflowMetricsSLACondition);
	}

	/**
	 * Returns the workflow metrics sla calendar local service.
	 *
	 * @return the workflow metrics sla calendar local service
	 */
	public com.liferay.portal.workflow.metrics.service.
		WorkflowMetricsSLACalendarLocalService
			getWorkflowMetricsSLACalendarLocalService() {

		return workflowMetricsSLACalendarLocalService;
	}

	/**
	 * Sets the workflow metrics sla calendar local service.
	 *
	 * @param workflowMetricsSLACalendarLocalService the workflow metrics sla calendar local service
	 */
	public void setWorkflowMetricsSLACalendarLocalService(
		com.liferay.portal.workflow.metrics.service.
			WorkflowMetricsSLACalendarLocalService
				workflowMetricsSLACalendarLocalService) {

		this.workflowMetricsSLACalendarLocalService =
			workflowMetricsSLACalendarLocalService;
	}

	/**
	 * Returns the workflow metrics sla calendar persistence.
	 *
	 * @return the workflow metrics sla calendar persistence
	 */
	public WorkflowMetricsSLACalendarPersistence
		getWorkflowMetricsSLACalendarPersistence() {

		return workflowMetricsSLACalendarPersistence;
	}

	/**
	 * Sets the workflow metrics sla calendar persistence.
	 *
	 * @param workflowMetricsSLACalendarPersistence the workflow metrics sla calendar persistence
	 */
	public void setWorkflowMetricsSLACalendarPersistence(
		WorkflowMetricsSLACalendarPersistence
			workflowMetricsSLACalendarPersistence) {

		this.workflowMetricsSLACalendarPersistence =
			workflowMetricsSLACalendarPersistence;
	}

	/**
	 * Returns the workflow metrics sla condition local service.
	 *
	 * @return the workflow metrics sla condition local service
	 */
	public WorkflowMetricsSLAConditionLocalService
		getWorkflowMetricsSLAConditionLocalService() {

		return workflowMetricsSLAConditionLocalService;
	}

	/**
	 * Sets the workflow metrics sla condition local service.
	 *
	 * @param workflowMetricsSLAConditionLocalService the workflow metrics sla condition local service
	 */
	public void setWorkflowMetricsSLAConditionLocalService(
		WorkflowMetricsSLAConditionLocalService
			workflowMetricsSLAConditionLocalService) {

		this.workflowMetricsSLAConditionLocalService =
			workflowMetricsSLAConditionLocalService;
	}

	/**
	 * Returns the workflow metrics sla condition persistence.
	 *
	 * @return the workflow metrics sla condition persistence
	 */
	public WorkflowMetricsSLAConditionPersistence
		getWorkflowMetricsSLAConditionPersistence() {

		return workflowMetricsSLAConditionPersistence;
	}

	/**
	 * Sets the workflow metrics sla condition persistence.
	 *
	 * @param workflowMetricsSLAConditionPersistence the workflow metrics sla condition persistence
	 */
	public void setWorkflowMetricsSLAConditionPersistence(
		WorkflowMetricsSLAConditionPersistence
			workflowMetricsSLAConditionPersistence) {

		this.workflowMetricsSLAConditionPersistence =
			workflowMetricsSLAConditionPersistence;
	}

	/**
	 * Returns the workflow metrics sla definition local service.
	 *
	 * @return the workflow metrics sla definition local service
	 */
	public com.liferay.portal.workflow.metrics.service.
		WorkflowMetricsSLADefinitionLocalService
			getWorkflowMetricsSLADefinitionLocalService() {

		return workflowMetricsSLADefinitionLocalService;
	}

	/**
	 * Sets the workflow metrics sla definition local service.
	 *
	 * @param workflowMetricsSLADefinitionLocalService the workflow metrics sla definition local service
	 */
	public void setWorkflowMetricsSLADefinitionLocalService(
		com.liferay.portal.workflow.metrics.service.
			WorkflowMetricsSLADefinitionLocalService
				workflowMetricsSLADefinitionLocalService) {

		this.workflowMetricsSLADefinitionLocalService =
			workflowMetricsSLADefinitionLocalService;
	}

	/**
	 * Returns the workflow metrics sla definition persistence.
	 *
	 * @return the workflow metrics sla definition persistence
	 */
	public WorkflowMetricsSLADefinitionPersistence
		getWorkflowMetricsSLADefinitionPersistence() {

		return workflowMetricsSLADefinitionPersistence;
	}

	/**
	 * Sets the workflow metrics sla definition persistence.
	 *
	 * @param workflowMetricsSLADefinitionPersistence the workflow metrics sla definition persistence
	 */
	public void setWorkflowMetricsSLADefinitionPersistence(
		WorkflowMetricsSLADefinitionPersistence
			workflowMetricsSLADefinitionPersistence) {

		this.workflowMetricsSLADefinitionPersistence =
			workflowMetricsSLADefinitionPersistence;
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
		persistedModelLocalServiceRegistry.register(
			"com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACondition",
			workflowMetricsSLAConditionLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACondition");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return WorkflowMetricsSLAConditionLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return WorkflowMetricsSLACondition.class;
	}

	protected String getModelClassName() {
		return WorkflowMetricsSLACondition.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource =
				workflowMetricsSLAConditionPersistence.getDataSource();

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
		type = com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLACalendarLocalService.class
	)
	protected com.liferay.portal.workflow.metrics.service.
		WorkflowMetricsSLACalendarLocalService
			workflowMetricsSLACalendarLocalService;

	@BeanReference(type = WorkflowMetricsSLACalendarPersistence.class)
	protected WorkflowMetricsSLACalendarPersistence
		workflowMetricsSLACalendarPersistence;

	@BeanReference(type = WorkflowMetricsSLAConditionLocalService.class)
	protected WorkflowMetricsSLAConditionLocalService
		workflowMetricsSLAConditionLocalService;

	@BeanReference(type = WorkflowMetricsSLAConditionPersistence.class)
	protected WorkflowMetricsSLAConditionPersistence
		workflowMetricsSLAConditionPersistence;

	@BeanReference(
		type = com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLADefinitionLocalService.class
	)
	protected com.liferay.portal.workflow.metrics.service.
		WorkflowMetricsSLADefinitionLocalService
			workflowMetricsSLADefinitionLocalService;

	@BeanReference(type = WorkflowMetricsSLADefinitionPersistence.class)
	protected WorkflowMetricsSLADefinitionPersistence
		workflowMetricsSLADefinitionPersistence;

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

	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;

	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry
		persistedModelLocalServiceRegistry;

}