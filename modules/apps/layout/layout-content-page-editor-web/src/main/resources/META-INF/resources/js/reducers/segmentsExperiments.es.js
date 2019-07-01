import {CREATE_SEGMENTS_EXPERIMENT} from '../actions/actions.es';

export function createSegmentsExperimentsReducer(state, action) {
	return new Promise((resolve, reject) => {
		if (action.type === CREATE_SEGMENTS_EXPERIMENT) {
			const {name, description} = action.payload;
			const segmentsExperienceId =
				state.segmentsExperienceId || state.defaultSegmentsExperienceId;

			_fakeCreateExperiment({
				name,
				description,
				segmentsExperienceId
			}).then(experiment => {
				resolve({
					...state,
					segmentsExperiments: [
						experiment,
						...state.segmentsExperiments
					]
				});
			});
		} else {
			resolve(state);
		}
	});
}

function _fakeCreateExperiment({name, description, segmentsExperienceId}) {
	return new Promise(function(resolve, reject) {
		setTimeout(function() {
			resolve({
				segmentsExperiementId: Math.random().toPrecision(),
				name,
				description,
				segmentsExperienceId,
				status: 'draft'
			});
		}, 2000);
	});
}
