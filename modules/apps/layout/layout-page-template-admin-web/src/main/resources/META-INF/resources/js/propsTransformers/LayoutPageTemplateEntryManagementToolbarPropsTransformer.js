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
	const deleteLayoutPageTemplateEntries = () => {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			const form = document.getElementById(`${portletNamespace}fm`);

			if (form) {
				submitForm(form);
			}
		}
	};

	const exportLayoutPageTemplateEntries = (itemData) => {
		const form = document.getElementById(`${portletNamespace}fm`);

		if (form) {
			submitForm(form, itemData.exportLayoutPageTemplateEntryURL);
		}
	};

	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			const data = item.data;

			const action = data?.action;

			if (action === 'deleteLayoutPageTemplateEntries') {
				deleteLayoutPageTemplateEntries();
			}
			else if (action === 'exportLayoutPageTemplateEntries') {
				exportLayoutPageTemplateEntries(data);
			}
		},
		onCreationMenuItemClick(event, {item}) {
			if (item.data?.action === 'addLayoutPageTemplateEntry') {
				openSimpleInputModal({
					dialogTitle: Liferay.Language.get('add-page-template'),
					formSubmitURL: item.data?.addPageTemplateURL,
					mainFieldLabel: Liferay.Language.get('name'),
					mainFieldName: 'name',
					mainFieldPlaceholder: Liferay.Language.get('name'),
					namespace: portletNamespace,
				});
			}
		},
	};
}
