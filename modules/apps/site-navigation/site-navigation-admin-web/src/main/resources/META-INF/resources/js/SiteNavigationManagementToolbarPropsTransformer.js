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

import {openSimpleInputModal} from 'frontend-js-web';

export default function propsTransformer({portletNamespace, ...otherProps}) {
	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			const action = item.data?.action;

			if (action === 'deleteSelectedSiteNavigationMenus') {
				if (
					confirm(
						Liferay.Language.get(
							'are-you-sure-you-want-to-delete-this'
						)
					)
				) {
					const form = document.getElementById(
						`${portletNamespace}fm`
					);

					if (form) {
						submitForm(form);
					}
				}
			}
		},
		onCreateButtonClick(event, {item}) {
			const action = item.data?.action;

			if (action === 'addSiteNavigationMenu') {
				openSimpleInputModal({
					dialogTitle: Liferay.Language.get('add-menu'),
					formSubmitURL: item.data.addSiteNavigationMenuURL,
					mainFieldLabel: Liferay.Language.get('name'),
					mainFieldName: 'name',
					mainFieldPlaceholder: Liferay.Language.get('name'),
					namespace: portletNamespace,
				});
			}
		},
	};
}
