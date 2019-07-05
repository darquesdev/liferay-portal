import {CREATE_SEGMENTS_EXPERIMENT} from '../actions/actions.es';

export function createSegmentsExperimentsReducer(state, action) {
	return new Promise((resolve, reject) => {
		if (action.type === CREATE_SEGMENTS_EXPERIMENT) {
			const {name, description} = action.payload;
			const {classPK, classNameId} = state;
			const segmentsExperienceId =
				state.segmentsExperienceId || state.defaultSegmentsExperienceId;

			_createExperiment({
				name,
				description,
				segmentsExperienceId,
				classPK,
				classNameId
			}).then(experiment => {
				resolve({
					...state,
					availableSegmentsExperiments: [
						experiment,
						...state.availableSegmentsExperiments
					]
				});
			});
		} else {
			resolve(state);
		}
	});
}

/**
 *
 *
 * @param {object} experimentData
 * @param {string} experimentData.name
 * @param {string} experimentData.description
 * @param {string} experimentData.segmentsExperienceId
 * @param {string} experimentData.classPK
 * @returns {Promise}
 */
function _createExperiment({
	name,
	description,
	segmentsExperienceId,
	classPK,
	classNameId
}) {
	return new Promise(function(resolve, reject) {
		Liferay.Service(
			'/segments.segmentsexperiment/add-segments-experiment',
			{
				segmentsExperienceId,
				name,
				description,
				classPK,
				classNameId
			},
			function _successCallback(obj) {
				const {name, description, segmentsExperienceId, status} = obj;
				resolve({
					name,
					description,
					segmentsExperienceId,
					status
				});
			},
			function _errorCallback(error) {
				console.log(error);
			}
		);
	});
}

export function _mapExperimentsStatus(statusInt) {
	switch (statusInt) {
		case 0:
		default:
			return 'draft';
	}
}
