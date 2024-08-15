package org.example.intro.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;
    @Column(nullable = false)
    private BigDecimal total = BigDecimal.ZERO;
    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate = LocalDateTime.now();
    @Column(name = "shipping_address", nullable = false)
    private String shippingAddress;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems;

    @Getter
    public enum Status {
        PENDING("PENDING"),
        DELIVERED("DELIVERED"),
        COMPLETED("COMPLETED");

        public final String status;

        Status(String status) {
            this.status = status;
        }
    }
}
