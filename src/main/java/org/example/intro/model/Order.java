package org.example.intro.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.intro.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
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
    private OrderStatus status = OrderStatus.PENDING;
    @Column(nullable = false)
    private BigDecimal total = BigDecimal.ZERO;
    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate = LocalDateTime.now();
    @Column(name = "shipping_address", nullable = false)
    private String shippingAddress;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();
}
