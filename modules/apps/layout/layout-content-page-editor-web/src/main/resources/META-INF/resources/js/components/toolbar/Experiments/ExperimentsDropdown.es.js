import React, {useState} from 'react';
import PropTypes from 'prop-types';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayButton from '@clayui/button';
import ClayLabel from '@clayui/label';
import CreateExperimentModal from './CreateExperimentModal.es';
import {_mapExperimentsStatus} from '../../../reducers/segmentsExperiments.es';

function ExperimentsDropdown({
	createExperiment,
	activeExperience,
	experiments
}) {
	const [active, setActive] = useState(false);
	const [modalShown, setModalShown] = useState(false);

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
						{Liferay.Language.get('experiments-cta')}
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
								{Liferay.Language.get(
									'experiments-dropdown-title'
								)}
							</h6>
						</div>
						<ClayDropDown.ItemList className='pb-1'>
							{filteredExperiments.map(experiment => {
								return (
									<ClayDropDown.Item
										className='d-flex justify-content-between align-items-start'
										key={experiment.segmentsExperimentId}
									>
										<span
											data-testid='experiment-name'
											className='truncate-text mr-2'
										>
											{experiment.name}
										</span>
										<ClayLabel
											data-testid='experiment-status'
											displayType='secondary'
										>
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
						<h2>
							{Liferay.Language.get(
								'experiments-empty-state-message-title'
							)}
						</h2>
						<p>
							{Liferay.Language.get(
								'experiments-empty-state-message-paragraph'
							)}
						</p>
					</div>
				)}
				{canCreateExperiment && (
					<div className='px-3 py-2'>
						<ClayButton
							data-testid='create-experiment-button'
							className='w-100'
							onClick={() => setModalShown(true)}
						>
							{Liferay.Language.get('ab-tests-create-new-test')}
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
