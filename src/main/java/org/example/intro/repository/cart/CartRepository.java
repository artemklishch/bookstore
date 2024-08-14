package org.example.intro.repository.cart;

import java.util.Optional;
import org.example.intro.model.ShoppingCart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<ShoppingCart, Long> {
    @EntityGraph(attributePaths = "cartItems.book")
    Optional<ShoppingCart> findById(Long userId);
}
