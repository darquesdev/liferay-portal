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

import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItem;
import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItemTracker;
import com.liferay.analytics.reports.web.internal.constants.AnalyticsReportsPortletKeys;
import com.liferay.analytics.reports.web.internal.model.TimeRange;
import com.liferay.analytics.reports.web.internal.model.TimeSpan;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.time.format.DateTimeFormatter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AnalyticsReportsPortletKeys.ANALYTICS_REPORTS,
		"mvc.command.name=/analytics_reports/get_analytics_reports_panel"
	},
	service = MVCResourceCommand.class
)
public class GetAnalyticsReportsPanelMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		Locale locale = _portal.getLocale(resourceRequest);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		try {
			String className = ParamUtil.getString(
				resourceRequest, "className");

			long classPK = GetterUtil.getLong(
				ParamUtil.getLong(resourceRequest, "classPK"));

			AnalyticsReportsInfoItem<Object> analyticsReportsInfoItem =
				(AnalyticsReportsInfoItem<Object>)
					_analyticsReportsInfoItemTracker.
						getAnalyticsReportsInfoItem(className);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			if (analyticsReportsInfoItem != null) {
				jsonObject.put(
					"context",
					_getContext(
						true, true, 0, resourceResponse, locale, resourceBundle)
				).put(
					"props",
					_getAnalyticsReportsInfoItemObjectOptional(
						className, classPK
					).map(
						analyticsReportsInfoItemObject -> _getPropsJSONObject(
							analyticsReportsInfoItem,
							analyticsReportsInfoItemObject, locale)
					).orElse(
						JSONFactoryUtil.createJSONObject()
					)
				);
			}

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse, jsonObject);
		}
		catch (Exception exception) {
			if (_log.isInfoEnabled()) {
				_log.info(exception, exception);
			}

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"error",
					ResourceBundleUtil.getString(
						resourceBundle, "an-unexpected-error-occurred")));
		}
	}

	private Optional<Object> _getAnalyticsReportsInfoItemObjectOptional(
		String className, long classPK) {

		return Optional.ofNullable(
			(InfoDisplayContributor<Object>)
				_infoDisplayContributorTracker.getInfoDisplayContributor(
					className)
		).flatMap(
			objectInfoDisplayContributor -> {
				try {
					return Optional.ofNullable(
						objectInfoDisplayContributor.
							getInfoDisplayObjectProvider(classPK));
				}
				catch (PortalException portalException) {
					_log.error(
						"Unable to get info display object provider",
						portalException);

					return Optional.empty();
				}
			}
		).flatMap(
			objectInfoDisplayObjectProvider -> Optional.ofNullable(
				objectInfoDisplayObjectProvider.getDisplayObject())
		);
	}

	private Map<String, Object> _getContext(
		boolean validAnalyticsConnection, boolean readsEnabled, long plid,
		ResourceResponse resourceResponse, Locale locale,
		ResourceBundle resourceBundle) {

		return HashMapBuilder.<String, Object>put(
			"defaultTimeRange",
			() -> {
				TimeSpan defaultTimeSpan = TimeSpan.of(
					TimeSpan.defaultTimeSpanKey());

				TimeRange defaultTimeRange = defaultTimeSpan.toTimeRange(0);

				return HashMapBuilder.<String, Object>put(
					"endDate",
					DateTimeFormatter.ISO_DATE.format(
						defaultTimeRange.getEndLocalDate())
				).put(
					"startDate",
					DateTimeFormatter.ISO_DATE.format(
						defaultTimeRange.getStartLocalDate())
				).build();
			}
		).put(
			"defaultTimeSpanKey", TimeSpan.defaultTimeSpanKey()
		).put(
			"endpoints",
			HashMapBuilder.<String, Object>put(
				"getAnalyticsReportsHistoricalReadsURL",
				() -> {
					ResourceURL resourceURL =
						resourceResponse.createResourceURL();

					resourceURL.setResourceID(
						"/analytics_reports/get_historical_reads");

					return resourceURL.toString();
				}
			).put(
				"getAnalyticsReportsHistoricalViewsURL",
				() -> {
					ResourceURL resourceURL =
						resourceResponse.createResourceURL();

					resourceURL.setResourceID(
						"/analytics_reports/get_historical_views");

					return resourceURL.toString();
				}
			).put(
				"getAnalyticsReportsTotalReadsURL",
				() -> {
					ResourceURL resourceURL =
						resourceResponse.createResourceURL();

					resourceURL.setResourceID(
						"/analytics_reports/get_total_reads");

					return resourceURL.toString();
				}
			).put(
				"getAnalyticsReportsTotalViewsURL",
				() -> {
					ResourceURL resourceURL =
						resourceResponse.createResourceURL();

					resourceURL.setResourceID(
						"/analytics_reports/get_total_views");

					return resourceURL.toString();
				}
			).build()
		).put(
			"languageTag", locale.toLanguageTag()
		).put(
			"namespace",
			_portal.getPortletNamespace(
				AnalyticsReportsPortletKeys.ANALYTICS_REPORTS)
		).put(
			"page",
			HashMapBuilder.<String, Object>put(
				"plid", plid
			).build()
		).put(
			"readsEnabled", readsEnabled
		).put(
			"timeSpans", _getTimeSpansJSONArray(resourceBundle)
		).put(
			"validAnalyticsConnection", validAnalyticsConnection
		).build();
	}

	private JSONObject _getPropsJSONObject(
		AnalyticsReportsInfoItem<Object> analyticsReportsInfoItem,
		Object analyticsReportsInfoItemObject, Locale locale) {

		return JSONUtil.put(
			"authorName",
			analyticsReportsInfoItem.getAuthorName(
				analyticsReportsInfoItemObject)
		).put(
			"publishDate",
			analyticsReportsInfoItem.getPublishDate(
				analyticsReportsInfoItemObject)
		).put(
			"title",
			analyticsReportsInfoItem.getTitle(
				analyticsReportsInfoItemObject, locale)
		).put(
			"trafficSources", JSONFactoryUtil.createJSONArray()
		);
	}

	private JSONArray _getTimeSpansJSONArray(ResourceBundle resourceBundle) {
		JSONArray timeSpansJSONArray = JSONFactoryUtil.createJSONArray();

		Stream<TimeSpan> stream = Arrays.stream(TimeSpan.values());

		stream.filter(
			timeSpan -> timeSpan != TimeSpan.TODAY
		).sorted(
			Comparator.comparingInt(TimeSpan::getDays)
		).forEach(
			timeSpan -> timeSpansJSONArray.put(
				JSONUtil.put(
					"key", timeSpan.getKey()
				).put(
					"label",
					ResourceBundleUtil.getString(
						resourceBundle, timeSpan.getKey())
				))
		);

		return timeSpansJSONArray;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetAnalyticsReportsPanelMVCResourceCommand.class);

	@Reference
	private AnalyticsReportsInfoItemTracker _analyticsReportsInfoItemTracker;

	@Reference
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

	@Reference
	private Portal _portal;

}