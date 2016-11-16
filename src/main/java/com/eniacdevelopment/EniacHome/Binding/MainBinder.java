package com.eniacdevelopment.EniacHome.Binding;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by larsg on 11/16/2016.
 */
public class MainBinder extends AbstractBinder {
        @Override
        protected void configure() {
            bind(50).to(Integer.class);
            TransportClient transportClient = null;
            try {
                transportClient = TransportClient.builder().build()
                        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            bind(transportClient).to(TransportClient.class);
        }
}
