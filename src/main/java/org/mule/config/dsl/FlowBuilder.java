/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import org.mule.api.MuleContext;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.lifecycle.Callable;
import org.mule.api.source.MessageSource;
import org.mule.config.dsl.EndPointBuilder.OutboundEndpointBuilder;
import org.mule.config.dsl.RouterBuilder.ChoiceRouterBuilder;
import org.mule.config.dsl.expression.CoreExpr.GenericExpressionFilterEvaluatorBuilder;
import org.mule.config.dsl.internal.EndpointBuilderImpl;
import org.mule.construct.SimpleFlowConstruct;

import com.google.inject.Injector;

public class FlowBuilder implements PipelineBuilder<FlowBuilder> {

	@Override
	public FlowBuilder log() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FlowBuilder log(ErrorLevel level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FlowBuilder log(String message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FlowBuilder log(String message, ErrorLevel level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends ExpressionEvaluatorBuilder> FlowBuilder log(E expr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends ExpressionEvaluatorBuilder> FlowBuilder log(E expr, ErrorLevel level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FlowBuilder echo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FlowBuilder execute(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FlowBuilder execute(Callable obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExecutorBuilder execute(Class<?> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutboundEndpointBuilder send(String uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends ExpressionEvaluatorBuilder> FlowBuilder transform(E expr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> FlowBuilder transformTo(Class<T> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FlowBuilder filter(GenericExpressionFilterEvaluatorBuilder expr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends ExpressionEvaluatorBuilder> FlowBuilder filter(E expr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AllRouterBuilder<FlowBuilder> all() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChoiceRouterBuilder choice() {
		// TODO Auto-generated method stub
		return null;
	}

    private final SimpleFlowConstruct flow;
    private EndpointBuilderImpl.InboundEndpointBuilderImpl inboundEndpointBuilder;

    public FlowBuilder(String name, MuleContext muleContext) {
//        super(muleContext, null);
        this.flow = new SimpleFlowConstruct(name, muleContext);
    }

    public EndPointBuilder.InboundEndpointBuilder from(String uri) {
        this.inboundEndpointBuilder = new EndpointBuilderImpl.InboundEndpointBuilderImpl(this, this.flow.getMuleContext(), uri);
        return inboundEndpointBuilder;
    }
    
    FlowConstruct build(Injector injector) {
        if (inboundEndpointBuilder != null) {
            flow.setMessageSource((MessageSource) inboundEndpointBuilder.build(injector));
        }
        if (!isProcessorListEmpty()) {
            flow.setMessageProcessors(buildProcessorList(injector));
        }
        return flow;
    }

}
