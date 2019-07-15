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

package com.liferay.segments.experiment.web.internal.product.navigation.control.menu;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.control.menu.BaseProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.taglib.aui.IconTag;
import com.liferay.taglib.aui.ScriptTag;
import com.liferay.taglib.ui.MessageTag;
import com.liferay.taglib.util.BodyBottomTag;

import java.io.IOException;
import java.io.Writer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = {
		"product.navigation.control.menu.category.key=" + ProductNavigationControlMenuCategoryKeys.USER,
		"product.navigation.control.menu.entry.order:Integer=500"
	},
	service = ProductNavigationControlMenuEntry.class
)
public class SegmentsExperimentProductNavigationControlMenuEntry
	extends BaseProductNavigationControlMenuEntry {

	@Activate
	public void activate() {
		_portletNamespace = _portal.getPortletNamespace(
			SegmentsPortletKeys.SEGMENTS_EXPERIMENT);
	}

	@Override
	public String getLabel(Locale locale) {
		return null;
	}

	@Override
	public String getURL(HttpServletRequest httpServletRequest) {
		return null;
	}

	@Override
	public boolean includeBody(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		BodyBottomTag bodyBottomTag = new BodyBottomTag();

		bodyBottomTag.setOutputKey("segmentsExperimentPanelURL");

		try {
			bodyBottomTag.doBodyTag(
				httpServletRequest, httpServletResponse,
				this::_processBodyBottomTagBody);
		}
		catch (JspException je) {
			throw new IOException(je);
		}

		return true;
	}

	@Override
	public boolean includeIcon(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		PortletURL segmentsExperimentPanelURL = _portletURLFactory.create(
			httpServletRequest, SegmentsPortletKeys.SEGMENTS_EXPERIMENT,
			PortletRequest.RENDER_PHASE);

		try {
			segmentsExperimentPanelURL.setWindowState(
				LiferayWindowState.EXCLUSIVE);
		}
		catch (WindowStateException wse) {
			ReflectionUtil.throwException(wse);
		}

		Map<String, String> values = new HashMap<>();

		IconTag iconTag = new IconTag();

		iconTag.setCssClass("icon-monospaced");
		iconTag.setImage("star-half");
		iconTag.setMarkupView("lexicon");

		try {
			values.put(
				"iconTag",
				iconTag.doTagAsString(httpServletRequest, httpServletResponse));
		}
		catch (JspException je) {
			ReflectionUtil.throwException(je);
		}

		values.put("portletNamespace", _portletNamespace);
		values.put(
			"segmentsExperimentPanelURL",
			segmentsExperimentPanelURL.toString());
		values.put(
			"title", _html.escape(_language.get(httpServletRequest, "ab")));

		Writer writer = httpServletResponse.getWriter();

		writer.write(StringUtil.replace(_ICON_TMPL_CONTENT, "${", "}", values));

		return true;
	}

	@Override
	public boolean isShow(HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (layout.isTypeControlPanel()) {
			return false;
		}

		if (isEmbeddedPersonalApplicationLayout(layout)) {
			return false;
		}

		String layoutMode = ParamUtil.getString(
			httpServletRequest, "p_l_mode", Constants.VIEW);

		if (layoutMode.equals(Constants.EDIT)) {
			return false;
		}

		return super.isShow(httpServletRequest);
	}

	private void _processBodyBottomTagBody(PageContext pageContext) {
		try {
			Map<String, String> values = new HashMap<>();

			values.put("portletNamespace", _portletNamespace);

			MessageTag messageTag = new MessageTag();

			messageTag.setKey("ab");

			values.put("sidebarMessage", messageTag.doTagAsString(pageContext));

			messageTag = new MessageTag();

			messageTag.setKey("ab-panel");

			values.put(
				"segmentsExperimentPanel",
				messageTag.doTagAsString(pageContext));

			IconTag iconTag = new IconTag();

			iconTag.setCssClass("icon-monospaced sidenav-close");
			iconTag.setImage("times");
			iconTag.setMarkupView("lexicon");
			iconTag.setUrl("javascript:;");

			values.put("sidebarIcon", iconTag.doTagAsString(pageContext));

			Writer writer = pageContext.getOut();

			writer.write(
				StringUtil.replace(_BODY_TMPL_CONTENT, "${", "}", values));

			ScriptTag scriptTag = new ScriptTag();

			scriptTag.setUse("liferay-store,io-request,parse-content");

			scriptTag.doBodyTag(pageContext, this::_processScriptTagBody);
		}
		catch (Exception e) {
			ReflectionUtil.throwException(e);
		}
	}

	private void _processScriptTagBody(PageContext pageContext) {
		Writer writer = pageContext.getOut();

		try {
			writer.write(
				StringUtil.replace(
					_BODY_SCRIPT_TMPL_CONTENT, "${", "}",
					Collections.singletonMap(
						"portletNamespace", _portletNamespace)));
		}
		catch (IOException ioe) {
			ReflectionUtil.throwException(ioe);
		}
	}

	private static final String _BODY_SCRIPT_TMPL_CONTENT = StringUtil.read(
		SegmentsExperimentProductNavigationControlMenuEntry.class,
		"body_script.tmpl");

	private static final String _BODY_TMPL_CONTENT = StringUtil.read(
		SegmentsExperimentProductNavigationControlMenuEntry.class, "body.tmpl");

	private static final String _ICON_TMPL_CONTENT = StringUtil.read(
		SegmentsExperimentProductNavigationControlMenuEntry.class, "icon.tmpl");

	@Reference
	private Html _html;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	private String _portletNamespace;

	@Reference
	private PortletURLFactory _portletURLFactory;

}