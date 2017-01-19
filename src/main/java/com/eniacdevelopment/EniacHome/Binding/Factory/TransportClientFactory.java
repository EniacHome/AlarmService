package com.eniacdevelopment.EniacHome.Binding.Factory;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.glassfish.hk2.api.Factory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.InetSocketAddress;
import java.util.Properties;

/**
 * Created by larsg on 12/7/2016.
 */
public class TransportClientFactory implements Factory<TransportClient> {
    private final static String TRANSPORTCLIENTS = "ELASTIC_CLIENTS";
    private final Properties properties;
    private TransportClient transportClient;

    @Inject
    public TransportClientFactory(Properties properties) {
        this.properties = properties;
    }

    @Override
    @Singleton
    public TransportClient provide() {
        this.transportClient = TransportClient.builder().build()
                .addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("localhost", 9300)));

        String[] transportClients = ((String) this.properties.get(TRANSPORTCLIENTS)).split(";");

        for (String client : transportClients) {
            String[] values = client.split(":");
            this.transportClient.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(values[0], Integer.parseInt(values[1]))));
        }

        return this.transportClient;
    }

    @Override
    public void dispose(TransportClient transportClient) {
        this.transportClient.close();
    }
}
