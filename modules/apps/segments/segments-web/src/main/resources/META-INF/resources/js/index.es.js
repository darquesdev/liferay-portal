import React from 'react';
import ReactDOM from 'react-dom';
import SegmentEdit from './components/segment_edit/SegmentEdit.es';
import ThemeContext from './ThemeContext.es';

export default function(id, props, context) {
	const preps = {
		...props,
		initialSegmentName: JSON.parse(props.initialSegmentName)
	}
	ReactDOM.render(
		<ThemeContext.Provider value={context}>
			<div className="segments-root">
				<SegmentEdit {...preps} />
			</div>
		</ThemeContext.Provider>,
		document.getElementById(id)
	);
}