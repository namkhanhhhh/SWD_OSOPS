package SWD392_OSOPS.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import SWD392_OSOPS.entities.Cart;
import SWD392_OSOPS.entities.CartItem;
import SWD392_OSOPS.entities.Shoes;
import SWD392_OSOPS.entities.User;
import SWD392_OSOPS.repositories.CartItemRepository;
import SWD392_OSOPS.repositories.CartRepository;
import SWD392_OSOPS.repositories.ShoesRepository;
import SWD392_OSOPS.repositories.UserRepository;
import SWD392_OSOPS.services.CartItemService;

import java.util.List;


@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ShoesRepository shoesRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public void removeShoesFromCart(String userName, int cartId, int shoesId) {
        int quantity=0;
        double total=0;
        User user = userRepository.findByUsername(userName);
        Cart cart = user.getCart();
        List<CartItem> ciList = cart.getItems();
        cartItemRepository.deleteShoesFromCart(cartId, shoesId);
        cartRepository.save(cart);
    }

    @Override
    public void addShoesToCart(String userName, int shoesId) {
        int quantity=0;
        double total = 0;
        Shoes upd = shoesRepository.findById(shoesId).orElse(null);
        if (upd == null||!upd.getStatus()) {
            return;
        }
        User user = userRepository.findByUsername(userName);
        Cart cart = user.getCart();
        List<CartItem> ciList = cart.getItems();
        boolean found = false;
        for (CartItem item : ciList) {
            if (item.getShoes().getShoesId() == upd.getShoesId()) {
                item.setQuantity(item.getQuantity() + 1);
                found = true;
                break;
            }
        }
        if (!found) {
            ciList.add(CartItem.builder().shoes(upd).cart(cart).quantity(1).build());
        }
        cartRepository.save(cart);
    }

    @Override
    public void updateShoesQuantity(String userName, int cartId, int shoesId, int quantity) {
        int quantityCart=0;
        double total=0;
        CartItem cartItem=cartItemRepository.listCartItemByPAC(cartId,shoesId);
        User user = userRepository.findByUsername(userName);
        Cart cart = user.getCart();
        List<CartItem> ciList = cart.getItems();
        if (cartItem!=null&&cartItem.getShoes().getStatus()){
            cartItem.setQuantity(quantity);
            cartItem.setTotal(cartItem.getTotalPrice());
            cartRepository.save(cart);
        }
    }

    @Override
    public void addShoesSingleToCart(String userName, int shoesId, int quantity) {
            Shoes shoes = shoesRepository.findById(shoesId).orElse(null);
            User user = userRepository.findByUsername(userName);
            Cart cart = user.getCart();
            List<CartItem> cartItems = cart.getItems();


            boolean shoesExistsInCart = false;
            for (CartItem item : cartItems) {
                if (item.getShoes().getShoesId() == shoes.getShoesId()) {
                    item.setQuantity(item.getQuantity() + quantity);
                    cartItemRepository.save(item); // Update existing cart item
                    shoesExistsInCart = true;
                    break;
                }
            }


            if (!shoesExistsInCart) {
                CartItem newItem = CartItem.builder()
                        .shoes(shoes)
                        .cart(cart)
                        .quantity(quantity)
                        .total(quantity*shoes.getPrice())
                        .build();
                cartItems.add(newItem);
                cartItemRepository.save(newItem);
            }
            int totalQuantity = cartItems.stream().mapToInt(CartItem::getQuantity).sum();
            double totalPrice = cartItems.stream().mapToDouble(item -> item.getQuantity() * item.getShoes().getPrice()).sum();
            cart.setQuantity(totalQuantity);
            cart.setTotal(totalPrice);
            cartRepository.save(cart); // Save updated cart
        }
}
