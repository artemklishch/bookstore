package org.example.intro.repository.order;

import org.example.intro.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("SELECT i FROM OrderItem i WHERE i.order.id=?1 AND i.id=?2")
    OrderItem getOrderItem(Long orderId, Long itemId);
}
