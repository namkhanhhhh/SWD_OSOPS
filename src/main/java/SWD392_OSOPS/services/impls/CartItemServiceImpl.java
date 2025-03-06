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
    private ShoesRepository phoneRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public void removePhoneFromCart(String userName,int cartId, int phoneId) {
        int quantity=0;
        double total=0;
        User user = userRepository.findByUsername(userName);
        Cart cart = user.getCart();
        List<CartItem> ciList = cart.getItems();
        cartItemRepository.deletePhoneFromCart(cartId, phoneId);
        cartRepository.save(cart);
    }

    @Override
    public void addPhoneToCart(String userName, int phoneId) {
        int quantity=0;
        double total = 0;
        Shoes upd = phoneRepository.findById(phoneId).orElse(null);
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
    public void updatePhoneQuantity(String userName,int cartId, int phoneId, int quantity) {
        int quantityCart=0;
        double total=0;
        CartItem cartItem=cartItemRepository.listCartItemByPAC(cartId,phoneId);
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
    public void addPhoneSingleToCart(String userName, int phoneId, int quantity) {
            Shoes shoes = phoneRepository.findById(phoneId).orElse(null);
            User user = userRepository.findByUsername(userName);
            Cart cart = user.getCart();
            List<CartItem> cartItems = cart.getItems();

            // Check if the phone already exists in the cart
            boolean phoneExistsInCart = false;
            for (CartItem item : cartItems) {
                if (item.getShoes().getShoesId() == shoes.getShoesId()) {
                    item.setQuantity(item.getQuantity() + quantity);
                    cartItemRepository.save(item); // Update existing cart item
                    phoneExistsInCart = true;
                    break;
                }
            }

            // If the phone is not already in the cart, add it as a new cart item
            if (!phoneExistsInCart) {
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
