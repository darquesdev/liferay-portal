import React from 'react';
import ReactDOM from 'react-dom';
import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';
import {ClayIconSpriteContext} from '@clayui/icon';
import getConnectedComponent, {
	getConnectedReactComponent
} from '../../store/ConnectedComponent.es';
import Store from '../../store/store.es';
import {CREATE_SEGMENTS_EXPERIMENT} from '../../actions/actions.es';
import ExperimentsDropdown from './Experiments/ExperimentsDropdown.es';
import templates from './Experiments.soy';
import {ExperimentsContext} from './Experiments/Context.es';

class Experiments extends Component {
	syncStore(store, prevStore) {
		if (store && store !== prevStore) {
			_renderExperimentsApp(
				`${this.portletNamespace}experimentsId`,
				store,
				{
					portletNamespace: this.portletNamespace,
					spritemap: this.spritemap
				}
			);
		}
	}
}

Experiments.STATE = {
	portletNamespace: Config.string(),
	spritemap: Config.string(),
	store: Config.instanceOf(Store)
};

/**
 * Initialization of Experimients React App
 * Renders a connected to Store version of Experiments Dropdown
 *
 * @param {string} id - id to locate DOM element
 * @param {Store} store - instance of store to maintain the App connected
 * @param {object} context - values that won't change
 * @param {string} context.spritemap
 * @param {string} context.portletNamespace
 */
function _renderExperimentsApp(id, store, context) {
	const ConnectedExperiences = getConnectedReactComponent(
		function mapStateToProps(myState) {
			return {
				activeExperience:
					myState.segmentsExperienceId ||
					myState.defaultSegmentsExperienceId,
				experiments: myState.segmentsExperiments
			};
		},
		function mapDispatchToProps(dispatch) {
			return {
				createExperiment: ({name, description}) => {
					dispatch({
						payload: {
							name,
							description
						},
						type: CREATE_SEGMENTS_EXPERIMENT
					});
				}
			};
		},
		store
	)(ExperimentsDropdown);

	ReactDOM.render(
		<ExperimentsContext.Provider value={context.portletNamespace}>
			<ClayIconSpriteContext.Provider value={context.spritemap}>
				<ConnectedExperiences />
			</ClayIconSpriteContext.Provider>
		</ExperimentsContext.Provider>,
		document.getElementById(id)
	);
}

const ConnectedExperiments = getConnectedComponent(Experiments, ['languageId']);

Soy.register(ConnectedExperiments, templates);

export {ConnectedExperiments};
export default ConnectedExperiments;
