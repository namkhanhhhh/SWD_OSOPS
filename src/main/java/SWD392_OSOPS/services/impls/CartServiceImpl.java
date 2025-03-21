package SWD392_OSOPS.services.impls;

import SWD392_OSOPS.entities.Cart;
import SWD392_OSOPS.entities.CartItem;
import SWD392_OSOPS.entities.User;
import SWD392_OSOPS.repositories.CartItemRepository;
import SWD392_OSOPS.repositories.CartRepository;
import SWD392_OSOPS.repositories.UserRepository;
import SWD392_OSOPS.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Cart getCart(String userName) {
        int quantity=0;
        double total=0;
        User user = userRepository.findByUsername(userName);
        Cart cart = user.getCart();
        List<CartItem> ciList = cart.getItems();
        for (CartItem item : ciList) {
            if (item.getShoes().getStatus()) {
                item.setTotal(item.getTotalPrice());
                quantity += item.getQuantity();
                total += item.getTotal();
            }
        }
        cart.setQuantity(quantity);
        cart.setTotal(total);
        cartRepository.save(cart);
        return cart;
    }

    @Override
    public void clearCart(Cart cart) {
        cartItemRepository.deleteAll(cart.getItems());
        cart.setItems(new ArrayList<>());
        cart.setQuantity(0);
        cart.setTotal(0.0);
        cartRepository.save(cart);
    }


}
