package org.ing.grpc;
import com.google.protobuf.Empty;
import io.quarkus.grpc.GrpcService;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import org.ing.OrderService;
import org.ing.proto.*;
import org.ing.repository.converters.OrderMessageConverter;

@GrpcService
public class OrderGrpcService extends MutinyOrderGrpc.OrderImplBase{
    OrderService orderService;

    public OrderGrpcService(OrderService orderService){
        this.orderService=orderService;
    }
    public Uni<CreateOrderResponseMessage> createOrder(CreateOrderRequestMessage request) {
        Log.info(Thread.currentThread().getName());
        return this.orderService.create(OrderMessageConverter.toOrder(request.getOrder(), request.getCustomerId()))
                .map(o -> {
                    Log.info(Thread.currentThread().getName());
                    var createdOrder = CreateOrderResponseMessage
                            .newBuilder()
                            .setGenericOrderResponse("thanks for your order "+ o.getCustomer()+ " " +o.getId() + " " +o.getOrderNumber()).build();
                    return createdOrder;
                });


    }

    public Uni<Empty> cancelOrder(DeleteOrderRequestMessage request){
        Log.info(Thread.currentThread().getName());
        return this.orderService.cancel(request.getOrderId()).replaceWith(Empty.newBuilder().build())
                .invoke(() -> Log.info("I have deleted order number" + request.getOrderId() + "on thread " + Thread.currentThread().getName()));

    }
}
