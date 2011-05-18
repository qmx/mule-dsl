/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import org.mule.api.lifecycle.Callable;
import org.mule.config.dsl.expression.CoreExpr;

public interface PipelineBuilder<P extends PipelineBuilder<P>> {
	/* component */

	P log();

	P log(ErrorLevel level);

	P log(String message);

	P log(String message, ErrorLevel level);

	<E extends ExpressionEvaluatorBuilder> P log(E expr);

	<E extends ExpressionEvaluatorBuilder> P log(E expr, ErrorLevel level);

	P echo();

	P execute(Object obj);

	P execute(Callable obj);

	ExecutorBuilder execute(Class<?> clazz);

	/* outbound */
	EndPointBuilder.OutboundEndpointBuilder send(String uri);

	/* transform */

	<E extends ExpressionEvaluatorBuilder> P transform(E expr);

	<T> P transformTo(Class<T> clazz);

	/* filter */
	P filter(CoreExpr.GenericExpressionFilterEvaluatorBuilder expr);

	<E extends ExpressionEvaluatorBuilder> P filter(E expr);

	/* routers */

	AllRouterBuilder<P> all();

	RouterBuilder.ChoiceRouterBuilder choice();

}