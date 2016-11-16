
package com.eniacdevelopment.EniacHome.Serial;

//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectWriter;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.client.transport.TransportClient;
//
///**
// * Created by larsg on 9/28/2016.
// */
//public class ESPacketListenerObserver extends PacketListenerObserver {
//    private final ObjectWriter objectWriter;
//    private final TransportClient transportClient;
//
//    public ESPacketListenerObserver(TransportClient transportClient, ObjectWriter objectWriter) {
//        this.transportClient = transportClient;
//        this.objectWriter = objectWriter;
//    }
//
//    @Override
//    public void eventNotify(SerialNotification serialNotification) {
//        byte[] jsonSerialNotification;
//        try {
//            jsonSerialNotification = objectWriter.writeValueAsBytes(serialNotification);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//            return;
//        }
//
//        IndexRequest indexRequest = new IndexRequest("test", serialNotification.getClass().getTypeName())
//                .source(jsonSerialNotification)
//                .refresh(true);
//
//         this.transportClient.index(indexRequest);
//    }
//}
