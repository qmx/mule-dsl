/*
 * ---------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.config.dsl;

/**
 * Interface that extends {@link PipelineBuilder} and just adds a
 * way to delimits the end of the first successful block.
 *
 * @author porcelli
 */
public interface FirstSuccessfulRouterBuilder<P extends PipelineBuilder<P>> extends PipelineBuilder<FirstSuccessfulRouterBuilder<P>> {

    /**
     * Delimits the current block and returns back to outer block.
     *
     * @return the parameterized builder
     */
    P endFirstSuccessful();
}