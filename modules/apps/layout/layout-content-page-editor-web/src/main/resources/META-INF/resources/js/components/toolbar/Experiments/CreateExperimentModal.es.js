import React, {useState, useContext} from 'react';
import PropTypes from 'prop-types';
import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import {ExperimentsContext} from './Context.es';

/**
 * Second order function to generate a function from a listener
 * that uses the `target.value` of an event
 */
const _inputValueGetter = listener => event => listener(event.target.value);

/**
 * Wrapper for the modal to create an Experiments
 */
function CreateExperimentModal({setVisible, visible, onCreateExperiment}) {
	const [name, setName] = useState(Liferay.Language.get('experiment-new'));
	const [description, setDescription] = useState('');
	const {portletNamespace} = useContext(ExperimentsContext);

	const formId = `${portletNamespace}_createExperiement`;

	return (
		<>
			{visible && (
				<ClayModal onClose={_closeModal} size='lg'>
					{onClose => (
						<>
							<ClayModal.Header>
								{Liferay.Language.get('experiment-settings')}
							</ClayModal.Header>
							<ClayModal.Body>
								<form id={formId} onSubmit={_handleSaveAction}>
									<div className='form-group'>
										<label>
											{Liferay.Language.get('name')}
										</label>
										<input
											className='form-control'
											data-testid='create-experiment-name-input'
											value={name}
											onChange={_inputValueGetter(
												setName
											)}
										/>
									</div>

									<div className='form-group'>
										<label>
											{Liferay.Language.get(
												'description'
											)}
										</label>
										<textarea
											className='form-control'
											data-testid='create-experiment-description-input'
											value={description}
											onChange={_inputValueGetter(
												setDescription
											)}
										/>
									</div>
								</form>
							</ClayModal.Body>
							<ClayModal.Footer
								first={
									<ClayButton.Group spaced>
										<ClayButton
											data-testid='create-experiment-cancel'
											onClick={onClose}
											displayType='secondary'
										>
											{Liferay.Language.get('cancel')}
										</ClayButton>
									</ClayButton.Group>
								}
								last={
									<ClayButton
										data-testid='create-experiment-save'
										form={formId}
										onClick={_handleSaveAction}
									>
										{Liferay.Language.get('save')}
									</ClayButton>
								}
							/>
						</>
					)}
				</ClayModal>
			)}
		</>
	);

	function _closeModal() {
		setVisible(false);
	}

	function _handleSaveAction(event) {
		event.preventDefault();
		onCreateExperiment({name, description});
		_closeModal();
	}
}

CreateExperimentModal.propTypes = {
	setVisible: PropTypes.func.isRequired,
	visible: PropTypes.bool.isRequired,
	onCreateExperiment: PropTypes.func.isRequired
};

export default CreateExperimentModal;
