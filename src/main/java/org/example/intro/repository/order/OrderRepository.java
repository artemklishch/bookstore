package org.example.intro.repository.order;

import java.util.List;
import java.util.Optional;
import org.example.intro.model.Order;
import org.example.intro.model.OrderItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = "orderItems.book")
    List<Order> findByUserId(Long userId, Pageable pageable);

    @Query("SELECT o.orderItems FROM Order o WHERE o.id= ?1 AND o.user.id= ?2")
    List<OrderItem> findOrderItems(Long orderId, Long userId);

    @Query("SELECT o FROM Order o WHERE o.id= ?1 AND o.user.id= ?2")
    Optional<Order> findOrder(Long orderId, Long userId);

    @Query("SELECT o FROM Order o WHERE o.id= ?1")
    Optional<Order> findOrder(Long orderId);
}
