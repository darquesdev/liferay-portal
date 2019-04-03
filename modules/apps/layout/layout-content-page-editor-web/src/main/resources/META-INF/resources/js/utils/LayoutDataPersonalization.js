
/**
 * Tells if a fragmentEntryLink is referenced in any (but the current one)
 * LayoutData inside LayoutDataPersonalization
 *
 * @param {!array} LayoutDataPersonalization
 * @param {!string} fragmentEntryLinkId
 * @param {string} currentExperienceId
 * @returns
 */
function confirmFragmentEntryLinkIdInLayoutDataPersonalization(
	LayoutDataPersonalization,
	fragmentEntryLinkId,
	currentExperienceId
) {
	const result = LayoutDataPersonalization.reduce((found, LayoutDataPersonalizationItem) => {
		if(found || LayoutDataPersonalizationItem.segmentsExperienceId === currentExperienceId) return found;
		return _confirmFragmentEntryLinkIdInLayout(
			LayoutDataPersonalizationItem.layoutData,
			fragmentEntryLinkId
		);
	}, false);

	return result;
};


/**
 * Tells if a fragmentEntryLink is referenced inside
 * a LayoutData
 *
 * @param {!object} layoutData
 * @param {!string} fragmentEntryLinkId
 * @returns
 */
function _confirmFragmentEntryLinkIdInLayout(
	layoutData,
	fragmentEntryLinkId
) {
  const structure = layoutData.structure;
  return structure.reduce((found, columnsWrapper) => {
    if (found || !columnsWrapper.columns) return found;
	const result = !!(
      columnsWrapper.columns.reduce((found, column) => {
		  if (found || !column.fragmentEntryLinkIds) return found;
        return !!(
          column.fragmentEntryLinkIds.find(id => id === fragmentEntryLinkId)
        );
      }, false)
    );
    return result;
  }, false);
};


export {
	confirmFragmentEntryLinkIdInLayoutDataPersonalization
}