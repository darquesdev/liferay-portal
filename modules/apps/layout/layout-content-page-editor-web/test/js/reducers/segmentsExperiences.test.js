/* globals describe, test, jest, expect, beforeAll, afterAll */

import {createSegmentsExperienceReducer, deleteSegmentsExperienceReducer, editSegmentsExperienceReducer} from '../../../src/main/resources/META-INF/resources/js/reducers/segmentsExperiences.es';
import {CREATE_SEGMENTS_EXPERIENCE, DELETE_SEGMENTS_EXPERIENCE, EDIT_SEGMENTS_EXPERIENCE} from '../../../src/main/resources/META-INF/resources/js/actions/actions.es';
import * as FragmentsEditorUpdateUtils from '../../../src/main/resources/META-INF/resources/js/utils/FragmentsEditorUpdateUtils.es';




const SEGMENTS_EXPERIENCE_ID = 'SEGMENTS_EXPERIENCE_ID';

const SEGMENTS_EXPERIENCE_ID_DEFAULT = 'SEGMENTS_EXPERIENCE_ID_DEFAULT';

const SEGMENTS_EXPERIENCE_ID_SECOND = 'SEGMENTS_EXPERIENCE_ID_SECOND';

const SEGMENTS_EXPERIENCES_LIST = [SEGMENTS_EXPERIENCE_ID, SEGMENTS_EXPERIENCE_ID_SECOND];

describe(
	'segments experiences reducers',
	() => {
		beforeEach(() => {
			jest.spyOn(FragmentsEditorUpdateUtils, 'updateLayoutData')
				.mockImplementation(() => new Promise(resolve => resolve()));
		})
		test(
			'createSegmentsExperienceReducer communicates with API and updates the state',
			done => {
				let prevLiferayGlobal = {...global.Liferay};
				let prevThemeDisplay = {...global.themeDisplay};
				
				global.themeDisplay = {
					getScopeGroupId() {
						return 'mockedScopeGroupId';
					},
					getUserId() {
						return 'mockedUserId';
					}
				}

				global.Liferay = {
					Service(
						URL,
						{
							active,
							classNameId,
							classPK,
							nameMap,
							segmentsEntryId
						},
						callbackFunc,
						errorFunc
					) {
						return callbackFunc(
							{
								active,
								nameCurrentValue: JSON.parse(nameMap).en_US,
								segmentsEntryId,
								segmentsExperienceId: (experiencesCount++, SEGMENTS_EXPERIENCES_LIST[experiencesCount])
							}
						);
					}
				};

				const availableSegmentsExperiences = {};
				const classNameId = 'test-class-name-id';
				const classPK = 'test-class-p-k';
				const spy = jest.spyOn(global.Liferay, 'Service');
				const defaultSegmentsExperienceId = 'DEFAULT_SEGMENTS_EXPERIENCE_ID';

				let experiencesCount = -1;

				let currentLayout = {
					'any': 'object'
				}

				const prevState = {
					availableSegmentsExperiences,
					classNameId,
					classPK,
					defaultSegmentsExperienceId,
					defaultLanguageId: 'en_US',
					layoutDataPersonalization: [{
						segmentsExperienceId: defaultSegmentsExperienceId,
						layoutData: {},
					}],
					layoutData: currentLayout
				};

				const payload = {
					name: 'test experience name',
					segmentsEntryId: 'test-segment-id'
				};

				const nextState = {
					...prevState,
					availableSegmentsExperiences: {
						[SEGMENTS_EXPERIENCE_ID]: {
							active: true,
							name: payload.name,
							segmentsEntryId: payload.segmentsEntryId,
							segmentsExperienceId: SEGMENTS_EXPERIENCE_ID
						}
					},
					defaultSegmentsExperienceId,
					segmentsExperienceId: SEGMENTS_EXPERIENCE_ID,
					layoutDataPersonalization: [
						{
							segmentsExperienceId: defaultSegmentsExperienceId,
							layoutData: currentLayout,
						}, {
							segmentsExperienceId: SEGMENTS_EXPERIENCE_ID,
							layoutData: currentLayout,
						}
					],
					layoutData: currentLayout
				};

				const serviceContext = JSON.stringify({
						scopeGroupId: global.themeDisplay.getScopeGroupId(),
						userId: global.themeDisplay.getUserId()
					})

				const liferayServiceParams = {
					active: true,
					classNameId: prevState.classNameId,
					classPK: prevState.classPK,
					nameMap: JSON.stringify({en_US: payload.name}),
					segmentsEntryId: payload.segmentsEntryId,
					serviceContext
				};

				createSegmentsExperienceReducer(prevState, CREATE_SEGMENTS_EXPERIENCE, payload)
				.then(response => {
					expect(response).toEqual(nextState);
				
					expect(spy).toHaveBeenCalledWith(
						expect.stringContaining(''),
						liferayServiceParams,
						expect.objectContaining({}),
						expect.objectContaining({})
					);

					const secondPayload = {
						name: 'second test experience name',
						segmentsEntryId: 'test-segment-id'
					};

					const secondNextState = {
						...nextState,
						availableSegmentsExperiences: {
							...nextState.availableSegmentsExperiences,
							[SEGMENTS_EXPERIENCE_ID_SECOND]: {
								active: true,
								name: secondPayload.name,
								segmentsEntryId: secondPayload.segmentsEntryId,
								segmentsExperienceId: SEGMENTS_EXPERIENCE_ID_SECOND
							}
						},
						layoutDataPersonalization: [
							...nextState.layoutDataPersonalization,
							{
								segmentsExperienceId: SEGMENTS_EXPERIENCE_ID_SECOND,
								layoutData: currentLayout
							}
						],
						segmentsExperienceId: SEGMENTS_EXPERIENCE_ID_SECOND,
					};

					const secondLiferayServiceParams = {
						active: true,
						classNameId: prevState.classNameId,
						classPK: prevState.classPK,
						nameMap: JSON.stringify({en_US: secondPayload.name}),
						segmentsEntryId: secondPayload.segmentsEntryId,
						serviceContext
					};

					expect(
						createSegmentsExperienceReducer(
							nextState,
							CREATE_SEGMENTS_EXPERIENCE,
							secondPayload
						)
					).resolves.toEqual(secondNextState);
					
					expect(spy).toHaveBeenLastCalledWith(
						expect.stringContaining(''),
						secondLiferayServiceParams,
						expect.objectContaining({}),
						expect.objectContaining({})
					);

					global.Liferay = prevLiferayGlobal;
					global.themeDisplay = prevThemeDisplay
					done();
				}).catch(error => { throw new Error(error); });
				
				

			}
		);

		test(
			'deleteExperience communicates with API and updates the state',
			(done) => {
				let prevLiferayGlobal = {...global.Liferay};
				
				global.Liferay = {
					Service(
						URL,
						{
							segmentsExperienceId
						},
						callbackFunc,
						errorFunc
					) {
						return callbackFunc(
							{
								segmentsExperienceId
							}
						);
					}
				};
				
				const spy = jest.spyOn(global.Liferay, 'Service');

				const availableSegmentsExperiences = {
					[SEGMENTS_EXPERIENCE_ID]: {
						name: 'A test experience',
						segmentsEntryId: 'notRelevantSegmentId',
						segmentsExperienceId: SEGMENTS_EXPERIENCE_ID
					},
					[SEGMENTS_EXPERIENCE_ID_DEFAULT]: {
						name: 'A default test experience',
						segmentsEntryId: 'notRelevantSegmentId',
						segmentsExperienceId: SEGMENTS_EXPERIENCE_ID
					},
					[SEGMENTS_EXPERIENCE_ID_SECOND]: {
						name: 'A second test experience',
						segmentsEntryId: 'notRelevantSegmentId',
						segmentsExperienceId: SEGMENTS_EXPERIENCE_ID_SECOND
					}
				};

				const classNameId = 'test-class-name-id';
				const classPK = 'test-class-p-k';

				const prevState = {
					availableSegmentsExperiences,
					classNameId,
					classPK,
					defaultLanguageId: 'en_US',
					defaultSegmentsExperienceId: SEGMENTS_EXPERIENCE_ID_DEFAULT,
					segmentsExperienceId: SEGMENTS_EXPERIENCE_ID_SECOND
				};

				const nextState = {
					...prevState,
					availableSegmentsExperiences: {
						[SEGMENTS_EXPERIENCE_ID_DEFAULT]: prevState.availableSegmentsExperiences[SEGMENTS_EXPERIENCE_ID_DEFAULT],
						[SEGMENTS_EXPERIENCE_ID_SECOND]: prevState.availableSegmentsExperiences[SEGMENTS_EXPERIENCE_ID_SECOND]
					}
				};

				const secondNextState = {
					...nextState,
					availableSegmentsExperiences: {
						[SEGMENTS_EXPERIENCE_ID_DEFAULT]: prevState.availableSegmentsExperiences[SEGMENTS_EXPERIENCE_ID_DEFAULT]
					},
					segmentsExperienceId: SEGMENTS_EXPERIENCE_ID_DEFAULT
				};

				deleteSegmentsExperienceReducer(
					prevState,
					DELETE_SEGMENTS_EXPERIENCE,
					{
						segmentsExperienceId: SEGMENTS_EXPERIENCE_ID
					}
				).then(
					state => {
						expect(state).toEqual(nextState);

						expect(spy).toHaveBeenCalledTimes(1);
						expect(spy).toHaveBeenLastCalledWith(
							expect.stringContaining(''),
							{segmentsExperienceId: SEGMENTS_EXPERIENCE_ID},
							expect.objectContaining({}),
							expect.objectContaining({})
						);

						deleteSegmentsExperienceReducer(
							nextState,
							DELETE_SEGMENTS_EXPERIENCE,
							{
								segmentsExperienceId: SEGMENTS_EXPERIENCE_ID_SECOND
							}
						).then(
							state => {
								expect(state).toEqual(secondNextState);
							}
						).catch(error => { throw new Error(error); });

						expect(spy).toHaveBeenCalledTimes(2);
						expect(spy).toHaveBeenLastCalledWith(
							expect.stringContaining(''),
							{segmentsExperienceId: SEGMENTS_EXPERIENCE_ID_SECOND},
							expect.objectContaining({}),
							expect.objectContaining({})
						);
						done();
						global.Liferay = prevLiferayGlobal;
					}
				).catch((error) => {
					throw new Error(error)
				});
			}
		);

		test(
			'editExperience communicates with API and updates the state',
			() => {
				let prevLiferayGlobal = {...global.Liferay};
				global.Liferay = {
					Service(
						URL,
						{
							active,
							nameMap,
							segmentsEntryId,
							segmentsExperienceId
						},
						callbackFunc,
						errorFunc
					) {
						return callbackFunc(
							{
								active,
								nameCurrentValue: JSON.parse(nameMap).en_US,
								segmentsEntryId,
								segmentsExperienceId
							}
						);
					}
				};

				const availableSegmentsExperiences = {
					[SEGMENTS_EXPERIENCE_ID]: {
						active: true,
						name: 'A test experience',
						segmentsEntryId: 'notRelevantSegmentId',
						segmentsExperienceId: SEGMENTS_EXPERIENCE_ID
					},
					[SEGMENTS_EXPERIENCE_ID_DEFAULT]: {
						active: true,
						name: 'A default test experience',
						segmentsEntryId: 'notRelevantSegmentId',
						segmentsExperienceId: SEGMENTS_EXPERIENCE_ID
					},
					[SEGMENTS_EXPERIENCE_ID_SECOND]: {
						active: true,
						name: 'A second test experience',
						segmentsEntryId: 'notRelevantSegmentId',
						segmentsExperienceId: SEGMENTS_EXPERIENCE_ID_SECOND
					}
				};

				const payload = {
					name: 'A modified test experience',
					segmentsEntryId: 'relevantSegmentId',
					segmentsExperienceId: SEGMENTS_EXPERIENCE_ID
				};
				const prevState = {
					availableSegmentsExperiences,
					defaultLanguageId: 'en_US'
				};

				expect.assertions(3);

				editSegmentsExperienceReducer(
					prevState,
					EDIT_SEGMENTS_EXPERIENCE,
					payload
				).then(
					state => {
						expect(state.availableSegmentsExperiences[SEGMENTS_EXPERIENCE_ID_SECOND]).toEqual(prevState.availableSegmentsExperiences[SEGMENTS_EXPERIENCE_ID_SECOND]);
						expect(state.availableSegmentsExperiences[SEGMENTS_EXPERIENCE_ID_DEFAULT]).toEqual(prevState.availableSegmentsExperiences[SEGMENTS_EXPERIENCE_ID_DEFAULT]);
						expect(state.availableSegmentsExperiences[SEGMENTS_EXPERIENCE_ID]).toEqual(
							{
								...prevState.availableSegmentsExperiences[SEGMENTS_EXPERIENCE_ID],
								name: payload.name,
								segmentsEntryId: payload.segmentsEntryId
							}
						);
					}
				);

				global.Liferay = prevLiferayGlobal;
			}
		);
		
		afterEach(() => {
			jest.restoreAllMocks()
		})
	}
);