package com.eniacdevelopment.EniacHome.Binding.Factory;

import com.eniacdevelopment.EniacHome.Configuration.LocalConfiguration;
import com.eniacdevelopment.EniacHome.Configuration.TransportClientConfiguration;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.glassfish.hk2.api.Factory;

import javax.inject.Inject;
import java.net.InetSocketAddress;

/**
 * Created by larsg on 12/7/2016.
 */
public class TransportClientFactory implements Factory<TransportClient> {
    private final TransportClientConfiguration transportClientConfiguration;
    private TransportClient transportClient;

    @Inject
    public TransportClientFactory(LocalConfiguration localConfiguration){
        this.transportClientConfiguration = localConfiguration.transportClientConfiguration;
    }

    @Override
    public TransportClient provide() {
        this.transportClient = TransportClient.builder().build();

        for(InetSocketAddress transportAddress : this.transportClientConfiguration.transportAddresses) {
            this.transportClient.addTransportAddress(new InetSocketTransportAddress(transportAddress));
        }

        return this.transportClient;
    }

    @Override
    public void dispose(TransportClient transportClient) {
        this.transportClient.close();
    }
}
