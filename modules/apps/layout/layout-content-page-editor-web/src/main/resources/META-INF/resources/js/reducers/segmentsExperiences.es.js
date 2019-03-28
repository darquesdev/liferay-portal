import {CREATE_SEGMENTS_EXPERIENCE, DELETE_SEGMENTS_EXPERIENCE, EDIT_SEGMENTS_EXPERIENCE, SELECT_SEGMENTS_EXPERIENCE, UPDATE_SEGMENTS_EXPERIENCE_PRIORITY} from '../actions/actions.es';
import {setIn} from '../utils/utils.es';
import {updateLayoutData} from '../utils/FragmentsEditorUpdateUtils.es';

const CREATE_SEGMENTS_EXPERIENCE_URL = '/segments.segmentsexperience/add-segments-experience';

const DELETE_SEGMENTS_EXPERIENCE_URL = '/segments.segmentsexperience/delete-segments-experience';

const EDIT_SEGMENTS_EXPERIENCE_URL = '/segments.segmentsexperience/update-segments-experience';

const UPDATE_SEGMENTS_EXPERIENCE_PRIORITY_URL = '/segments.segmentsexperience/update-segments-experience-priority';


/**
 *
 *
 * @param {!object} state
 * @param {!array} state.layoutDataPersonalization
 * @param {!object} state.layoutData
 * @param {!string} state.defaultSegmentsExperienceId
 * @returns
 */
function setBaseLayoutData(state) {
	if (state.layoutDataPersonalization.length === 0) {
		state.layoutDataPersonalization.push(
			{
				layoutData: Object.assign({} , state.layoutData),
				segmentsExperienceId: state.defaultSegmentsExperienceId
			}
		);
	}
	return state;
}


/**
 *
 *
 * @param {!object} state
 * @param {!array} state.layoutDataPersonalization
 * @param {!object} state.layoutData
 * @param {!string} state.defaultSegmentsExperienceId
 * @param {!string} segmentsExperienceId The segmentsExperience id that owns this LayoutData
 * @returns
 */
function storeLayoutData(state, segmentsExperienceId) {
	const nextState = Object.assign({}, state);
	const baseLayoutData = nextState.layoutDataPersonalization.find(segmentedLayout => segmentedLayout.segmentsExperienceId === nextState.defaultSegmentsExperienceId);

	nextState.layoutDataPersonalization.push(
		{
			layoutData: baseLayoutData.layoutData,
			segmentsExperienceId
		}
	);

	return Object.assign({}, nextState);
}


/**
 *
 *
 * @param {*} state
 * @param {*} segmentsExperienceId
 * @returns
 */
function switchLayout(state, segmentsExperienceId) {
	return new Promise((resolve, reject) => {
		updateLayoutData(
			{
				updateLayoutPageTemplateDataURL: state.updateLayoutPageTemplateDataURL,
				portletNamespace: state.portletNamespace,
				classNameId: state.classNameId,
				classPK: state.classPK,
				data: state.layoutData,
				segmentsExperienceId 
			}
		)
			.then(
				() => {
					if(segmentsExperienceId === state.segmentsExperienceId) resolve(state);

					let nextState = state;
					const prevSegmentsExperienceId = state.segmentsExperienceId || nextState.defaultSegmentsExperienceId;
					const prevLayout = state.layoutData;

					const { layoutData } = nextState.layoutDataPersonalization.find(segmentedLayout => {
						return segmentedLayout.segmentsExperienceId === segmentsExperienceId;
					});

					nextState = setIn(
						nextState,
						['layoutData'],
						layoutData
					);

					const newlayoutDataPersonalization = nextState.layoutDataPersonalization.map(
						segmentedLayout => {
							if (segmentedLayout.segmentsExperienceId === prevSegmentsExperienceId) {
								return Object.assign(
									{},
									segmentedLayout,
									{
										layoutData: prevLayout
									}
								);
							}
							return segmentedLayout;
						}
					);

					nextState = setIn(
						nextState,
						['layoutDataPersonalization'],
						newlayoutDataPersonalization
					);

					return resolve(nextState);
				}
			)
			.catch(
				() => {
					resolve(nextState);
				}
			);

	})
}

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {string} payload.segmentsEntryId
 * @param {string} payload.name
 * @return {object}
 * @review
 */
function createSegmentsExperienceReducer(state, actionType, payload) {
	return new Promise(
		(resolve, reject) => {
			let nextState = state;
			if (actionType === CREATE_SEGMENTS_EXPERIENCE) {
				const {name, segmentsEntryId} = payload;

				const {
					classNameId,
					classPK
				} = nextState;

				const nameMap = JSON.stringify(
					{
						[state.defaultLanguageId]: name
					}
				);

				Liferay.Service(
					CREATE_SEGMENTS_EXPERIENCE_URL,
					{
						active: true,
						classNameId,
						classPK,
						nameMap,
						segmentsEntryId: segmentsEntryId
					},
					(obj) => {

						const {
							active,
							nameCurrentValue,
							priority,
							segmentsEntryId,
							segmentsExperienceId
						} = obj;

						nextState = setIn(
							nextState,
							[
								'availableSegmentsExperiences',
								segmentsExperienceId
							],
							{
								active,
								name: nameCurrentValue,
								priority,
								segmentsEntryId,
								segmentsExperienceId
							}
						);
						nextState = storeLayoutData(nextState, segmentsExperienceId);
						switchLayout(nextState, segmentsExperienceId)
							.then((newState) => {
								let nextNewState = setIn(
									newState,
									['segmentsExperienceId'],
									segmentsExperienceId
								);
								resolve(nextNewState);
							})
					},
					error => {
						reject(error);
					}
				);
			}
			else {
				resolve(nextState);
			}
		}
	);
}

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {object} payload
 * @param {!string} payload.experienceId
 * @returns\
 */
function deleteSegmentsExperienceReducer(state, actionType, payload) {
	return new Promise(
		(resolve, reject) => {
			let nextState = state;
			if (actionType === DELETE_SEGMENTS_EXPERIENCE) {
				const {segmentsExperienceId} = payload;

				Liferay.Service(
					DELETE_SEGMENTS_EXPERIENCE_URL,
					{
						segmentsExperienceId
					},
					response => {
						const priority = response.priority;

						let availableSegmentsExperiences = Object.assign(
							{},
							nextState.availableSegmentsExperiences
						);

						delete availableSegmentsExperiences[response.segmentsExperienceId];
						const experienceIdToSelect = (segmentsExperienceId === nextState.segmentsExperienceId) ? nextState.defaultSegmentsExperienceId : nextState.segmentsExperienceId;

						Object.entries(availableSegmentsExperiences).forEach(
							([key, experience]) => {
								const segmentExperiencePriority = experience.priority;

								if (segmentExperiencePriority > priority) {
									experience.priority = segmentExperiencePriority - 1;
								}
							}
						);

						nextState = setIn(
							nextState,
							['availableSegmentsExperiences'],
							availableSegmentsExperiences
						);
						nextState = setIn(
							nextState,
							['segmentsExperienceId'],
							experienceIdToSelect
						);
						resolve(nextState);
					},
					(error, {exception}) => {

						reject(exception);
					}
				);

			}
			else {
				resolve(nextState);
			}
		}
	);
}

/**
 *
 *
 * @export
 * @param {!object} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {!string} payload.segmentsExperienceId
 * @returns
 */
function selectSegmentsExperienceReducer(state, actionType, payload) {
	return new Promise((resolve, reject) => {
		let nextState = state;
		if (actionType === SELECT_SEGMENTS_EXPERIENCE) {
			switchLayout(nextState, payload.segmentsExperienceId)
			.then(
				newState => {
					let nextNewState = setIn(
						newState,
						['segmentsExperienceId'],
						payload.segmentsExperienceId,
					);
					resolve(nextNewState);
				}
			)
		} else {
			resolve(nextState);
		}
	})
}

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {!string} payload.segmentsEntryId
 * @param {!string} payload.name
 * @param {!string} payload.segmentsExperienceId
 * @return {object}
 * @review
 */
function editSegmentsExperienceReducer(state, actionType, payload) {
	return new Promise(
		(resolve, reject) => {
			let nextState = state;
			if (actionType === EDIT_SEGMENTS_EXPERIENCE) {
				const {
					name,
					segmentsEntryId,
					segmentsExperienceId
				} = payload;

				const nameMap = JSON.stringify(
					{
						[state.defaultLanguageId]: name
					}
				);

				Liferay.Service(
					EDIT_SEGMENTS_EXPERIENCE_URL,
					{
						active: true,
						nameMap,
						segmentsEntryId,
						segmentsExperienceId
					},
					(obj) => {

						const {
							active,
							nameCurrentValue,
							priority,
							segmentsEntryId,
							segmentsExperienceId
						} = obj;

						nextState = setIn(
							nextState,
							[
								'availableSegmentsExperiences',
								segmentsExperienceId
							],
							{
								active,
								name: nameCurrentValue,
								priority,
								segmentsEntryId,
								segmentsExperienceId
							}
						);

						resolve(nextState);
					},
					error => {
						reject(error);
					}
				);
			}
			else {
				resolve(nextState);
			}
		}
	);
}

/**
 *
 *
 * @param {*} state
 * @param {!string} actionType
 * @param {object} payload
 * @param {!('up' | 'down')} payload.direction
 * @param {!string} payload.segmentsExperienceId
 * @param {!number} payload.priority
 */
function updateSegmentsExperiencePriorityReducer(state, actionType, payload) {
	return new Promise((resolve, reject) => {
		let nextState = state;
		if (actionType === UPDATE_SEGMENTS_EXPERIENCE_PRIORITY) {
			const {
				direction,
				priority: oldPriority,
				segmentsExperienceId
			} = payload;

			const priority = parseInt(oldPriority, 10);

			const newPriority = (direction === 'up') ? priority + 1 : priority - 1;

			Liferay.Service(
				UPDATE_SEGMENTS_EXPERIENCE_PRIORITY_URL,
				{
					newPriority,
					segmentsExperienceId
				}
			).then(
				() => {
					const availableSegmentsExperiencesArray = Object.values(nextState.availableSegmentsExperiences);
					const subTargetExperience = availableSegmentsExperiencesArray.find(
						experience => {
							return experience.priority === newPriority;
						}
					);
					const targetExperience = availableSegmentsExperiencesArray.find(
						experience => {
							return experience.priority === priority;
						}
					);
					nextState = setIn(
						nextState,
						[
							'availableSegmentsExperiences',
							targetExperience.segmentsExperienceId,
							'priority'
						],
						newPriority
					);

					nextState = setIn(
						nextState,
						[
							'availableSegmentsExperiences',
							subTargetExperience.segmentsExperienceId,
							'priority'
						],
						priority
					);

					resolve(nextState);
				}
			).catch(
				(error) => {
					reject(error);
				}
			);
		}
		else {
			resolve(nextState);
		}
	});
}

export {
	createSegmentsExperienceReducer,
	deleteSegmentsExperienceReducer,
	editSegmentsExperienceReducer,
	updateSegmentsExperiencePriorityReducer,
	selectSegmentsExperienceReducer
};