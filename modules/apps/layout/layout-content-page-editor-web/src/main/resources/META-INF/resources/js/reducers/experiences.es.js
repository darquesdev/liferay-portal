import {CREATE_EXPERIENCE, END_CREATE_EXPERIENCE, SELECT_EXPERIENCE, START_CREATE_EXPERIENCE} from '../actions/actions.es';
import {setIn} from '../utils/utils.es';

const CREATE_EXPERIENCE_URL = '/segments.segmentsexperience/add-segments-experience';

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {string} payload.segmentId
 * @param {string} payload.experienceLabel
 * @return {object}
 * @review
 */
function createExperienceReducer(state, actionType, payload) {
	return new Promise(
		resolve => {
			let nextState = state;
			if (actionType === CREATE_EXPERIENCE) {
				const {experienceLabel, segmentId} = payload;

				const {
					classNameId,
					classPK
				} = nextState;

				const nameMap = JSON.stringify(
					{
						[state.defaultLanguageId]: experienceLabel
					}
				);
				const priority = Object.values(nextState.availableExperiences || []).length;

				Liferay.Service(
					CREATE_EXPERIENCE_URL,
					{
						active: true,
						classNameId,
						classPK,
						nameMap,
						priority,
						segmentsEntryId: segmentId
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
								'availableExperiences',
								segmentsExperienceId
							],
							{
								active,
								experienceId: segmentsExperienceId,
								experienceLabel: nameCurrentValue,
								priority,
								segmentId: segmentsEntryId
							}
						);

						nextState = setIn(
							nextState,
							['experienceId'],
							segmentsExperienceId
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
 * @return {object}
 * @review
 */
function startCreateExperience(state, actionType) {
	let nextState = state;

	if (actionType === START_CREATE_EXPERIENCE) {
		nextState = setIn(
			nextState,
			['experienceCreation'],
			{
				creatingExperience: true,
				error: null
			}
		);
	}
	return nextState;
}

/**
 * @param {!object} state
 * @param {!string} actionType
 * @return {object}
 * @review
 */
function endCreateExperience(state, actionType) {
	let nextState = state;

	if (actionType === END_CREATE_EXPERIENCE) {
		nextState = setIn(
			nextState,
			['experienceCreation'],
			{
				creatingExperience: false,
				error: null
			}
		);
	}
	return nextState;
}

/**
 *
 *
 * @export
 * @param {!object} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {!string} payload.experienceId
 * @returns
 */
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
	startCreateExperience,
	endCreateExperience,
	selectExperienceReducer
};