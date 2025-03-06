package SWD392_OSOPS.services;

public interface CartItemService {
    void removePhoneFromCart(String userName,int cartId, int phoneId);
    void addPhoneToCart(String userName, int phoneId);
    void updatePhoneQuantity(String userName,int cartId, int phoneId, int quantity);
    void addPhoneSingleToCart(String userName, int phoneId, int quantity);
//    void addCartItem(Cart cart, CartItem cartItem);
}
