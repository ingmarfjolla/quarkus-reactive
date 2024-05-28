package org.ing.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.ing.repository.models.Order;


@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {


    public Uni<Order> findByCustomerName(String name){
        return find("customer", name).firstResult();
    }

    public Uni<String> getLastOrderNumber(String customerId){
        return find("customerId","ORDER by creation_time DESC",customerId).firstResult()
                .onItem().ifNotNull().transform(o-> o.getOrderNumber())
                .onItem().ifNull().continueWith(() -> null);
    }
}
