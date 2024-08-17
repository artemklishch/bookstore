package org.example.intro.repository.cart;

import org.example.intro.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("SELECT c FROM CartItem c WHERE c.shoppingCart.id= ?1 AND c.book.id= ?2")
    CartItem findByCartIdAndBookId(Long cartId, Long bookId);

    @Query("SELECT c FROM CartItem c WHERE c.shoppingCart.id= ?1 AND c.id= ?2")
    CartItem findByIdAndByCartId(Long cartId, Long id);
}
