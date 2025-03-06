package SWD392_OSOPS.services;
import SWD392_OSOPS.entities.Cart;

public interface CartService {
    Cart getCart(String userName);
    void clearCart(Cart cart);
}
