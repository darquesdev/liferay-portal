/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import ClayList from '@clayui/list';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {Align} from 'metal-position';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {numberFormat} from '../../utils/numberFormat';
import Hint from '../Hint';
import TimeSpanSelector from '../TimeSpanSelector';
import TotalCount from '../TotalCount';

const MOCK_TITLE = '19 - Nov 25, 2020';

const ITEMS_TO_SHOW = 5;

export default function ReferralDetail({
	currentPage,
	languageTag,
	timeSpanOptions,
	trafficShareDataProvider,
	trafficVolumeDataProvider,
}) {
	const [isExpanded, setIsExpanded] = useState(false);

	const {details} = currentPage.data;
	const {referringDomains, referringPages} = details;

	return (
		<div className="c-p-3 traffic-source-detail">
			<div className="c-mb-3 c-mt-2">
				<TimeSpanSelector
					disabledNextTimeSpan={true}
					disabledPreviousPeriodButton={true}
					onNextTimeSpanClick={() => {}}
					onPreviousTimeSpanClick={() => {}}
					onTimeSpanChange={() => {}}
					timeSpanKey={0}
					timeSpanOptions={timeSpanOptions}
				/>
			</div>

			{MOCK_TITLE && <h5 className="c-mb-4">{MOCK_TITLE}</h5>}

			<TotalCount
				className="c-mb-2"
				dataProvider={trafficVolumeDataProvider}
				label={Liferay.Util.sub(Liferay.Language.get('traffic-volume'))}
				languageTag={languageTag}
				popoverAlign={Align.Bottom}
				popoverHeader={Liferay.Language.get('traffic-volume')}
				popoverMessage={Liferay.Language.get(
					'traffic-volume-is-the-number-of-page-views-coming-from-one-channel'
				)}
				popoverPosition="bottom"
			/>

			<TotalCount
				className="c-mb-3"
				dataProvider={trafficShareDataProvider}
				label={Liferay.Util.sub(Liferay.Language.get('traffic-share'))}
				percentage={true}
				popoverHeader={Liferay.Language.get('traffic-share')}
				popoverMessage={Liferay.Language.get(
					'traffic-share-is-the-percentage-of-traffic-sent-to-your-page-by-one-channel'
				)}
			/>

			<ClayList className="list-group-pages-list">
				<ClayList.Item flex>
					<ClayList.ItemField expand>
						<ClayList.ItemTitle className="text-truncate-inline">
							<span className="text-truncate">
								{Liferay.Language.get('top-referring-pages')}
								<span className="text-secondary">
									<Hint
										message={Liferay.Language.get(
											'top-referring-pages-help'
										)}
										title={Liferay.Language.get(
											'top-referring-pages'
										)}
									/>
								</span>
							</span>
						</ClayList.ItemTitle>
					</ClayList.ItemField>
					<ClayList.ItemField>
						<ClayList.ItemTitle>
							<span>{Liferay.Language.get('traffic')}</span>
						</ClayList.ItemTitle>
					</ClayList.ItemField>
				</ClayList.Item>
				{referringPages
					.slice(0, isExpanded ? 10 : ITEMS_TO_SHOW)
					.map(({traffic, url}) => {
						return (
							<ClayList.Item flex key={url}>
								<ClayList.ItemField expand>
									<ClayList.ItemText>
										<ClayTooltipProvider>
											<span
												className="text-truncate-inline"
												data-tooltip-align="top"
												title={url}
											>
												<a
													className="c-mr-2 text-primary text-truncate text-truncate-reverse"
													href={url}
													target="_blank"
												>
													{url}
												</a>
											</span>
										</ClayTooltipProvider>
									</ClayList.ItemText>
								</ClayList.ItemField>
								<ClayList.ItemField expand>
									<span className="align-self-end">
										{numberFormat(languageTag, traffic)}
									</span>
								</ClayList.ItemField>
							</ClayList.Item>
						);
					})}
			</ClayList>

			<ClayButton
				borderless
				className="c-mb-4"
				displayType="secondary"
				onClick={() => setIsExpanded(!isExpanded)}
				small
			>
				{isExpanded ? (
					<span>{Liferay.Language.get('view-less')}</span>
				) : (
					<span>{Liferay.Language.get('view-more')}</span>
				)}
			</ClayButton>

			<ClayList className="list-group-pages-list">
				<ClayList.Item flex>
					<ClayList.ItemField expand>
						<ClayList.ItemTitle className="text-truncate-inline">
							<span className="text-truncate">
								{Liferay.Language.get('top-referring-domains')}
								<span className="text-secondary">
									<Hint
										message={Liferay.Language.get(
											'top-referring-domains-help'
										)}
										title={Liferay.Language.get(
											'top-referring-domains'
										)}
									/>
								</span>
							</span>
						</ClayList.ItemTitle>
					</ClayList.ItemField>
					<ClayList.ItemField>
						<ClayList.ItemTitle>
							<span>{Liferay.Language.get('traffic')}</span>
						</ClayList.ItemTitle>
					</ClayList.ItemField>
				</ClayList.Item>
				{referringDomains.map(({traffic, url}) => {
					return (
						<ClayList.Item flex key={url}>
							<ClayList.ItemField expand>
								<ClayList.ItemText>
									<ClayTooltipProvider>
										<span
											className="text-truncate-inline"
											data-tooltip-align="top"
											title={url}
										>
											<a
												className="text-primary text-truncate"
												href={url}
												target="_blank"
											>
												{url}
											</a>
										</span>
									</ClayTooltipProvider>
								</ClayList.ItemText>
							</ClayList.ItemField>
							<ClayList.ItemField expand>
								<span className="align-self-end">
									{numberFormat(languageTag, traffic)}
								</span>
							</ClayList.ItemField>
						</ClayList.Item>
					);
				})}
			</ClayList>
		</div>
	);
}

ReferralDetail.propTypes = {
	currentPage: PropTypes.object.isRequired,
	languageTag: PropTypes.string.isRequired,
	timeSpanOptions: PropTypes.arrayOf(
		PropTypes.shape({
			key: PropTypes.string,
			label: PropTypes.string,
		})
	).isRequired,
	trafficShareDataProvider: PropTypes.func.isRequired,
	trafficVolumeDataProvider: PropTypes.func.isRequired,
};
