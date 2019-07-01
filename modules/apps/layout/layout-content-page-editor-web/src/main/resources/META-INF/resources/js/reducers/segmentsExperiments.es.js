import {CREATE_SEGMENTS_EXPERIMENT} from '../actions/actions.es';

export function createSegmentsExperimentsReducer(state, action) {
	return new Promise((resolve, reject) => {
		if (action.type === CREATE_SEGMENTS_EXPERIMENT) {
			const {name, description} = action.payload;
			const segmentsExperienceId =
				state.segmentsExperienceId || state.defaultSegmentsExperienceId;

			_createExperiment({
				name,
				description,
				segmentsExperienceId
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

function _createExperiment({name, description, segmentsExperienceId}) {
	return new Promise(function(resolve, reject) {
		Liferay.Service(
			'/segments.segmentsexperiment/add-segments-experience',
			{
				segmentsExperienceId,
				name,
				description
			},
			obj => {
				const {name, description, segmentsExperienceId, status} = obj;
				resolve({
					name,
					description,
					segmentsExperienceId,
					status: _mapExperimentsStatus(status)
				});
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
