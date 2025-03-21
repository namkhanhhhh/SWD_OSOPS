package SWD392_OSOPS.repositories;

import SWD392_OSOPS.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM cart_item WHERE cart_id = :cartId AND shoes_id = :shoesId", nativeQuery = true)
    void deleteShoesFromCart(@Param("cartId") int cartId, @Param("shoesId") int shoesId);

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.cartId = ?1 AND ci.shoes.shoesId = ?2")
    CartItem listCartItemByPAC(int cartId, int shoesId);

    @Query(value =  "select sum(total) from cart_item where cart_id = :cartId", nativeQuery = true)
    Double totalCart(@Param("cartId") int cartId);
}
