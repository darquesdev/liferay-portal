import React, {useState} from 'react';
import PropTypes from 'prop-types';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayButton from '@clayui/button';
import ClayLabel from '@clayui/label';
import CreateExperimentModal from './CreateExperimentModal.es';
import {_mapExperimentsStatus} from '../../../reducers/segmentsExperiments.es';

function ExperimentsDropdown(props) {
	const [active, setActive] = useState(false);
	const [modalShown, setModalShown] = useState(false);

	const {createExperiment, activeExperience, experiments} = props;

	const filteredExperiments = experiments.filter(
		e => e.segmentsExperienceId === activeExperience
	);

	const canCreateExperiment = !filteredExperiments.some(
		experiment => experiment.status === 0
	);

	return (
		<>
			<ClayDropDown
				className='ml-2'
				trigger={
					<ClayButton small={true} displayType={'secondary'}>
						A/B
						<ClayIcon symbol={'caret-bottom'} />
					</ClayButton>
				}
				active={active}
				onActiveChange={setActive}
			>
				{filteredExperiments.length > 0 ? (
					<>
						<div className='px-3 pt-2'>
							<h6 className='text-uppercase'>
								A/B Tests for current Experience
							</h6>
						</div>
						<ClayDropDown.ItemList className='pb-1'>
							{filteredExperiments.map(experiment => {
								return (
									<ClayDropDown.Item
										className='d-flex justify-content-between align-items-start'
										key={experiment.segmentsExperimentId}
									>
										<span className='truncate-text mr-2'>
											{experiment.name}
										</span>
										<ClayLabel displayType='secondary'>
											{_mapExperimentsStatus(
												experiment.status
											)}
										</ClayLabel>
									</ClayDropDown.Item>
								);
							})}
						</ClayDropDown.ItemList>
					</>
				) : (
					<div className='px-3 pt-3'>
						<h2>No A/B Tests yet</h2>
						<p>Please, click create a New Test</p>
					</div>
				)}
				{canCreateExperiment && (
					<div className='px-3 py-2'>
						<ClayButton className='w-100' onClick={setModalShown}>
							Create a New Test
						</ClayButton>
					</div>
				)}
			</ClayDropDown>
			{modalShown && (
				<CreateExperimentModal
					onCreateExperiment={createExperiment}
					visible={modalShown}
					setVisible={setModalShown}
				/>
			)}
		</>
	);
}

ExperimentsDropdown.propTypes = {
	createExperiment: PropTypes.func.isRequired,
	activeExperience: PropTypes.string.isRequired,
	experiments: PropTypes.arrayOf(
		PropTypes.shape({
			segmentsExperimentId: PropTypes.string.isRequired,
			name: PropTypes.string.isRequired,
			description: PropTypes.string,
			segmentsExperienceId: PropTypes.string.isRequired,
			status: PropTypes.number.isRequired
		})
	)
};

export default ExperimentsDropdown;
