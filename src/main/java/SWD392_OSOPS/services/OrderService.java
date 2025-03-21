package SWD392_OSOPS.services;

import SWD392_OSOPS.entities.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrder();
    List<Order> ListOrderByUserId(int id);
    void placeOrder(String userName);
    void cancelOrder(int orderId);
    void updateOrderStatus(int id, String status);
}
