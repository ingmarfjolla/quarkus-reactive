package org.ing.repository.converters;

import jakarta.enterprise.context.ApplicationScoped;
import org.ing.repository.models.Order;
import org.ing.proto.*;


import java.util.Random;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.stream.Collectors;

/**
 * Converter for orders grpc models -from GRPC to domain model
 */
@ApplicationScoped
public class OrderMessageConverter {

    private final static Random rand = new Random();

    private OrderMessageConverter() {
    }

    /**
     * Converts a order domain model to a grpc CreateOrderResponse
     *
     * @param source the order domain model
     * @return the CreateOrderResponse grpc model
     */
    public static CreateOrderResponseMessage toCreateOrderResponseMessage(Order source) {
        if (source == null) {
            return null;
        }
        var orderMessageBuilder = CreateOrderResponseMessage.newBuilder()
                .setGenericOrderResponse(source.getOrderNumber());
        return orderMessageBuilder.build();
    }

    /**
     * Converts a OrderRequestMessage grpc model to a order domain model
     *
     * @param source the CreateOrderRequest grpc model
     * @return the order domains model
     */
    public static Order toOrder(OrderRequestMessage source, String customerId) {
        if (source == null) {
            return null;
        }

        var orderDataModel = new Order()
                .setCustomer(source.getCustomer())
                .setCreationTime(OffsetDateTime.now(ZoneOffset.UTC))
                .setCost(source.getCost())
                .setStatus(source.getStatus().getNumber())
                .setCustomerId(customerId)
                .setOrderNumber(String.valueOf(rand.nextInt(100000)));

        var orderItemDataModels = source.getItemsList().stream()
                .map(x -> OrderItemMessageConverter.toOrderItem(x, orderDataModel))
                .collect(Collectors.toList());
        orderDataModel.setOrderItems(orderItemDataModels);
        System.out.println(Thread.currentThread().getName()) ;
        return orderDataModel;
    }

    /**
     * Converts a order model to a Order grpc model
     *
     * @param source the order model
     * @return the order grpc message
     */
    public static OrderResponseMessage toOrderResponseMessage(Order source) {
        if (source == null) {
            return null;
        }

        var orderMessageBuilder = OrderResponseMessage.newBuilder()
                .setCustomer(source.getCustomer());
//                .setCost(source.getCost())
//                .setOrderNumber(source.getOrderNumber())
//                .setStatus(OrderStatusEnumConverter.toOrderStatusEnum(source.getStatus()))
//                .setCreationTime(convertToTimeStamp(source.getCreationTime()));

//        source.getItems().stream()
//                .map(OrderItemMessageConverter::toOrderItemMessage)
//                .collect(Collectors.toList())
//                .forEach(orderMessageBuilder::addItems);

        return orderMessageBuilder.build();
    }
}
