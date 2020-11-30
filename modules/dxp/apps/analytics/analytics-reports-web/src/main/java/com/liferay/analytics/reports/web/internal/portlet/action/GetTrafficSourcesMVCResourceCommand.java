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

package com.liferay.analytics.reports.web.internal.portlet.action;

import com.liferay.analytics.reports.web.internal.constants.AnalyticsReportsPortletKeys;
import com.liferay.analytics.reports.web.internal.data.provider.AnalyticsReportsDataProvider;
import com.liferay.analytics.reports.web.internal.info.display.contributor.util.LayoutDisplayPageProviderUtil;
import com.liferay.analytics.reports.web.internal.layout.seo.CanonicalURLProvider;
import com.liferay.analytics.reports.web.internal.model.ReferralTrafficDetails;
import com.liferay.analytics.reports.web.internal.model.ReferringURL;
import com.liferay.analytics.reports.web.internal.model.TrafficSource;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
import com.liferay.layout.seo.kernel.LayoutSEOLinkManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AnalyticsReportsPortletKeys.ANALYTICS_REPORTS,
		"mvc.command.name=/analytics_reports/get_traffic_sources"
	},
	service = MVCResourceCommand.class
)
public class GetTrafficSourcesMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			themeDisplay.getLocale(), getClass());

		try {
			HttpServletRequest httpServletRequest =
				_portal.getHttpServletRequest(resourceRequest);

			LayoutDisplayPageObjectProvider<Object>
				layoutDisplayPageObjectProvider =
					(LayoutDisplayPageObjectProvider<Object>)
						LayoutDisplayPageProviderUtil.
							getLayoutDisplayPageObjectProvider(
								httpServletRequest,
								_layoutDisplayPageProviderTracker, _portal);

			if (layoutDisplayPageObjectProvider == null) {
				JSONPortletResponseUtil.writeJSON(
					resourceRequest, resourceResponse,
					JSONUtil.put(
						"error",
						_language.get(
							httpServletRequest,
							"an-unexpected-error-occurred")));

				return;
			}

			AnalyticsReportsDataProvider analyticsReportsDataProvider =
				new AnalyticsReportsDataProvider(_http);
			CanonicalURLProvider canonicalURLProvider =
				new CanonicalURLProvider(
					_portal.getHttpServletRequest(resourceRequest),
					_layoutSEOLinkManager, _portal);

			JSONObject jsonObject = JSONUtil.put(
				"trafficSources",
				_getTrafficSourcesJSONArray(
					analyticsReportsDataProvider, themeDisplay.getCompanyId(),
					canonicalURLProvider.getCanonicalURL(),
					themeDisplay.getLocale(), resourceBundle));

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse, jsonObject);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"error",
					ResourceBundleUtil.getString(
						resourceBundle, "an-unexpected-error-occurred")));
		}
	}

	private ReferralTrafficDetails _getReferralTrafficDetails() {
		return new ReferralTrafficDetails(
			Arrays.asList(
				new ReferringURL(17985230, "http://youtube.com/"),
				new ReferringURL(12218030, "http://www.google.com/"),
				new ReferringURL(9062949, "http://microsoft.com/"),
				new ReferringURL(4601453, "http://linkedin.com/"),
				new ReferringURL(253399, "https://www.liferay.com")),
			Arrays.asList(
				new ReferringURL(
					125461,
					"https://www.liferay.com/resources/ebooks/Becoming+a+" +
						"Digital+Business-4+Common+Enterprise+Challenges+" +
							"Conquered"),
				new ReferringURL(
					85485,
					"https://www.liferay.com/resources/whitepapers/B2B+E-" +
						"Commerce+RFP+Kit"),
				new ReferringURL(
					84564,
					"https://www.liferay.com/resources/whitepapers/6+Tactics+" +
						"to+Modernize+Your+Intranet"),
				new ReferringURL(
					5846,
					"https://www.liferay.com/resources/case-studies/materion-" +
						"case-study"),
				new ReferringURL(
					3521,
					"https://www.liferay.com/web/l/a1-hrvatska-case-study"),
				new ReferringURL(
					2513,
					"https://www.liferay.com/resources/case-studies/excellus-" +
						"case-study"),
				new ReferringURL(
					2200,
					"https://www.liferay.com/resources/case-studies/terres-" +
						"inovia-case-study"),
				new ReferringURL(
					1230,
					"https://www.liferay.com/resources/case-studies/vodafone-" +
						"business"),
				new ReferringURL(
					1100,
					"https://www.liferay.com/web/guest/resources/case-" +
						"studies/agia"),
				new ReferringURL(
					100,
					"https://www.liferay.com/resources/case-studies/vitality-" +
						"case-study")));
	}

	private List<TrafficSource> _getTrafficSources(
		AnalyticsReportsDataProvider analyticsReportsDataProvider,
		String canonicalURL, long companyId) {

		Map<String, TrafficSource> emptyMap = HashMapBuilder.put(
			"direct", new TrafficSource(Collections.emptyList(), "direct", 0, 0)
		).put(
			"organic",
			new TrafficSource(Collections.emptyList(), "organic", 0, 0)
		).put(
			"paid", new TrafficSource(Collections.emptyList(), "paid", 0, 0)
		).put(
			"referral",
			new TrafficSource(Collections.emptyList(), "referral", 0, 0)
		).put(
			"social", new TrafficSource(Collections.emptyList(), "social", 0, 0)
		).build();

		if (!analyticsReportsDataProvider.isValidAnalyticsConnection(
				companyId)) {

			return new ArrayList<>(emptyMap.values());
		}

		try {
			Map<String, TrafficSource> trafficSources =
				analyticsReportsDataProvider.getTrafficSources(
					companyId, canonicalURL);

			emptyMap.forEach(
				(name, trafficSource) -> trafficSources.merge(
					name, trafficSource,
					(trafficSource1, trafficSource2) -> trafficSource1));

			return new ArrayList<>(trafficSources.values());
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return Arrays.asList(
				new TrafficSource("direct"), new TrafficSource("organic"),
				new TrafficSource("paid"), new TrafficSource("referral"),
				new TrafficSource("social"));
		}
	}

	private JSONArray _getTrafficSourcesJSONArray(
		AnalyticsReportsDataProvider analyticsReportsDataProvider,
		long companyId, String canonicalURL, Locale locale,
		ResourceBundle resourceBundle) {

		Map<String, String> helpMessageMap = HashMapBuilder.put(
			"direct",
			ResourceBundleUtil.getString(
				resourceBundle,
				"this-is-the-number-of-page-views-generated-by-people-" +
					"arriving-directly-to-your-page")
		).put(
			"organic",
			ResourceBundleUtil.getString(
				resourceBundle,
				"this-is-the-number-of-page-views-generated-by-people-coming-" +
					"from-a-search-engine")
		).put(
			"paid",
			ResourceBundleUtil.getString(
				resourceBundle,
				"this-is-the-number-of-page-views-generated-by-people-that-" +
					"find-your-page-through-google-adwords")
		).put(
			"referral",
			ResourceBundleUtil.getString(
				resourceBundle,
				"this-is-the-number-of-page-views-generated-by-people-coming-" +
					"to-your-page-from-other-sites-which-are-not-search-" +
						"engine-pages-or-social-sites")
		).put(
			"social",
			ResourceBundleUtil.getString(
				resourceBundle,
				"this-is-the-number-of-page-views-generated-by-people-coming-" +
					"to-your-page-from-social-sites")
		).build();

		List<TrafficSource> trafficSources = _getTrafficSources(
			analyticsReportsDataProvider, canonicalURL, companyId);

		Stream<TrafficSource> stream = trafficSources.stream();

		Comparator<TrafficSource> comparator = Comparator.comparing(
			TrafficSource::getTrafficShare);

		JSONArray trafficSourcesJSONArray = JSONUtil.putAll(
			stream.sorted(
				comparator.reversed()
			).map(
				trafficSource -> trafficSource.toJSONObject(
					helpMessageMap.get(trafficSource.getName()), locale,
					ResourceBundleUtil.getString(
						resourceBundle, trafficSource.getName()))
			).toArray());

		return _mockReferralTraffic(trafficSourcesJSONArray);
	}

	private JSONArray _mockReferralTraffic(JSONArray trafficSourcesJSONArray) {
		Iterator<JSONObject> iterator = trafficSourcesJSONArray.iterator();

		iterator.forEachRemaining(
			jsonObject -> {
				if (Objects.equals("referral", jsonObject.get("name"))) {
					ReferralTrafficDetails referralTrafficDetails =
						_getReferralTrafficDetails();

					jsonObject.put(
						"details", referralTrafficDetails.toJSONObject());

					jsonObject.put("share", String.format("%.1f", 64.5D));

					jsonObject.put("value", 24225897);
				}
			});

		return trafficSourcesJSONArray;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetTrafficSourcesMVCResourceCommand.class);

	@Reference
	private Http _http;

	@Reference
	private Language _language;

	@Reference
	private LayoutDisplayPageProviderTracker _layoutDisplayPageProviderTracker;

	@Reference
	private LayoutSEOLinkManager _layoutSEOLinkManager;

	@Reference
	private Portal _portal;

}