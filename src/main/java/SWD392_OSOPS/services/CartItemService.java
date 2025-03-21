package SWD392_OSOPS.services;

public interface CartItemService {
    void removeShoesFromCart(String userName, int cartId, int shoesId);
    void addShoesToCart(String userName, int shoesId);
    void updateShoesQuantity(String userName, int cartId, int shoesId, int quantity);
    void addShoesSingleToCart(String userName, int shoesId, int quantity);
}
