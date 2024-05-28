package org.ing.repository.models;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(
        //needs to be customer order not just order, order is a reserved keyword.
        name = "customer_order",
        uniqueConstraints = @UniqueConstraint(columnNames = {"customer_id", "order_number"})
)
public class Order {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "order_id", nullable = false)
    private UUID id;
    @Column(name = "order_number", nullable = false)
    private String orderNumber;
    @Column(name = "customer_id", nullable = false)
    private String customerId;
    @Column(name = "customer", nullable = false)
    private String customer;
    @Column(nullable = false)
    private int status;
    @Column(name = "cost")
    private int cost;
    @Column(name = "creation_time")
    private OffsetDateTime creationTime;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    public Order() {
        orderItems = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public Order setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public Order setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Order setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getCustomer() {
        return customer;
    }

    public Order setCustomer(String customer) {
        this.customer = customer;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public Order setStatus(int status) {
        this.status = status;
        return this;
    }

    public int getCost() {
        return cost;
    }

    public Order setCost(int cost) {
        this.cost = cost;
        return this;
    }

    public OffsetDateTime getCreationTime() {
        return creationTime;
    }

    public Order setCreationTime(OffsetDateTime creationTime) {
        this.creationTime = creationTime;
        return this;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public Order setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
        return this;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, orderNumber, customerId, customer, status, cost, creationTime, orderItems);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order that = (Order) o;
        return status == that.status
                && Objects.deepEquals(id, that.id)
                && Objects.deepEquals(orderNumber, that.orderNumber)
                && Objects.deepEquals(customerId, that.customerId)
                && Objects.deepEquals(customer, that.customer)
                && Objects.deepEquals(cost, that.cost)
                && Objects.deepEquals(creationTime, that.creationTime)
                && Objects.deepEquals(orderItems, that.orderItems);
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", orderNumber='" + orderNumber + '\'' + ", customerId='" + customerId + '\'' + ", customer='" + customer + '\'' + ", status=" + status + ", cost=" + cost + ", creationTime=" + creationTime + ", orderItems=" + orderItems + '}';
        }

}
