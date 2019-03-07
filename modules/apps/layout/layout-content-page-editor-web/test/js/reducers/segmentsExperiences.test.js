/* globals describe, test, jest, expect, beforeAll, afterAll */

import {createSegmentsExperienceReducer, endCreateSegmentsExperience, startCreateSegmentsExperience} from '../../../src/main/resources/META-INF/resources/js/reducers/segmentsExperiences.es';
import {CREATE_SEGMENTS_EXPERIENCE, END_CREATE_SEGMENTS_EXPERIENCE, START_CREATE_SEGMENTS_EXPERIENCE} from '../../../src/main/resources/META-INF/resources/js/actions/actions.es';

describe(
	'segments experiences reducers',
	() => {
		test(
			'createSegmentsExperienceReducer communicates with API and updates the state',
			() => {
				const availableSegmentsExperiences = {};
				const classNameId = 'test-class-name-id';
				const classPK = 'test-class-p-k';
				const spy = jest.spyOn(global.Liferay, 'Service');

				const prevState = {
					availableSegmentsExperiences,
					classNameId,
					classPK,
					defaultLanguageId: 'en_US'
				};

				const payload = {
					segmentsExperienceLabel: 'test experience label',
					segmentEntryId: 'test-segment-id'
				};

				const nextState = {
					...prevState,
					availableSegmentsExperiences: {
						[SEGMENTS_EXPERIENCE_ID]: {
							active: true,
							segmentsExperienceId: SEGMENTS_EXPERIENCE_ID,
							segmentsExperienceLabel: payload.segmentsExperienceLabel,
							priority: 0,
							segmentEntryId: payload.segmentEntryId
						}
					},
					segmentsExperienceId: SEGMENTS_EXPERIENCE_ID
				};

				const liferayServiceParams = {
					active: true,
					classNameId: prevState.classNameId,
					classPK: prevState.classPK,
					nameMap: JSON.stringify({en_US: payload.segmentsExperienceLabel}),
					priority: 0,
					segmentsEntryId: payload.segmentEntryId
				};

				expect.assertions(4);

				createSegmentsExperienceReducer(prevState, CREATE_SEGMENTS_EXPERIENCE, payload)
					.then(
						response => {
							expect(response).toEqual(nextState);
						}
					);

				expect(spy).toHaveBeenCalledWith(
					expect.stringContaining(''),
					liferayServiceParams,
					expect.objectContaining({}),
					expect.objectContaining({})
				)

				const secondPayload = {
					segmentsExperienceLabel: 'second test experience label',
					segmentEntryId: 'test-segment-id'
				};

				const secondNextState = {
					...nextState,
					availableSegmentsExperiences: {
						...nextState.availableSegmentsExperiences,
						[SEGMENTS_EXPERIENCE_ID_SECOND]: {
							active: true,
							segmentsExperienceId: SEGMENTS_EXPERIENCE_ID_SECOND,
							segmentsExperienceLabel: secondPayload.segmentsExperienceLabel,
							priority: 1,
							segmentEntryId: secondPayload.segmentEntryId
						}
					},
					segmentsExperienceId: SEGMENTS_EXPERIENCE_ID_SECOND
				};

				const secondLiferayServiceParams = {
					active: true,
					classNameId: prevState.classNameId,
					classPK: prevState.classPK,
					nameMap: JSON.stringify({en_US: secondPayload.segmentsExperienceLabel}),
					priority: 1,
					segmentsEntryId: secondPayload.segmentEntryId
				};

				createSegmentsExperienceReducer(
					nextState,
					CREATE_SEGMENTS_EXPERIENCE,
					secondPayload
				).then(
					response => {
						expect(response).toEqual(secondNextState);
					}
				);

				expect(spy).toHaveBeenLastCalledWith(
					expect.stringContaining(''),
					secondLiferayServiceParams,
					expect.objectContaining({}),
					expect.objectContaining({})
				);
			}
		);

		test(
			'startCreateSegmentsExperience and endCreateSegmentsExperience switch states',
			() => {
				const prevState = {};

				const creatingSegmentsExperienceState = startCreateSegmentsExperience(prevState, START_CREATE_SEGMENTS_EXPERIENCE);
				expect(creatingSegmentsExperienceState).toMatchObject(
					{
						experienceSegmentsCreation: {
							creatingSegmentsExperience: true,
							error: null
						}
					}
				);
				const notEdtingState = endCreateSegmentsExperience(creatingSegmentsExperienceState, END_CREATE_SEGMENTS_EXPERIENCE);

				expect(notEdtingState).toMatchObject(
					{
						experienceSegmentsCreation: {
							creatingSegmentsExperience: false,
							error: null
						}
					}
				);
			}
		);

		beforeAll(
			() => {
				prevLiferayGlobal = {...global.Liferay};
				global.Liferay = {
					Service(
						URL,
						{
							classNameId,
							classPK,
							segmentsEntryId,
							nameMap,
							active,
							priority
						},
						callbackFunc,
						errorFunc
					) {
						return callbackFunc(
							{
								active,
								nameCurrentValue: JSON.parse(nameMap).en_US,
								priority,
								segmentsEntryId,
								segmentsExperienceId: (experiencesCount++, SEGMENTS_EXPERIENCES_LIST[experiencesCount])
							}
						);
					}
				};
			}
		);

		afterAll(
			() => {
				global.Liferay = prevLiferayGlobal;
			}
		);
	}
);

const SEGMENTS_EXPERIENCE_ID = 'SEGMENTS_EXPERIENCE_ID';

const SEGMENTS_EXPERIENCE_ID_SECOND = 'SEGMENTS_EXPERIENCE_ID_SECOND';

const SEGMENTS_EXPERIENCES_LIST = [SEGMENTS_EXPERIENCE_ID, SEGMENTS_EXPERIENCE_ID_SECOND];
let experiencesCount = -1;
let prevLiferayGlobal = null;