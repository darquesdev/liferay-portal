import {
	CREATE_EXPERIENCE,
	END_CREATE_EXPERIENCE,
	REMOVE_EXPERIENCE,
	SELECT_EXPERIENCE,
	START_CREATE_EXPERIENCE
} from '../actions/actions.es';
import {setIn} from '../utils/utils.es';


const CREATE_EXPERIENCE_URL = '/segments.segmentsexperience/add-segments-experience';

const EXPERIENCE_CREDENTIALS = 'include';

const REMOVE_EXPERIENCE_URL = '/something/to/talk/remove';

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {object} payload
 * @param {string} payload.segmentId
 * @return {object}
 * @review
 */
function createExperienceReducer(state, actionType, payload) {
	return new Promise(
		resolve => {
			let nextState = state;
			if (actionType === CREATE_EXPERIENCE) {
				const {experienceLabel, segmentId} = payload;

				const formData = new FormData();

				const {
					classNameId,
					classPK,
					portletNamespace
				} = state;

				const nameMap = { en_US: experienceLabel };

				Liferay.Service(
					CREATE_EXPERIENCE_URL,
					{
						classNameId,
						classPK,
						segmentsEntryId: segmentId,
						nameMap: JSON.stringify(nameMap),
						active: true,
						priority: 1
					},
					(obj) => {

						const {
							active,
							nameCurrentValue,
							priority,
							segmentsEntryId,
							segmentsExperienceId
						} = obj;

						nextState = Object.assign(
							{},
							nextState,
							{
								availableExperiences: Object.assign(
									{},
									nextState.availableExperiences,
									{
										[segmentsExperienceId]: {
											active,
											experienceId: segmentsExperienceId,
											experienceLabel: nameCurrentValue,
											priority,
											segmentId: segmentsEntryId
										}
									}
								),
								experienceId: segmentsExperienceId
							}
						);
						resolve(nextState);
					},
					error => {
						resolve(nextState);
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
 * @param {string} payload.segmentId
 * @return {object}
 * @review
 */
function startCreateExperience(state, actionType, payload) {
	if (actionType === START_CREATE_EXPERIENCE) {
		return Object.assign(
			{},
			state,
			{
				experienceCreation: Object.assign(
					{},
					state.experienceCreation,
					{
						creatingExperience: true,
						error: null,
					}
				)
			}
		)
	}
	return state;
}

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {object} payload
 * @param {string} payload.segmentId
 * @return {object}
 * @review
 */
function endCreateExperience(state, actionType, payload) {
	if (actionType === END_CREATE_EXPERIENCE) {
		return Object.assign(
			{},
			state,
			{
				experienceCreation: Object.assign(
					{},
					state.experienceCreation,
					{
						creatingExperience: false,
						error: null,
					}
				)
			}
		)
	}
	return state;
}

function removeExperienceReducer(state, actionType, payload) {
	return new Promise(
		resolve => {
			let nextState = state;

			const {experienceId} = payload;

			const body = new FormData();

			body.append('experienceId', experienceId);

			if (actionType === REMOVE_EXPERIENCE) {
				fetch(
					REMOVE_EXPERIENCE_URL,
					{
						body,
						credentials: EXPERIENCE_CREDENTIALS,
						method: 'POST'
					}
				)
					.then(
						() => {
							delete nextState.experiences[experienceId];
							resolve(nextState);
						}
					);
			}
			else {
				resolve(nextState);
			}
		}
	);
}

function selectExperienceReducer(state, actionType, payload) {
	let nextState = state;

	if (actionType === SELECT_EXPERIENCE) {
		nextState = setIn(
			nextState,
			['experienceId'],
			payload.experienceId,
		);
	}

	return nextState;
}

export {
	createExperienceReducer,
	endCreateExperience,
	removeExperienceReducer,
	selectExperienceReducer,
	startCreateExperience
};