import React from 'react';
import ClayIcon from '../shared/ClayIcon.es';

class LocalizedDropdown extends React.Component {
	constructor(props) {
		super(props);
		const {initialOpen, initialLang} = props;
		this.state = {
			open: initialOpen,
			currentLang: initialLang.replace(/_/g, "-").toLowerCase()
		}
	}
	_handleButtonClick = () => {
		this.setState(prevState => ({
			open: !prevState.open
		}));
	}
	_handleButtonBlur = e => {
		if (e.nativeEvent.explicitOriginalTarget &&
			e.nativeEvent.explicitOriginalTarget === e.nativeEvent.originalTarget) {
			return;
		}

		if (this.state.open) {
			this.timer = setTimeout(() => {
				this.setState(() => ({
						open: false
					})
				);
			}, 200);
		}
	}

	_changeLanguage = langKey => {
		this.setState({
			currentLang: langKey.replace(/_/g, "-").toLowerCase(),
			open: false
		},() => this.props.onLanguageChange(langKey))
	}

	_handleItemFocus = () => {
		clearTimeout(this.timer);
	}

	_handleLanguageClick = langKey => () => this._changeLanguage(langKey)

	_handleLanguageKeyboard = langKey => e => {
		if (e.keyCode === 13) this._changeLanguage(langKey)
	}

	render() {
		const {
			open,
			currentLang
		} = this.state;
		const {
			availableLanguages,
			defaultLang
		} = this.props;

		return <div className={`dropdown postion-relative lfr-icon-menu ${open ? 'open' : ''}`}>
					<button
						aria-expanded="false"
						aria-haspopup="true"
						className="btn btn-monospaced btn-secondary dropdown-toggle"
						title=""
						onClick={this._handleButtonClick}
						onBlur={this._handleButtonBlur}
						type="button"
						role="button"
					>
						<span className="inline-item">
							<ClayIcon iconName={currentLang} />
						</span>
						<span className="btn-section">{currentLang}</span>
					</button>
					{
						open &&
						<ul
							className="dropdown-menu d-block"
							role="menu"
							aria-labelledby="_com_liferay_dynamic_data_mapping_web_portlet_PortletDisplayTemplatePortlet__com_liferay_dynamic_data_mapping_web_portlet_PortletDisplayTemplatePortlet_nameMenu"
							aria-activedescendant="_com_liferay_dynamic_data_mapping_web_portlet_PortletDisplayTemplatePortlet___com__liferay__dynamic__data__mapping__web__portlet__PortletDisplayTemplatePortlet__nameMenu__en_2d_us_2d_span_2d_class_2d_label_2d_label_2d_info_2d_valor_2d_por_2d_defecto_2d__2f_span_2d_"
						>
							{
								availableLanguages.map(entry => {
									const {key, value, hasValue} = entry;

									return <li
											role="presentation"
											key={key}
											onFocus={this._handleItemFocus}
											onBlur={this._handleButtonBlur}
											onClick={this._handleLanguageClick(key)}
											onKeyDown={this._handleLanguageKeyboard(key)}
										>
											<a
												target="_self"
												className="dropdown-item palette-item lfr-icon-item taglib-icon"
												role="menuitem"
												tabIndex="0"
											>
												<span className="inline-item inline-item-before">
													<ClayIcon iconName={key.replace(/_/g, "-").toLowerCase()} />
												</span>
												<span className="taglib-text-icon"
													>
													{key.replace(/_/g, "-")}
													{
														defaultLang === key &&
														<span className="ml-1 label label-info">Valor por defecto</span>
													}
													{
														defaultLang !== key && (
															hasValue ? 
																<span className="ml-1 label label-success">Traducido</span> :
																<span className="ml-1 label label-warning">No Traducido</span>
															)
													}
												</span>
											</a>
										</li>
								})
							}
							
						</ul>}
			</div>
	}
}

export default LocalizedDropdown;