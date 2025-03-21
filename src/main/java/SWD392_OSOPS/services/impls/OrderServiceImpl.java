package SWD392_OSOPS.services.impls;

import SWD392_OSOPS.entities.*;
import SWD392_OSOPS.repositories.OrderItemRepository;
import SWD392_OSOPS.repositories.OrderRepository;
import SWD392_OSOPS.repositories.UserRepository;
import SWD392_OSOPS.services.CartService;
import SWD392_OSOPS.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> ListOrderByUserId(int id) {
        return orderRepository.getOrderByUserId(id);
    }

    @Transactional
    public void placeOrder(String userName) {
        User user = userRepository.findByUsername(userName);
        Cart cart = user.getCart();
        if (cart == null || cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }
        Order order = new Order();
        order.setUser(user);
        order.setTotalPrice(cart.getTotal());
        order.setOrderDate(LocalDate.now());
        order.setStatus("Pending");

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setShoes(cartItem.getShoes());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotal(cartItem.getTotalPrice());
            orderItemRepository.save(orderItem);
        }
        cartService.clearCart(cart);
        orderRepository.save(order);
    }

    @Override
    public void cancelOrder(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order ID: " + orderId));
        if ("Pending".equals(order.getStatus())) {
            order.setStatus("Canceled");
            orderRepository.save(order);
        } else {
            throw new IllegalStateException("Order cannot be canceled because its status is: " + order.getStatus());
        }
    }

    @Transactional
    public void updateOrderStatus(int id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order ID: " + id));
        if ("Canceled".equals(order.getStatus())) {
            throw new IllegalStateException("Order cannot be set any status because its status is: " + order.getStatus());

        } else {
            order.setStatus(status);
            orderRepository.save(order);        }
    }
}
