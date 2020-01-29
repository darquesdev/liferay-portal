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

package com.liferay.petra.sql.dsl.expressions.impl;

import com.liferay.petra.sql.dsl.ast.ASTNodeListener;
import com.liferay.petra.sql.dsl.ast.impl.BaseASTNode;
import com.liferay.petra.sql.dsl.expressions.Expression;
import com.liferay.petra.sql.dsl.expressions.Predicate;
import com.liferay.petra.sql.dsl.expressions.WhenThenStep;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Preston Crary
 */
public class CaseWhenThen<T> extends BaseASTNode implements WhenThenStep<T> {

	public CaseWhenThen(Predicate predicate, Expression<T> thenExpression) {
		_predicate = Objects.requireNonNull(predicate);
		_thenExpression = Objects.requireNonNull(thenExpression);
	}

	public Predicate getPredicate() {
		return _predicate;
	}

	public Expression<T> getThenExpression() {
		return _thenExpression;
	}

	@Override
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		consumer.accept("case when ");

		_predicate.toSQL(consumer, astNodeListener);

		consumer.accept(" then ");

		_thenExpression.toSQL(consumer, astNodeListener);
	}

	private final Predicate _predicate;
	private final Expression<T> _thenExpression;

}