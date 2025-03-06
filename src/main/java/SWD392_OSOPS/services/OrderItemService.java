package SWD392_OSOPS.services;

import SWD392_OSOPS.entities.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> listOrderItemByOrderId(int oId);
}
