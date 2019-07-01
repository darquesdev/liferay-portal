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
	const [name, setName] = useState('');
	const [description, setDescription] = useState('');
	const {portletNamespace} = useContext(ExperimentsContext);

	const formId = `${portletNamespace}_createExperiement`;

	return (
		<>
			{visible && (
				<ClayModal onClose={_closeModal} size='lg'>
					{onClose => (
						<>
							<ClayModal.Header>{'Title'}</ClayModal.Header>
							<ClayModal.Body>
								<form id={formId} onSubmit={_handleSaveAction}>
									<div className='form-group'>
										<label>Name:</label>
										<input
											className='form-control'
											value={name}
											onChange={_inputValueGetter(
												setName
											)}
										/>
									</div>

									<div className='form-group'>
										<label>Description:</label>
										<textarea
											className='form-control'
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
											onClick={onClose}
											displayType='secondary'
										>
											{'Cancel'}
										</ClayButton>
									</ClayButton.Group>
								}
								last={
									<ClayButton
										form={formId}
										onClick={_handleSaveAction}
									>
										{'Save'}
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
