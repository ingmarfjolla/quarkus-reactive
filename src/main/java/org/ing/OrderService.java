package org.ing;
import io.debezium.outbox.reactive.quarkus.internal.DebeziumOutboxHandler;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.ing.cdc.OrderCreatedEvent;
import org.ing.repository.OrderRepository;
import org.ing.repository.models.Order;
import java.util.UUID;



@ApplicationScoped
public class OrderService {
    @Inject
    DebeziumOutboxHandler handler;

    OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository=orderRepository;
    }

    @WithTransaction
    public Uni<Order> create(Order order){
        Log.info(Thread.currentThread().getName());
        return this.orderRepository.persistAndFlush(order)
                .call(() -> handler.persistToOutbox(OrderCreatedEvent.of(order)));
    }



    @WithTransaction
    public Uni<Integer> cancel(String id){
        return this.orderRepository.update("status=4 where id=?1",UUID.fromString(id));
    }

}
