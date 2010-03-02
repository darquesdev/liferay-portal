<%
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
%>

<%@ include file="/html/portlet/shopping/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

ShoppingOrder order = (ShoppingOrder)request.getAttribute(WebKeys.SHOPPING_ORDER);

order = order.toEscapedModel();

long orderId = BeanParamUtil.getLong(order, request, "orderId");
%>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/shopping/edit_order" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escapeAttribute(currentURL) %>" />
<input name="<portlet:namespace />orderId" type="hidden" value="<%= orderId %>" />
<input name="<portlet:namespace />number" type="hidden" value="<%= HtmlUtil.escapeAttribute(order.getNumber()) %>" />
<input name="<portlet:namespace />emailType" type="hidden" value="" />
<input name="<portlet:namespace />deleteOrderIds" type="hidden" value="<%= orderId %>" />

<c:choose>
	<c:when test="<%= windowState.equals(LiferayWindowState.POP_UP) %>">
		<a href="<%= themeDisplay.getURLHome() %>"><img alt="<liferay-ui:message key="logo" />" src="<%= themeDisplay.getCompanyLogo() %>" /></a>

		<br /><br />

		<span style="font-size: small;">
		<strong><liferay-ui:message key="invoice" /></strong>
		</span>

		<br /><br />
	</c:when>
	<c:otherwise>
		<liferay-ui:tabs
			names="order"
			backURL="<%= PortalUtil.escapeRedirect(redirect) %>"
		/>
	</c:otherwise>
</c:choose>

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="order" /> #:
	</td>
	<td>
		<strong><%= order.getNumber() %></strong>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="order-date" />:
	</td>
	<td>
		<%= dateFormatDateTime.format(order.getCreateDate()) %>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="last-modified" />:
	</td>
	<td>
		<%= dateFormatDateTime.format(order.getModifiedDate()) %>
	</td>
</tr>
</table>

<br />

<table class="lfr-table">
<tr>
	<td>
		<strong><liferay-ui:message key="billing-address" /></strong>

		<br /><br />

		<table class="lfr-table">
		<tr>
			<td>
				<liferay-ui:message key="first-name" />:
			</td>
			<td>
				<%= order.getBillingFirstName() %>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="last-name" />:
			</td>
			<td>
				<%= order.getBillingLastName() %>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="email-address" />:
			</td>
			<td>
				<%= order.getBillingEmailAddress() %>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="company" />:
			</td>
			<td>
				<%= order.getBillingCompany() %>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="street" />:
			</td>
			<td>
				<%= order.getBillingStreet() %>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="city" />
			</td>
			<td>
				<%= order.getBillingCity() %>:
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="state" />:
			</td>
			<td>
				<%= order.getBillingState() %>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="zip" />:
			</td>
			<td>
				<%= order.getBillingZip() %>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="country" />:
			</td>
			<td>
				<%= order.getBillingCountry() %>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="phone" />:
			</td>
			<td>
				<%= order.getBillingPhone() %>
			</td>
		</tr>
		</table>
	</td>
	<td class="lfr-top">
		<strong><liferay-ui:message key="shipping-address" /></strong>

		<br /><br />

		<table class="lfr-table">
		<tr>
			<td>
				<liferay-ui:message key="first-name" />:
			</td>
			<td>
				<%= order.getShippingFirstName() %>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="last-name" />:
			</td>
			<td>
				<%= order.getShippingLastName() %>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="email-address" />:
			</td>
			<td>
				<%= order.getShippingEmailAddress() %>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="company" />:
			</td>
			<td>
				<%= order.getShippingCompany() %>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="street" />:
			</td>
			<td>
				<%= order.getShippingStreet() %>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="city" />
			</td>
			<td>
				<%= order.getShippingCity() %>:
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="state" />:
			</td>
			<td>
				<%= order.getShippingState() %>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="zip" />:
			</td>
			<td>
				<%= order.getShippingZip() %>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="country" />:
			</td>
			<td>
				<%= order.getShippingCountry() %>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="phone" />:
			</td>
			<td>
				<%= order.getShippingPhone() %>
			</td>
		</tr>
		</table>
	</td>
</tr>
</table>

<br />

<c:choose>
	<c:when test="<%= shoppingPrefs.usePayPal() %>">
		<strong>PayPal</strong>

		<br /><br />

		<table class="lfr-table">
		<tr>
			<td>
				<liferay-ui:message key="status" />:
			</td>
			<td>
				<select name="<portlet:namespace />ppPaymentStatus">

					<%
					for (int i = 0; i < ShoppingOrderConstants.STATUSES.length; i++) {
					%>

						<option <%= ShoppingUtil.getPpPaymentStatus(ShoppingOrderConstants.STATUSES[i]).equals(order.getPpPaymentStatus()) ? "selected" : "" %> value="<%= ShoppingOrderConstants.STATUSES[i] %>"><%= LanguageUtil.get(pageContext, ShoppingOrderConstants.STATUSES[i]) %></option>

					<%
					}
					%>

				</select>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="transaction-id" />:
			</td>
			<td>
				<liferay-ui:input-field model="<%= ShoppingOrder.class %>" bean="<%= order %>" field="ppTxnId" />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="payment-gross" />:
			</td>
			<td>
				<liferay-ui:input-field model="<%= ShoppingOrder.class %>" bean="<%= order %>" field="ppPaymentGross" defaultValue="<%= doubleFormat.format(order.getPpPaymentGross()) %>" />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="receiver-email-address" />:
			</td>
			<td>
				<liferay-ui:input-field model="<%= ShoppingOrder.class %>" bean="<%= order %>" field="ppReceiverEmail" />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="payer-email-address" />:
			</td>
			<td>
				<liferay-ui:input-field model="<%= ShoppingOrder.class %>" bean="<%= order %>" field="ppPayerEmail" />
			</td>
		</tr>
		<c:if test="<%= order.getPpPaymentStatus().equals(ShoppingOrderConstants.STATUS_CHECKOUT) %>">
			<tr>
				<td>
					<liferay-ui:message key="paypal-order" />:
				</td>
				<td>

					<%
					String payPalLinkOpen = "<a href=\"" + ShoppingUtil.getPayPalRedirectURL(shoppingPrefs, order, ShoppingUtil.calculateTotal(order), ShoppingUtil.getPayPalReturnURL(renderResponse.createActionURL(), order), ShoppingUtil.getPayPalNotifyURL(themeDisplay)) + "\"><strong><u>";
					String payPalLinkClose = "</u></strong></a>";
					%>

					<%= LanguageUtil.format(pageContext, "please-complete-your-order", new Object[] {payPalLinkOpen, payPalLinkClose}, false) %>
				</td>
			</tr>
		</c:if>
		</table>
	</c:when>
	<c:otherwise>
		<strong><liferay-ui:message key="credit-card" /></strong>

		<br /><br />

		<table class="lfr-table">
		<tr>
			<td>
				<liferay-ui:message key="full-name" />:
			</td>
			<td>
				<%= order.getCcName() %>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="type" />:
			</td>
			<td>
				<liferay-ui:message key='<%= "cc_" + order.getCcType() %>' />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="number" />:
			</td>
			<td>
				<%= CreditCard.hide(order.getCcNumber()) %>
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="expiration-date" />:
			</td>
			<td>
				<%= CalendarUtil.getMonths(locale)[order.getCcExpMonth()] %>, <%= order.getCcExpYear() %>
			</td>
		</tr>

		<c:if test="<%= Validator.isNotNull(order.getCcVerNumber()) %>">
			<tr>
				<td>
					<liferay-ui:message key="verification-number" />:
				</td>
				<td>
					<%= order.getCcVerNumber() %>
				</td>
			</tr>
		</c:if>

		</table>
	</c:otherwise>
</c:choose>

<br />

<c:if test="<%= Validator.isNotNull(order.getComments()) %>">
	<strong><liferay-ui:message key="comments" /></strong>

	<br /><br />

	<%= order.getComments() %>

	<br /><br />
</c:if>

<%
StringBuilder itemIds = new StringBuilder();

SearchContainer searchContainer = new SearchContainer();

List<String> headerNames = new ArrayList<String>();

headerNames.add("sku");
headerNames.add("description");
headerNames.add("quantity");
headerNames.add("price");
headerNames.add("total");

searchContainer.setHeaderNames(headerNames);
searchContainer.setHover(false);

List results = ShoppingOrderItemLocalServiceUtil.getOrderItems(order.getOrderId());
int total = results.size();

searchContainer.setTotal(total);

List resultRows = searchContainer.getResultRows();

Iterator itr = results.iterator();

for (int i = 0; itr.hasNext(); i++) {
	ShoppingOrderItem orderItem = (ShoppingOrderItem)itr.next();

	ShoppingItem item = null;

	try {
		item = ShoppingItemLocalServiceUtil.getItem(ShoppingUtil.getItemId(orderItem.getItemId()));
	}
	catch (Exception e) {
	}

	String[] fieldsArray = StringUtil.split(ShoppingUtil.getItemFields(orderItem.getItemId()), "&");

	int quantity = orderItem.getQuantity();

	ResultRow row = new ResultRow(item, orderItem.getOrderItemId(), i);

	PortletURL rowURL = null;

	if (item != null) {
		rowURL = renderResponse.createRenderURL();

		rowURL.setParameter("struts_action", "/shopping/view_item");
		rowURL.setParameter("itemId", String.valueOf(item.getItemId()));
	}

	// SKU

	row.addText(orderItem.getSku(), rowURL);

	// Description

	if (fieldsArray.length > 0) {
		StringBundler sb = new StringBundler(4);

		sb.append(orderItem.getName());
		sb.append(" (");
		sb.append(StringUtil.replace(StringUtil.merge(fieldsArray, ", "), "=", ": "));
		sb.append(")");

		row.addText(sb.toString(), rowURL);
	}
	else {
		row.addText(orderItem.getName(), rowURL);
	}

	// Quantity

	row.addText(String.valueOf(quantity), rowURL);

	// Price

	row.addText(currencyFormat.format(orderItem.getPrice()), rowURL);

	// Total

	row.addText(currencyFormat.format(orderItem.getPrice() * quantity), rowURL);

	// Add result row

	resultRows.add(row);
}
%>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

<br />

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="subtotal" />:
	</td>
	<td>
		<%= currencyFormat.format(ShoppingUtil.calculateActualSubtotal(results)) %>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="tax" />:
	</td>
	<td>
		<%= currencyFormat.format(order.getTax()) %>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="shipping" /> <%= Validator.isNotNull(order.getAltShipping()) ? "(" + order.getAltShipping() + ")" : StringPool.BLANK %>
	</td>
	<td>
		<%= currencyFormat.format(order.getShipping()) %>
	</td>
</tr>

<c:if test="<%= order.isInsure() %>">
	<tr>
		<td>
			<liferay-ui:message key="insurance" />:
		</td>
		<td>
			<%= currencyFormat.format(order.getInsurance()) %>
		</td>
	</tr>
</c:if>

<c:if test="<%= Validator.isNotNull(order.getCouponCodes()) %>">
	<tr>
		<td>
			<liferay-ui:message key="coupon-discount" />:
		</td>
		<td>
			<%= currencyFormat.format(order.getCouponDiscount()) %>

			<a href="javascript:var viewCouponWindow = window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/shopping/view_coupon" /><portlet:param name="code" value="<%= order.getCouponCodes() %>" /></portlet:renderURL>', 'viewCoupon', 'directories=no,height=200,location=no,menubar=no,resizable=no,scrollbars=yes,status=no,toolbar=no,width=280'); void(''); viewCouponWindow.focus();">
			(<%= order.getCouponCodes() %>)
			</a>
		</td>
	</tr>
</c:if>

<tr>
	<td>
		<liferay-ui:message key="total" />:
	</td>
	<td>
		<%= currencyFormat.format(ShoppingUtil.calculateTotal(order)) %>
	</td>
</tr>
</table>

<c:if test="<%= !windowState.equals(LiferayWindowState.POP_UP) %>">
	<br />

	<c:if test="<%= shoppingPrefs.usePayPal() %>">
		<input type="button" value="<liferay-ui:message key="save" />" onClick="<portlet:namespace />saveOrder();" />
	</c:if>

	<input type="button" value="<liferay-ui:message key="invoice" />" onClick="window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/shopping/edit_order" /><portlet:param name="orderId" value="<%= String.valueOf(orderId) %>" /></portlet:renderURL>');" />

	<input type="button" value='<%= LanguageUtil.get(pageContext, (order.isSendOrderEmail() ? "" : "re") + "send-confirmation-email") %>' onClick="<portlet:namespace />sendEmail('confirmation');" />

	<input type="button" value='<%= LanguageUtil.get(pageContext, (order.isSendShippingEmail() ? "" : "re") + "send-shipping-email") %>' onClick="<portlet:namespace />sendEmail('shipping');" />

	<c:if test="<%= ShoppingOrderPermission.contains(permissionChecker, scopeGroupId, order, ActionKeys.DELETE) %>">
		<input type="button" value="<liferay-ui:message key="delete" />" onClick="<portlet:namespace />deleteOrder();" />
	</c:if>

	<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(PortalUtil.escapeRedirect(redirect)) %>';" />
</c:if>

</form>

<c:if test="<%= !windowState.equals(LiferayWindowState.POP_UP) %>">
	<br />

	<liferay-ui:tabs names="comments" />

	<portlet:actionURL var="discussionURL">
		<portlet:param name="struts_action" value="/shopping/edit_order_discussion" />
	</portlet:actionURL>

	<liferay-ui:discussion
		className="<%= ShoppingOrder.class.getName() %>"
		classPK="<%= order.getOrderId() %>"
		formAction="<%= discussionURL %>"
		formName="fm2"
		redirect="<%= currentURL %>"
		subject="<%= order.getNumber() %>"
		userId="<%= order.getUserId() %>"
	/>
</c:if>

<aui:script>
	function <portlet:namespace />deleteOrder() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = "<%= HtmlUtil.escape(redirect) %>";
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />saveOrder() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />sendEmail(emailType) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "sendEmail";
		document.<portlet:namespace />fm.<portlet:namespace />emailType.value = emailType;
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>