package io.cockroachdb.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cockroachdb.demo.model.PurchaseOrderEvent;

@Service
public class InboxChangeFeedListener {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(id = "inbox-demo", topics = "orders-inbox",
            groupId = "orders-inbox",
            properties = {"spring.json.value.default.type=io.cockroachdb.demo.model.PurchaseOrderEvent"})
    public void onPurchaseOrderEvent(PurchaseOrderEvent event)
            throws JsonProcessingException {
        logger.info("Received inbox event: {}",
                objectMapper.writer(new DefaultPrettyPrinter())
                        .writeValueAsString(event));
    }
}
