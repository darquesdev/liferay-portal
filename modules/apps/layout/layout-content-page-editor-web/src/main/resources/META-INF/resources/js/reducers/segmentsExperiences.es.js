import {CREATE_SEGMENTS_EXPERIENCE, DELETE_SEGMENTS_EXPERIENCE, EDIT_SEGMENTS_EXPERIENCE, SELECT_SEGMENTS_EXPERIENCE, UPDATE_SEGMENTS_EXPERIENCE_PRIORITY} from '../actions/actions.es';
import {setIn} from '../utils/utils.es';
import {updateLayoutData} from '../utils/FragmentsEditorUpdateUtils.es';
import { getRowFragmentEntryLinkIds } from '../utils/FragmentsEditorGetUtils.es';
import { containsFragmentEntryLinkId } from '../utils/LayoutDataList.es';

const CREATE_SEGMENTS_EXPERIENCE_URL = '/segments.segmentsexperience/add-segments-experience';

const DELETE_SEGMENTS_EXPERIENCE_URL = '/segments.segmentsexperience/delete-segments-experience';

const EDIT_SEGMENTS_EXPERIENCE_URL = '/segments.segmentsexperience/update-segments-experience';

const UPDATE_SEGMENTS_EXPERIENCE_PRIORITY_URL = '/segments.segmentsexperience/update-segments-experience-priority';

/**
 * Stores a the layout data of a new experience in layoutDataList
 * @param {!object} state
 * @param {!array} state.layoutDataList
 * @param {!object} state.layoutData
 * @param {!string} state.defaultSegmentsExperienceId
 * @param {!string} segmentsExperienceId The segmentsExperience id that owns this LayoutData
 * @returns {Promise}
 */
function _storeNewLayoutData(state, segmentsExperienceId) {
	let nextState = state;
	return new Promise((resolve, reject) => {
		let baseLayoutData = null;
		if (
			nextState.defaultSegmentsExperienceId === nextState.segmentsExperienceId ||
			!nextState.segmentsExperienceId
		) {

			// the current layout is the latest default available

			baseLayoutData = nextState.layoutData;
		}
		else {

			// the layoutList contains the latest baseLayout

			const defaultExperienceLayoutListItem = nextState.layoutDataList.find(segmentedLayout => segmentedLayout.segmentsExperienceId === nextState.defaultSegmentsExperienceId);
			baseLayoutData = defaultExperienceLayoutListItem && defaultExperienceLayoutListItem.layoutData;
		}

		// make sure the backend knows the layoutData of the new segment

		updateLayoutData(
			{
				classNameId: nextState.classNameId,
				classPK: nextState.classPK,
				data: baseLayoutData,
				portletNamespace: nextState.portletNamespace,
				segmentsExperienceId: segmentsExperienceId,
				updateLayoutPageTemplateDataURL: nextState.updateLayoutPageTemplateDataURL
			}
		).then(
			() => {
				nextState.layoutDataList.push(
					{
						layoutData: baseLayoutData,
						segmentsExperienceId
					}
				);

				return resolve(nextState);
			}
		).catch(
			e => {
				reject(e);
			}
		);
	});
}

/**
 *
 * @param {!object} state
 * @param {!string} segmentsExperienceId
 * @returns {Promise}
 */
function _switchLayoutDataList(state, segmentsExperienceId) {
	let nextState = state;
	return new Promise((resolve, reject) => {
		try {

		// this makes sure the server gets the latest changes of the layout belonging to the experience we are leaving

			updateLayoutData(
				{
					classNameId: state.classNameId,
					classPK: state.classPK,
					data: state.layoutData,
					portletNamespace: state.portletNamespace,
					segmentsExperienceId: state.segmentsExperienceId || state.defaultSegmentsExperienceId,
					updateLayoutPageTemplateDataURL: state.updateLayoutPageTemplateDataURL
				}
			)
				.then(
					() => {
						const prevLayout = nextState.layoutData;
						const prevSegmentsExperienceId = state.segmentsExperienceId || nextState.defaultSegmentsExperienceId;

						const {layoutData} = nextState.layoutDataList.find(
							segmentedLayout => {
								return segmentedLayout.segmentsExperienceId === segmentsExperienceId;
							}
						);

						nextState = setIn(
							nextState,
							['layoutData'],
							layoutData
						);

						const newlayoutDataList = nextState.layoutDataList.map(
							segmentedLayout => {
								let noSingleReturnSegmentedLayout = segmentedLayout;
								if (segmentedLayout.segmentsExperienceId === prevSegmentsExperienceId) {
									noSingleReturnSegmentedLayout = Object.assign(
										{},
										segmentedLayout,
										{
											layoutData: prevLayout
										}
									);
								}
								return noSingleReturnSegmentedLayout;
							}
						);

						nextState = setIn(
							nextState,
							['layoutDataList'],
							newlayoutDataList
						);

						resolve(nextState);
					}
				)
				.catch(
					(error) => {
						reject(error);
					}
				);
		}
		catch (e) {
			reject(e);
		}

	});
}

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {string} payload.segmentsEntryId
 * @param {string} payload.name
 * @return {Promise}
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
						segmentsEntryId: segmentsEntryId,
						serviceContext: JSON.stringify(
							{
								scopeGroupId: themeDisplay.getScopeGroupId(),
								userId: themeDisplay.getUserId()
							}
						)
					},
					(obj) => {

						const {
							active,
							nameCurrentValue,
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
								segmentsEntryId,
								segmentsExperienceId
							}
						);

						_storeNewLayoutData(
							nextState,
							segmentsExperienceId
						).then(
							response => {
								_switchLayoutDataList(response, segmentsExperienceId)
									.then(
										(newState) => {
											let nextNewState = setIn(
												newState,
												['segmentsExperienceId'],
												segmentsExperienceId
											);
											resolve(nextNewState);
										}
									).catch(
										(e) => {
											reject(e);
										}
									);
							}
						);
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

function _removeExperience(
	{
		fragmentEntryLinkIds,
		portletNamespace,
		segmentsExperienceId,
		deleteSegmentsExperienceURL 
	}
) {
	const formData = new FormData();

	formData.append(`${portletNamespace}segmentsExperienceId`, segmentsExperienceId);

	if (fragmentEntryLinkIds) {
		formData.append(
			`${portletNamespace}fragmentEntryLinkIds`,
			JSON.stringify(fragmentEntryLinkIds)
		);
	}

	return fetch(
		deleteSegmentsExperienceURL,
		{
			body: formData,
			credentials: 'include',
			method: 'POST'
		}
	);
}

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {object} payload
 * @param {!string} payload.experienceId
 * @returns {Promise}
 */
function deleteSegmentsExperienceReducer(state, actionType, payload) {
	return new Promise(
		(resolve, reject) => {
			try {
				let nextState = state;
				if (actionType === DELETE_SEGMENTS_EXPERIENCE) {
					const {segmentsExperienceId} = payload;
					
					const priority = nextState.availableSegmentsExperiences[segmentsExperienceId].priority;

					const fragmentEntryLinkIdsInCurrentLayout = nextState
						.layoutData
						.structure
						.reduce(
							(acc, row) => {
								return [
									...getRowFragmentEntryLinkIds(row),
									...acc
								];
							},
							[]
						);
					
					const fragmentEntryLinkIds = fragmentEntryLinkIdsInCurrentLayout.filter(
						fragmentId => !containsFragmentEntryLinkId(
							nextState.layoutDataList,
							fragmentId,
							segmentsExperienceId
						)
					);

					_removeExperience({
						deleteSegmentsExperienceURL: nextState.deleteSegmentsExperienceURL,
						portletNamespace: nextState.portletNamespace,
						classNameId: nextState.classNameId,
						classPK: nextState.classPK,
						fragmentEntryLinkIds: fragmentEntryLinkIds,
						segmentsExperienceId
					}).then(() => {
						let availableSegmentsExperiences = Object.assign(
							{},
							nextState.availableSegmentsExperiences
						);

						delete availableSegmentsExperiences[segmentsExperienceId];
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
					})
				}
				else {
					resolve(nextState);
				}
			}
			catch (e) {
				reject(e);
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
 * @returns {Promise}
 */
function selectSegmentsExperienceReducer(state, actionType, payload) {
	return new Promise((resolve, reject) => {
		let nextState = state;
		if (actionType === SELECT_SEGMENTS_EXPERIENCE) {
			if (payload.segmentsExperienceId === nextState.segmentsExperienceId) {
				resolve(nextState);
			}
			else {
				_switchLayoutDataList(nextState, payload.segmentsExperienceId)
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
					.catch(
						e => {
							reject(e);
						}
					);
			}
		}
		else {
			resolve(nextState);
		}
	});
}

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {!string} payload.segmentsEntryId
 * @param {!string} payload.name
 * @param {!string} payload.segmentsExperienceId
 * @return {Promise}
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
 * @return {Promise}
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