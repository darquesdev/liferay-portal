import React from 'react';
import {
	cleanup,
	render,
	fireEvent,
	waitForDomChange
} from 'react-testing-library';
import ExperimentsDropdown from '../../../../../src/main/resources/META-INF/resources/js/components/toolbar/Experiments/ExperimentsDropdown.es';

describe('ExperimentsDropdown', () => {
	afterEach(cleanup);

	it('renders empty state message with button', () => {
		const mockCreateExperiment = jest.fn();

		const {container} = render(
			<ExperimentsDropdown
				createExperiment={mockCreateExperiment}
				activeExperience={'0'}
				experiments={[]}
			/>
		);

		// there is a button to toggle the dropdown
		const toggleButton = container.querySelector('.dropdown-toggle');
		expect(toggleButton).not.toBe(null);

		// once toggled the dropdown shows the empty state message
		fireEvent.click(toggleButton, {});
		const dropdownMenu = document.body.querySelector('.dropdown-menu');
		expect(dropdownMenu.querySelector('h2').textContent).toContain(
			'experiments-empty-state-message-title'
		);
	});

	it('allows to create experiement from empty state', done => {
		const mockCreateExperiment = jest.fn();

		const {container} = render(
			<ExperimentsDropdown
				createExperiment={mockCreateExperiment}
				activeExperience={'0'}
				experiments={[]}
			/>
		);

		const toggleButton = container.querySelector('.dropdown-toggle');
		fireEvent.click(toggleButton, {});

		const dropdownMenu = document.body.querySelector('.dropdown-menu');

		// the dropdown offers a button to create an Experiment
		const newExperimentButton = dropdownMenu.querySelector(
			'[data-testid="create-experiment-button"]'
		);
		expect(newExperimentButton.textContent).toBe(
			'experiements-create-new-test'
		);
		fireEvent.click(newExperimentButton, {});

		waitForDomChange(() => document.querySelector('.modal-backdrop')).then(
			() => {
				const {
					experimentNameInput,
					experimentDescriptionInput,
					saveButton,
					cancelButton
				} = _getExperimentsModalUIElements(document.body);

				// Modal elements are present
				expect(saveButton).not.toBe(null);
				expect(cancelButton).not.toBe(null);
				expect(experimentNameInput).not.toBe(null);
				expect(experimentDescriptionInput).not.toBe(null);

				// Input name has default values
				expect(experimentNameInput.value).toBe('experiment-new');

				fireEvent.change(experimentNameInput, {
					target: {value: 'New Experiment Name'}
				});

				expect(experimentNameInput.value).toBe('New Experiment Name');

				fireEvent.click(saveButton, {});

				// createExperiment action has been triggered
				expect(mockCreateExperiment).toHaveBeenCalledWith({
					name: 'New Experiment Name',
					description: ''
				});

				done();
			}
		);
	});

	it('renders with a draft experience', () => {
		const mockCreateExperiment = jest.fn();

		const {container} = render(
			<ExperimentsDropdown
				createExperiment={mockCreateExperiment}
				activeExperience={'0'}
				experiments={[
					{
						segmentsExperimentId: 'experiment-id',
						name: 'Existing Experience',
						segmentsExperienceId: '0',
						status: 0
					}
				]}
			/>
		);

		const toggleButton = container.querySelector('.dropdown-toggle');
		fireEvent.click(toggleButton, {});

		const dropdownMenu = document.body.querySelector('.dropdown-menu');
		const experimentListTitle = dropdownMenu.querySelector('h6');

		expect(experimentListTitle.textContent).toBe(
			'experiments-dropdown-title'
		);
		const experimentsListItems = dropdownMenu.querySelectorAll(
			'.dropdown-item'
		);

		expect(experimentsListItems.length).toBe(1);

		const {experimentName, experimentStatus} = _getExperimentsUIElements(
			experimentsListItems[0]
		);

		expect(experimentName.textContent).toBe('Existing Experience');
		expect(experimentStatus.textContent).toBe('draft');

		const newExperimentButton = dropdownMenu.querySelector(
			'[data-testid="create-experiment-button"]'
		);

		expect(newExperimentButton).toBe(null);
	});
});

/**
 * Find some common UI elements in the Experiments Modal from the body
 *
 * @param {HTMLBodyElement} body
 * @returns {object}
 */
function _getExperimentsModalUIElements(body) {
	const experimentNameInput = body.querySelector(
		'[data-testid=create-experiment-name-input]'
	);
	const experimentDescriptionInput = body.querySelector(
		'[data-testid="create-experiment-description-input"]'
	);
	const saveButton = body.querySelector(
		'[data-testid="create-experiment-save"'
	);
	const cancelButton = body.querySelector(
		'[data-testid="create-experiment-cancel"'
	);
	return {
		experimentNameInput,
		experimentDescriptionInput,
		saveButton,
		cancelButton
	};
}

/**
 * Finds some common UI elements in the Experiments Item element
 *
 * @param {HTMLElement} experimentItemElement
 * @returns {object}
 */
function _getExperimentsUIElements(experimentItemElement) {
	const experimentName = experimentItemElement.querySelector(
		`[data-testid="experiment-name"]`
	);
	const experimentStatus = experimentItemElement.querySelector(
		`[data-testid="experiment-status"]`
	);

	return {
		experimentName,
		experimentStatus
	};
}
