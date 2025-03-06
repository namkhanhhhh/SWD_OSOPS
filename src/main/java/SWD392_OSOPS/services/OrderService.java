package SWD392_OSOPS.services;

import SWD392_OSOPS.entities.Order;
import SWD392_OSOPS.exceptions.FileNotFoundException;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface OrderService {
    List<Order> getAllOrder();
    List<Order> ListOrderByUserId(int id);
    Order getOrder(int oId) throws FileNotFoundException;
    void placeOrder(String userName);
    void cancelOrder(int orderId);
    List<Order> searchOrderByUserName(String name);
    void updateOrderStatus(int id, String status);
    int totalOrder();
    int totalOrderByDate(Date start, Date end);
    List<Order> findOrdersBetweenDates(LocalDate startDate,LocalDate endDate);
}
