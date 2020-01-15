<%--
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