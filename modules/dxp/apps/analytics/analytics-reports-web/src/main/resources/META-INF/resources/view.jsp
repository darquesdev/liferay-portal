<%--
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
--%>

<%@ include file="/init.jsp" %>

<div class="lfr-analytics-reports-sidebar" id="analyticsReportsSidebar">
	<div class="sidebar-header">
		<h1 class="sr-only"><liferay-ui:message key="analytics-reports-panel" /></h1>

		<span><liferay-ui:message key="analytics-reports" /></span>

		<aui:icon cssClass="icon-monospaced sidenav-close" image="times" markupView="lexicon" url="javascript:;" />
	</div>

	<div class="sidebar-body">
		<liferay-util:include page="/analytics_reports_panel.jsp" servletContext="<%= application %>" />
	</div>
</div>