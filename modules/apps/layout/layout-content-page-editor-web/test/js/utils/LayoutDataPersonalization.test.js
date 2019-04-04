import {
	confirmFragmentEntryLinkIdInLayoutDataPersonalization
} from '../../../src/main/resources/META-INF/resources/js/utils/LayoutDataPersonalization';

const LAYOUT_DATE_PERSONALIZATION = [
	{
		segmentsExperienceId: 'segmentsExperienceId1',
		layoutData: {
			"nextColumnId": 3,
			"nextRowId": 2,
			"structure": [
				{
					"columns": [
					{
						"columnId": "0",
						"fragmentEntryLinkIds": [
							"37212",
							"37213"
						],
						"size": ""
					}
					],
					"rowId": "0"
				},
				{
					"columns": [
						{
							"columnId": "1",
							"fragmentEntryLinkIds": ["37218"],
							"size": "8"
						},
						{
							"columnId": "2",
							"fragmentEntryLinkIds": ["37215"],
							"size": "4"
						}
					],
					"config": {
						"columnSpacing": true,
						"paddingHorizontal": "3",
						"paddingVertical": "3"
					},
					"rowId": "1"
				}
			]
		}
	}, {
		segmentsExperienceId: 'segmentsExperienceId2',
		layoutData: {
			"nextColumnId": 3,
			"nextRowId": 2,
			"structure": [
				{
					"columns": [
					{
						"columnId": "0",
						"fragmentEntryLinkIds": [
							"37212",
							"37213"
						],
						"size": ""
					}
					],
					"rowId": "0"
				},
				{
					"columns": [
						{
							"columnId": "1",
							"fragmentEntryLinkIds": ["37214"],
							"size": "8"
						},
						{
							"columnId": "2",
							"fragmentEntryLinkIds": ["37215"],
							"size": "4"
						}
					],
					"config": {
						"columnSpacing": true,
						"paddingHorizontal": "3",
						"paddingVertical": "3"
					},
					"rowId": "1"
				}
			]
		}
	}
]

describe('confirmFragmnetEntryLinkIdLayoutDataPersonalization ', () => {
	test('should confirm fragmentEntryLinkId presence in a LayoutData different than the one selected', () => {
		expect(
			confirmFragmentEntryLinkIdInLayoutDataPersonalization(
				LAYOUT_DATE_PERSONALIZATION,
				'37214',
				'segmentsExperienceId1'
			)
		).toBe(true);
		
		expect(
			confirmFragmentEntryLinkIdInLayoutDataPersonalization(
				LAYOUT_DATE_PERSONALIZATION,
				'37215',
				'segmentsExperienceId1'
			)
		).toBe(true);
	});

	test('should confirm fragmentEntryLinkId presence in a LayoutData', () => {
		expect(
			confirmFragmentEntryLinkIdInLayoutDataPersonalization(
				LAYOUT_DATE_PERSONALIZATION,
				'37214'
			)
		).toBe(true);
	});

	test('should confirm fragmentEntryLinkId ausence in a LayoutData different than the one selected', () => {	
		expect(
			confirmFragmentEntryLinkIdInLayoutDataPersonalization(
				LAYOUT_DATE_PERSONALIZATION,
				'37214',
				'segmentsExperienceId2'
			)
		).toBe(false);
	})
})