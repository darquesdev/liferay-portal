import React from 'react';
import LocalizedDropdown from './LocalizedDropdown.es'

export default class LocalizedInput extends React.Component {
	static defaultProps = {
		onChange: () => {}
	}

	constructor(props) {
		super(props);
		this.state = {
			currentLang: props.initialLang,
			values: props.initialValues,
			currentValue: props.initialValues[props.initialLang] || '',
			availableLanguages: Object.entries(props.availableLanguages).map(
				([key, value]) => {
					return {
						key,
						value,
						hasValue: !!props.initialValues[key]
					}
				}
			)
		}
	}

	_handleLanguageChange = (langKey) => {
		this.setState(prevState => {
			return {
				currentLang: langKey,
				currentValue: prevState.values[langKey] || ''
			}
		});
	}

	_onLocalizedChange = event => {
		this.props.onChange(
			event,
			this.state.values
		);
	}

	_handleInputChange = event => {
		event.persist();
		this.setState(prevState => {
			const newValues = {
				...prevState.values,
				[prevState.currentLang]: event.target.value
			}
			return {
				values: newValues,
				currentValue: event.target.value,
				availableLanguages: prevState.availableLanguages.map(lang => {
					if (lang.key === prevState.currentLang) return {
						...lang,
						hasValue: event.target.value !== ''
					}
					return lang
				})
			}
		},
		() => this._onLocalizedChange(event)
		)
	}

	render() {
		const {
			initialOpen,
			initialLang,
			defaultLang,
			disabled
		} = this.props;

		return (
			<React.Fragment>
				<div className="input-group input-localized input-localized-input">
					<LocalizedDropdown
						onLanguageChange={this._handleLanguageChange}
						initialOpen={initialOpen}
						availableLanguages={this.state.availableLanguages}
						initialLang={initialLang}
						defaultLang={defaultLang}
					/>
					<input
						readOnly={disabled}
						className='ml-3 rounded form-control language-value field form-control-inline form-control'
						type="text"
						value={this.state.currentValue}
						onChange={
							this._handleInputChange
						}
					/>
				</div>
			</React.Fragment>
		);
	}
}