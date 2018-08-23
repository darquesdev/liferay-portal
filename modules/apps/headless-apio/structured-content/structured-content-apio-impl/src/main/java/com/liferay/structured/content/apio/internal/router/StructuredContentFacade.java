/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.structured.content.apio.internal.router;

import com.liferay.journal.service.JournalArticleService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.structured.content.apio.internal.search.JournalArticleSearchHelper;
import com.liferay.structured.content.apio.internal.search.QueryMapper;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ruben Pulido
 */
public class StructuredContentFacade {

	@Reference
	private JournalArticleSearchHelper _journalArticleSearchHelper;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private QueryMapper _queryMapper;

	private static final Log _log = LogFactoryUtil.getLog(
		StructuredContentFacade.class);

}
