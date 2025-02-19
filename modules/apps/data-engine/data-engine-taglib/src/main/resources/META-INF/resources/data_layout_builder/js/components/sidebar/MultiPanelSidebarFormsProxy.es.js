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

import {ClayIconSpriteContext} from '@clayui/icon';
import {getConnectedReactComponentAdapter} from 'dynamic-data-mapping-form-renderer/js/util/ReactComponentAdapter.es';
import React, {useState} from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import MultiPanelSidebar from './MultiPanelSidebar.es';

const MultiPanelSidebarFormsProxy = React.forwardRef(
	(
		{
			activePage,
			dataProviderInstanceParameterSettingsURL,
			dataProviderInstancesURL,
			defaultLanguageId,
			editingLanguageId,
			fieldTypes,
			focusedField,
			functionsMetadata,
			functionsURL,
			instance,
			onChange,
			pages,
			panels,
			rules,
			sidebarPanels,
			sidebarVariant,
			spritemap,
		},
		forwardRef
	) => {
		const [{currentPanelId, open}, setStatus] = useState({
			currentPanelId: 'fields',
			open: true,
		});

		return (
			<DndProvider backend={HTML5Backend} context={window}>
				<ClayIconSpriteContext.Provider value={spritemap}>
					<FormsSidebarPluginContext.Provider
						value={{
							activePage,
							dataProviderInstanceParameterSettingsURL,
							dataProviderInstancesURL,
							defaultLanguageId,
							dispatch: (type, payload) => {
								instance.context.dispatch(type, payload);
							},
							editingLanguageId,
							fieldTypes,
							focusedCustomObjectField: {},
							focusedField,
							functionsMetadata,
							functionsURL,
							pages,
							rules,
						}}
					>
						<MultiPanelSidebar
							createPlugin={({
								panel,
								sidebarOpen,
								sidebarPanelId,
							}) => ({
								panel,
								sidebarOpen,
								sidebarPanelId,
							})}
							currentPanelId={currentPanelId}
							onChange={({sidebarOpen, sidebarPanelId}) => {
								onChange(sidebarOpen);
								setStatus({
									currentPanelId: sidebarPanelId,
									open: sidebarOpen,
								});
							}}
							open={open}
							panels={panels}
							ref={forwardRef}
							sidebarPanels={sidebarPanels}
							variant={sidebarVariant}
						/>
					</FormsSidebarPluginContext.Provider>
				</ClayIconSpriteContext.Provider>
			</DndProvider>
		);
	}
);

export const FormsSidebarPluginContext = React.createContext({});

MultiPanelSidebarFormsProxy.displayName = 'MultiPanelSidebarFormsProxy';

export const ReactMultiPanelSidebarAdapter = getConnectedReactComponentAdapter(
	MultiPanelSidebarFormsProxy
);
