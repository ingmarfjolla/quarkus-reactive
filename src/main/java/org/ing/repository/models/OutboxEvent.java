//package org.ingi.repository.models;
//
//import com.fasterxml.jackson.databind.JsonNode;
//
//import io.quarkus.hibernate.reactive.panache.PanacheEntity;
//import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
//import org.hibernate.annotations.GenericGenerator;
//
//import javax.persistence.*;
//import java.util.UUID;
//
//@Entity
//@Table(
//        name = "outbox"
//)
//public class OutboxEvent extends PanacheEntityBase {
//    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
//    @Column(name = "id", nullable = false)
//    private UUID id;
//
//    @Column(name = "aggregatetype", nullable = false)
//    private String aggregateType;
//
//    @Column(name = "aggregateid", nullable = false)
//    private String aggregateId;
//
//    @Column(name = "type", nullable = false)
//    private String type;
//
//    @Column(name = "payload", nullable = false)
//    private String payload;
//
//
//    public OutboxEvent() {
//    }
//
//    public OutboxEvent(String aggregateType, String aggregateId, String type, String payload) {
//        this.aggregateType = aggregateType;
//        this.aggregateId = aggregateId;
//        this.type = type;
//        this.payload = payload;
//    }
//
//}
