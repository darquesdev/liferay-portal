/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import PropTypes from 'prop-types';
import React from 'react';

import {config} from '../../app/config/index';
import itemSelectorValueToLayout from '../../app/utils/item-selector-value/itemSelectorValueToLayout';
import ItemSelector from './ItemSelector';

export const LayoutSelector = ({mappedLayout, onLayoutSelect}) => {
	return (
		<div className="mb-3">
			<ItemSelector
				eventName={`${config.portletNamespace}selectLayout`}
				itemSelectorURL={config.layoutItemSelectorURL}
				label={Liferay.Language.get('page')}
				onItemSelect={(layout) => onLayoutSelect(layout)}
				selectedItemTitle={mappedLayout?.name || ''}
				showMappedItems={false}
				transformValueCallback={itemSelectorValueToLayout}
			/>
		</div>
	);
};

LayoutSelector.propTypes = {
	mappedLayout: PropTypes.shape({name: PropTypes.string.isRequired}),
	onLayoutSelect: PropTypes.func.isRequired,
};
