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

<%
EditSegmentsEntryDisplayContext editSegmentsEntryDisplayContext = (EditSegmentsEntryDisplayContext)request.getAttribute(SegmentsWebKeys.EDIT_SEGMENTS_ENTRY_DISPLAY_CONTEXT);

String redirect = ParamUtil.getString(request, "redirect", editSegmentsEntryDisplayContext.getRedirect());

String backURL = ParamUtil.getString(request, "backURL", redirect);

Criteria criteria = editSegmentsEntryDisplayContext.getCriteria();

List<SegmentsCriteriaContributor> segmentsCriteriaContributors = editSegmentsEntryDisplayContext.getSegmentsCriteriaContributors();

SegmentsEntry segmentsEntry = editSegmentsEntryDisplayContext.getSegmentsEntry();

long segmentsEntryId = editSegmentsEntryDisplayContext.getSegmentsEntryId();

String type = editSegmentsEntryDisplayContext.getType();

if (Validator.isNotNull(backURL)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);
}

renderResponse.setTitle(editSegmentsEntryDisplayContext.getTitle(locale));
%>

<liferay-util:include page="/edit_segments_entry_tabs.jsp" servletContext="<%= application %>" />

<portlet:actionURL name="updateSegmentsEntry" var="updateSegmentsEntryActionURL" />

<aui:form action="<%= updateSegmentsEntryActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveSegmentsEntry();" %>'>
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="segmentsEntryId" type="hidden" value="<%= segmentsEntryId %>" />
	<aui:input name="type" type="hidden" value="<%= type %>" />
	<aui:input name="saveAndContinue" type="hidden" />

	<div class="lfr-form-content">
		<aui:model-context bean="<%= segmentsEntry %>" model="<%= SegmentsEntry.class %>" />

		<liferay-ui:error exception="<%= SegmentsEntryCriteriaException.class %>" message="invalid-criteria" />
		<liferay-ui:error exception="<%= SegmentsEntryKeyException.class %>" message="key-is-already-used" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input autoFocus="<%= true %>" name="name" required="<%= true %>" />

				<aui:input cssClass="lfr-textarea-container" name="description" />

				<aui:input name="key" required="<%= true %>" />

				<aui:input checked="<%= (segmentsEntry == null) ? false : segmentsEntry.isActive() %>" name="active" type="toggle-switch" />

				<aui:input checked="<%= (segmentsEntry != null) && Validator.isNotNull(segmentsEntry.getFilter()) %>" disabled="<%= segmentsEntry != null %>" name="dynamic" type="toggle-switch" />

				<div id="<portlet:namespace />criteriaWrapper">

					<%
					for (int i = 0; i < segmentsCriteriaContributors.size(); i++) {
						SegmentsCriteriaContributor segmentsCriteriaContributor = segmentsCriteriaContributors.get(i);

						Criteria.Criterion criterion = segmentsCriteriaContributor.getCriterion(criteria);

						if (i > 0) {
					%>

							<aui:select label="" name='<%= "criterionConjunction" + segmentsCriteriaContributor.getKey() %>'>

								<%
								for (Criteria.Conjunction conjunction : Criteria.Conjunction.values()) {
								%>

									<aui:option label="<%= conjunction.getValue() %>" selected="<%= (criterion != null) && conjunction.equals(criterion.getConjunction()) %>" />

								<%
								}
								%>

							</aui:select>

						<%
						}
						%>

						<aui:input label="<%= segmentsCriteriaContributor.getLabel(locale) %>" name='<%= "criterionFilter" + segmentsCriteriaContributor.getKey() %>' type="textarea" value="<%= (criterion != null) ? criterion.getFilter() : StringPool.BLANK %>" />

					<%
					}
					%>

				</div>
			</aui:fieldset>
		</aui:fieldset-group>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" onClick='<%= renderResponse.getNamespace() + "saveSegmentsEntryAndContinue();" %>' value="save-and-continue" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	Liferay.Util.toggleBoxes('<portlet:namespace />dynamic', '<portlet:namespace />criteriaWrapper');
</aui:script>

<aui:script>
	function <portlet:namespace />saveSegmentsEntry() {
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />saveSegmentsEntryAndContinue() {
		document.<portlet:namespace />fm.<portlet:namespace />saveAndContinue.value = 'true';

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>

<c:if test="<%= segmentsEntry == null %>">
	<aui:script sandbox="<%= true %>">
		var form = $(document.<portlet:namespace />fm);

		var keyInput = form.fm('key');
		var nameInput = form.fm('name');

		var onNameInput = _.debounce(
			function(event) {
				keyInput.val(nameInput.val());
			},
			200
		);

		nameInput.on('input', onNameInput);
	</aui:script>
</c:if>