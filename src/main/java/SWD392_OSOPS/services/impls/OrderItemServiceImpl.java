package SWD392_OSOPS.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import SWD392_OSOPS.entities.OrderItem;
import SWD392_OSOPS.repositories.OrderItemRepository;
import SWD392_OSOPS.repositories.OrderRepository;
import SWD392_OSOPS.services.OrderItemService;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public List<OrderItem> listOrderItemByOrderId(int oId) {
        return orderItemRepository.getOrderItemByOrderId(oId);
    }

}
