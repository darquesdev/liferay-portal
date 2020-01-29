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

package com.liferay.petra.sql.dsl.query.impl;

import com.liferay.petra.sql.dsl.ast.ASTNodeListener;
import com.liferay.petra.sql.dsl.ast.impl.BaseASTNode;
import com.liferay.petra.sql.dsl.expressions.Expression;
import com.liferay.petra.sql.dsl.query.GroupByStep;
import com.liferay.petra.sql.dsl.query.HavingStep;

import java.util.function.Consumer;

/**
 * @author Preston Crary
 */
public class GroupBy extends BaseASTNode implements HavingStep {

	public GroupBy(GroupByStep groupByStep, Expression<?>... expressions) {
		super(groupByStep);

		if (expressions.length == 0) {
			throw new IllegalArgumentException();
		}

		_expressions = expressions;
	}

	public Expression<?>[] getExpressions() {
		return _expressions;
	}

	@Override
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		consumer.accept("group by ");

		for (int i = 0; i < _expressions.length; i++) {
			_expressions[i].toSQL(consumer, astNodeListener);

			if (i < (_expressions.length - 1)) {
				consumer.accept(", ");
			}
		}
	}

	private final Expression<?>[] _expressions;

}