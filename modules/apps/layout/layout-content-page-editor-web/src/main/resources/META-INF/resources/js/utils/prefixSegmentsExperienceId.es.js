/**
 * Returns a prefixed id
 * if the value is not undefined
 *
 * @export
 * @param {string|number} [segmentsExperienceId]
 * @returns {string}
 */
export function prefixSegmentsExperienceId(segmentsExperienceId) {
	return (segmentsExperienceId === undefined) ?
		undefined :
		(SEGMENT_EXPERIENCE_ID_PREFIX + segmentsExperienceId);
}

const SEGMENT_EXPERIENCE_ID_PREFIX = 'segments-experience-id-';