package org.ingi.grpc;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import org.ingi.OrderService;
import org.ingi.proto.*;
import org.ingi.repository.OrderRepository;
import org.ingi.repository.converters.OrderMessageConverter;

import javax.inject.Inject;

@GrpcService
public class OrderGrpcService extends MutinyOrderGrpc.OrderImplBase{
    OrderService orderService;
    public OrderGrpcService(OrderService orderService){
        this.orderService=orderService;
    }
    public Uni<CreateOrderResponseMessage> createOrder(CreateOrderRequestMessage request) {
        System.out.println(request);
        var order = OrderMessageConverter.toOrder(request.getOrder(), request.getCustomerId());
//        var o = this.orderService.create(order);
//        var createdOrder = CreateOrderResponseMessage.newBuilder()
//                .setOrderNumber()
//                .build();

//        System.out.println("2nd" + order.getCustomer());
        return this.orderService.create(order)
                .map(o -> {
                    var createdOrder = CreateOrderResponseMessage
                            .newBuilder()
                            .setOrderNumber(o.getOrderNumber()).build();
                    return createdOrder;
                });

    }
}
