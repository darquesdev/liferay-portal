/**
 * Returns a prefixed id
 * if the value is not undefined
 *
 * @export
 * @param {string|number} [experienceId]
 * @returns {string}
 */
export function prefixExperienceId(experienceId) {
	return (experienceId === undefined) ?
		undefined :
		(SEGMENT_EXPERIENCE_ID_PREFIX + experienceId);
}

const SEGMENT_EXPERIENCE_ID_PREFIX = 'segments-experience-id-';