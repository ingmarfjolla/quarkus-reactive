package org.ing.cdc;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.debezium.outbox.quarkus.ExportedEvent;
import org.ing.repository.models.Order;
import org.ing.repository.models.OrderItem;
import org.jboss.logging.Logger;
import java.time.Instant;

public class OrderCreatedEvent implements ExportedEvent {

    private static ObjectMapper mapper = new ObjectMapper();

    private final String id;
    private final JsonNode order;
    private final Instant timestamp;

    private OrderCreatedEvent(String id, JsonNode order) {
        this.id = id;
        this.order = order;
        this.timestamp = Instant.now();
    }

    @Override
    public String getAggregateId() {
        return String.valueOf(id);
    }

    @Override
    public String getAggregateType() {
        return "Order";
    }

    @Override
    public JsonNode getPayload() {
        return order;
    }

    @Override
    public String getType() {
        return "OrderCreated";
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public static OrderCreatedEvent of(Order order) {
        Logger.getLogger(OrderCreatedEvent.class).infof("Creating OrderCreatedEvent for Order: %s", order);

        ObjectNode asJson = mapper.createObjectNode()
                .put("orderNumber", order.getOrderNumber())
                .put("customer", order.getCustomer())
                .put("customerId", order.getCustomerId());

        ArrayNode items = asJson.putArray("items");

        for (OrderItem orderLine : order.getOrderItems()) {
            ObjectNode lineAsJon = mapper.createObjectNode()
//                    .put("id", orderLine.getId().toString())
                    .put("skuId", orderLine.getSkuId())
                    .put("type", orderLine.getType())
                    .put("name", orderLine.getName())
                    .put("quantity", orderLine.getQuantity());
            items.add(lineAsJon);
        }

        return new OrderCreatedEvent(order.getOrderNumber(), asJson);
    }
}