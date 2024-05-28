package org.ing.repository.converters;
import org.ing.repository.models.Order;
import org.ing.repository.models.OrderItem;
import org.ing.proto.*;

/**
 * Converter for Order items grpc models
 */
public class OrderItemMessageConverter {
    private OrderItemMessageConverter() {
    }

    /**
     * Converts a OrderItem grpc model to an OrderItem model
     *
     * @param source the OrderItem grpc model
     * @return the OrderItem model
     */
    public static OrderItem toOrderItem(OrderItemMessage source, Order order) {
        if (source == null) {
            return null;
        }
        return new OrderItem()
                .setOrder(order)
                .setQuantity(source.getQuantity())
                .setSkuId(source.getSkuId())
                .setName(source.getName())
                .setCost(source.getCost())
                .setType(source.getType());
    }

//    public static OrderItem toOrderItem(OrderItemMessage source, Order order) {
//        if (source == null) {
//            return null;
//        }
//        return toOrderItem(source).setOrder(order);
//    }

    /**
     * Converts a OrderItem domain model to an OrderItem grpc model
     *
     * @param source the OrderItem model
     * @return the OrderItem grpc model
     */
    public static OrderItemMessage toOrderItemMessage(OrderItem source) {
        if (source == null) {
            return null;
        }

        return OrderItemMessage.newBuilder()
                .setQuantity(source.getQuantity())
                .setSkuId(source.getSkuId())
                .setName(source.getName())
                .setCost(source.getCost())
                .setType(source.getType())
                .build();
    }
}
