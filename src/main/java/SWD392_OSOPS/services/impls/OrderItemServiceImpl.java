package SWD392_OSOPS.services.impls;

import SWD392_OSOPS.entities.OrderItem;
import SWD392_OSOPS.repositories.OrderItemRepository;
import SWD392_OSOPS.services.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public List<OrderItem> listOrderItemByOrderId(int oId) {
        return orderItemRepository.getOrderItemByOrderId(oId);
    }

}
