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

import org.apache.olingo.commons.api.edm.provider.CsdlEdmProvider;
import org.apache.olingo.commons.api.ex.ODataException;
import org.apache.olingo.server.api.uri.UriInfo;

/**
 * Parses OData queries.
 *
 * @author Ruben Pulido
 */
public interface ODataQueryParser {

	public CsdlEdmProvider getCsdlEdmProvider();

	public UriInfo parse(String query) throws ODataException;

}