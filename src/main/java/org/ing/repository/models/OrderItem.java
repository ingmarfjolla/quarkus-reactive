package org.ing.repository.models;


import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JoinColumn(name = "order_item_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "sku_id", nullable = false)
    private String skuId;
    private int quantity;
    private String type;
    private String name;
    private int cost;

    /**
     * Gets the id
     *
     * @return the id
     */
    public UUID getId() {
        return id;
    }

    public String getSkuId() {
        return skuId;
    }

    public OrderItem setSkuId(String skuId) {
        this.skuId = skuId;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public OrderItem setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getType() {
        return type;
    }

    public OrderItem setType(String type) {
        this.type = type;
        return this;
    }

    public String getName() {
        return name;
    }

    public OrderItem setName(String name) {
        this.name = name;
        return this;
    }

    public int getCost() {
        return cost;
    }

    public OrderItem setCost(int cost) {
        this.cost = cost;
        return this;
    }

    public OrderItem setOrder(Order order) {
        this.order = order;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, skuId, quantity, type, name, cost);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem that = (OrderItem) o;
        return quantity == that.quantity
                && Objects.deepEquals(id, that.id)
                && Objects.deepEquals(skuId, that.skuId)
                && Objects.deepEquals(type, that.type)
                && Objects.deepEquals(cost, that.cost)
                && Objects.deepEquals(name, that.name)
                && ((order == null && that.order == null)
                || (order != null && that.order != null && Objects.deepEquals(order.getOrderNumber(), that.order.getOrderNumber())));
    }
}