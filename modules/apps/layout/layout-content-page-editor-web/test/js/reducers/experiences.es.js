/* globals describe, test, jest, expect, beforeAll, afterAll */

import {createExperienceReducer, endCreateExperience, startCreateExperience} from '../../../src/main/resources/META-INF/resources/js/reducers/experiences.es';
import {CREATE_EXPERIENCE, END_CREATE_EXPERIENCE, START_CREATE_EXPERIENCE} from '../../../src/main/resources/META-INF/resources/js/actions/actions.es';

describe(
	'experiences reducers',
	() => {
		test(
			'createExperienceReducer communicates with API and updates the state',
			() => {
				const availableExperiences = {};
				const classNameId = 'test-class-name-id';
				const classPK = 'test-class-p-k';
				const spy = jest.spyOn(global.Liferay, 'Service');

				const prevState = {
					availableExperiences,
					classNameId,
					classPK,
					defaultLanguageId: 'en_US'
				};

				const payload = {
					experienceLabel: 'test experience label',
					segmentId: 'test-segment-id'
				};

				const nextState = {
					...prevState,
					availableExperiences: {
						[EXPERIENCE_ID]: {
							active: true,
							experienceId: EXPERIENCE_ID,
							experienceLabel: payload.experienceLabel,
							priority: 0,
							segmentId: payload.segmentId
						}
					},
					experienceId: EXPERIENCE_ID
				};

				const liferayServiceParams = {
					active: true,
					classNameId: prevState.classNameId,
					classPK: prevState.classPK,
					nameMap: JSON.stringify({en_US: payload.experienceLabel}),
					priority: 0,
					segmentsEntryId: payload.segmentId
				};

				expect.assertions(4);

				createExperienceReducer(prevState, CREATE_EXPERIENCE, payload)
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
				);

				const secondPayload = {
					experienceLabel: 'second test experience label',
					segmentId: 'test-segment-id'
				};

				const secondNextState = {
					...nextState,
					availableExperiences: {
						...nextState.availableExperiences,
						[EXPERIENCE_ID_SECOND]: {
							active: true,
							experienceId: EXPERIENCE_ID_SECOND,
							experienceLabel: secondPayload.experienceLabel,
							priority: 1,
							segmentId: secondPayload.segmentId
						}
					},
					experienceId: EXPERIENCE_ID_SECOND
				};

				const secondLiferayServiceParams = {
					active: true,
					classNameId: prevState.classNameId,
					classPK: prevState.classPK,
					nameMap: JSON.stringify({en_US: secondPayload.experienceLabel}),
					priority: 1,
					segmentsEntryId: secondPayload.segmentId
				};

				createExperienceReducer(
					nextState,
					CREATE_EXPERIENCE,
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
			'startCreateExperience and endCreateExperience switch states',
			() => {
				const prevState = {};

				const creatingExperienceState = startCreateExperience(prevState, START_CREATE_EXPERIENCE);
				expect(creatingExperienceState).toMatchObject(
					{
						experienceCreation: {
							creatingExperience: true,
							error: null
						}
					}
				);
				const notEdtingState = endCreateExperience(creatingExperienceState, END_CREATE_EXPERIENCE);

				expect(notEdtingState).toMatchObject(
					{
						experienceCreation: {
							creatingExperience: false,
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
								segmentsExperienceId: (experiencesCount++, EXPERIENCES_LIST[experiencesCount])
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

const EXPERIENCE_ID = 'EXPERIENCE_ID';

const EXPERIENCE_ID_SECOND = 'EXPERIENCE_ID_SECOND';

const EXPERIENCES_LIST = [EXPERIENCE_ID, EXPERIENCE_ID_SECOND];
let experiencesCount = -1;
let prevLiferayGlobal = null;