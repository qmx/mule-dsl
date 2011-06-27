/*
 * ---------------------------------------------------------------------------
 *  Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 */

package org.mule.config.dsl.module.mongo.internal;

import com.google.inject.Injector;
import org.mule.api.MuleContext;
import org.mule.config.dsl.ExpressionEvaluatorBuilder;
import org.mule.config.dsl.internal.Builder;
import org.mule.config.dsl.internal.util.PropertyPlaceholder;
import org.mule.config.dsl.module.mongo.ListIndicesMessageProcessorDefinition;
import org.mule.module.mongo.MongoCloudConnector;
import org.mule.module.mongo.config.ListIndicesMessageProcessor;

public class ListIndicesMessageProcessorBuilder implements ListIndicesMessageProcessorDefinition, Builder<ListIndicesMessageProcessor> {

    private final MongoCloudConnector object;

    private String collection = null;

    private ExpressionEvaluatorBuilder collectionExp = null;

    public ListIndicesMessageProcessorBuilder(MongoCloudConnector object, String collection) {
        this.object = object;
        this.collection = collection;
    }

    public ListIndicesMessageProcessorBuilder(MongoCloudConnector object, ExpressionEvaluatorBuilder collectionExp) {
        this.object = object;
        this.collectionExp = collectionExp;
    }

    @Override
    public ListIndicesMessageProcessor build(MuleContext muleContext, Injector injector, PropertyPlaceholder placeholder) {
        ListIndicesMessageProcessor mp = new ListIndicesMessageProcessor();

        mp.setObject(object);

        if (collectionExp != null) {
            mp.setCollection(collectionExp);
        } else {
            mp.setCollection(collection);
        }

        return mp;
    }
}
