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

package com.liferay.structured.content.apio.internal.odata;

import com.liferay.structured.content.apio.internal.search.FieldConstants;
import org.apache.olingo.commons.api.edm.Edm;
import org.apache.olingo.commons.api.edm.provider.CsdlEdmProvider;
import org.apache.olingo.commons.api.ex.ODataException;
import org.apache.olingo.commons.core.edm.EdmProviderImpl;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.uri.UriInfo;
import org.apache.olingo.server.core.uri.parser.Parser;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * Utility for parsing OData queries. It uses a model to create a {@link
 * UriInfo}.
 *
 * @author Julio Camarero
 * @review
 */
@Component(immediate = true, service = ODataQueryParser.class)
public class ODataQueryParserImpl implements ODataQueryParser {

	@Override
	public CsdlEdmProvider getCsdlEdmProvider() {
		return new StructuredContentEdmProvider();
	}

	@Override
	public UriInfo parse(String query) throws ODataException {
		return _parser.parseUri(FieldConstants.ENTITY_NAME, query, null, null);
	}

	@Activate
	protected void activate() {
		CsdlEdmProvider csdlEdmProvider = getCsdlEdmProvider();

		Edm edm = new EdmProviderImpl(csdlEdmProvider);

		OData oData = OData.newInstance();

		_parser = new Parser(edm, oData);
	}

	private Parser _parser;

}