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

import React, {useState} from 'react';
import PropTypes from 'prop-types';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayTable from '@clayui/table';
import ClayButton from '@clayui/button';

function Variant({name, control = false, active}) {
	const [openDropdown, setOpenDropdown] = useState(false);

	const firstCellAttributes = control
		? {
				colSpan: '2',
				expanded: true,
				headingTitle: true
		  }
		: {
				expanded: true,
				headingTitle: true
		  };

	return (
		<ClayTable.Row active={active}>
			<ClayTable.Cell {...firstCellAttributes}>
				{control ? Liferay.Language.get('variant-control') : name}
			</ClayTable.Cell>
			{!control && (
				<ClayTable.Cell>
					<ClayDropDown
						active={openDropdown}
						onActiveChange={setOpenDropdown}
						trigger={
							<ClayButton
								aria-label={Liferay.Language.get(
									'show-actions'
								)}
								borderless
								displayType="secondary"
								small
							>
								<ClayIcon symbol="ellipsis-v" />
							</ClayButton>
						}
					/>
				</ClayTable.Cell>
			)}
		</ClayTable.Row>
	);
}

Variant.propTypes = {
	active: PropTypes.bool.isRequired,
	control: PropTypes.bool.isRequired,
	name: PropTypes.string.isRequired
};

export default Variant;
