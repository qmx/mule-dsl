/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

import org.junit.Test;
import org.mule.MessageExchangePattern;
import org.mule.api.MuleContext;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.source.MessageSource;
import org.mule.config.dsl.component.InvokerFlowComponent;
import org.mule.construct.SimpleFlowConstruct;

import java.util.Iterator;

import static org.fest.assertions.Assertions.assertThat;

public class TestExecuteFlow {

    @Test
    public void simpleExecuteFlow() {
        final MuleContext muleContext = Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("Receiver")
                        .from("file:///Users/porcelli/test")
                        .executeFlow("Dispatcher");

                flow("Dispatcher")
                        .send("file:///Users/porcelli/out");

            }
        }).advanced().muleContext();

        assertThat(muleContext.getRegistry().lookupFlowConstructs()).isNotEmpty().hasSize(2);
        Iterator<FlowConstruct> flowIterator = muleContext.getRegistry().lookupFlowConstructs().iterator();

        {
            final FlowConstruct flowConstruct = flowIterator.next();

            assertThat(flowConstruct.getName()).isEqualTo("Receiver");
            assertThat(flowConstruct).isInstanceOf(SimpleFlowConstruct.class);

            final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct).getMessageSource();

            assertThat(messageSource).isNotNull().isInstanceOf(InboundEndpoint.class);

            final InboundEndpoint inboundEndpoint = (InboundEndpoint) messageSource;

            assertThat(inboundEndpoint.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

            assertThat(inboundEndpoint.getProtocol()).isNotNull().isEqualTo("file");

            assertThat(inboundEndpoint.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/test");

            assertThat(((SimpleFlowConstruct) flowConstruct).getMessageProcessors()).isNotEmpty().hasSize(1);

            final MessageProcessor processor = ((SimpleFlowConstruct) flowConstruct).getMessageProcessors().iterator().next();

            assertThat(processor).isNotNull().isInstanceOf(InvokerFlowComponent.class);

            final InvokerFlowComponent invokeBuilder = (InvokerFlowComponent) processor;

            assertThat(invokeBuilder.getFlowName()).isNotNull().isEqualTo("Dispatcher");
        }

        {
            final FlowConstruct flowConstruct2 = flowIterator.next();

            assertThat(flowConstruct2.getName()).isEqualTo("Dispatcher");
            assertThat(flowConstruct2).isInstanceOf(SimpleFlowConstruct.class);

            final MessageSource messageSource = ((SimpleFlowConstruct) flowConstruct2).getMessageSource();

            assertThat(messageSource).isNull();

            final Iterator<MessageProcessor> iterator = ((SimpleFlowConstruct) flowConstruct2).getMessageProcessors().iterator();

            final MessageProcessor endpointProcessor1 = iterator.next();

            assertThat(endpointProcessor1).isNotNull().isInstanceOf(OutboundEndpoint.class);

            final OutboundEndpoint outboundEndpoint1 = (OutboundEndpoint) endpointProcessor1;

            assertThat(outboundEndpoint1.getExchangePattern()).isEqualTo(MessageExchangePattern.ONE_WAY);

            assertThat(outboundEndpoint1.getProtocol()).isNotNull().isEqualTo("file");

            assertThat(outboundEndpoint1.getAddress()).isNotNull().isEqualTo("file:///Users/porcelli/out");

        }
    }

    @Test(expected = RuntimeException.class)
    public void simpleExecuteFlowEmpty() {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("Receiver")
                        .from("file:///Users/porcelli/test")
                        .executeFlow("");
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void simpleExecuteFlowNull() {
        Mule.newInstance(new AbstractModule() {
            @Override
            public void configure() {
                flow("Receiver")
                        .from("file:///Users/porcelli/test")
                        .executeFlow(null);
            }
        });
    }
}