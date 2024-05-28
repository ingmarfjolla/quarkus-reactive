package org.ing.repository.models;

import java.util.Arrays;

/**
 * The enumeration of order statuses
 */
public enum OrderStatus {
    UNKNOWN(0),
    ORDERED(1),
    COMPLETED(2);

    private final int id;

    OrderStatus(int id) {
        this.id = id;
    }

    /**
     * Gets a order status by it's id
     *
     * @param id the order status id
     * @return the order status
     */
    public static OrderStatus fromId(int id) {
        return Arrays.asList(OrderStatus.values()).stream()
                .filter(x -> x != OrderStatus.UNKNOWN && x.getid() == id)
                .findFirst()
                .orElse(OrderStatus.UNKNOWN);
    }

    /**
     * Get's the order status id
     *
     * @return the order status id
     */
    public int getid() {
        if (this == UNKNOWN) {
            throw new IllegalArgumentException(
                    "Can't get the number of an unknown enum value.");
        }

        return this.id;
    }
}
