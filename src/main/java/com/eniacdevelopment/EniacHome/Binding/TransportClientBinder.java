package com.eniacdevelopment.EniacHome.Binding;

import com.eniacdevelopment.EniacHome.Binding.Factory.TransportClientFactory;
import org.elasticsearch.client.transport.TransportClient;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * Created by larsg on 11/17/2016.
 */
public class TransportClientBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bindFactory(TransportClientFactory.class).to(TransportClient.class);
    }
}
