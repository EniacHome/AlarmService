package com.eniacdevelopment.EniacHome.Binding.Factory;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.glassfish.hk2.api.Factory;

import java.net.InetSocketAddress;

/**
 * Created by larsg on 12/7/2016.
 */
public class TransportClientFactory implements Factory<TransportClient> {
    private TransportClient transportClient;

    @Override
    public TransportClient provide() {
        this.transportClient = TransportClient.builder().build()
                .addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("localhost", 9300)));

        return this.transportClient;
    }

    @Override
    public void dispose(TransportClient transportClient) {
        this.transportClient.close();
    }
}
