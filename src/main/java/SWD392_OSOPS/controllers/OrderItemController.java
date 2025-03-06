package SWD392_OSOPS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import SWD392_OSOPS.exceptions.FileNotFoundException;
import SWD392_OSOPS.services.OrderItemService;
import SWD392_OSOPS.services.OrderService;
import SWD392_OSOPS.services.UserService;

@Controller
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderService orderService;
    @Autowired
    UserService userService;

    @GetMapping("/orderDetail/{id}")
    public String detailOrder(@PathVariable("id") int id, Model model) throws FileNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("isLogin", false);
            return "detail";
        }
        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());
        model.addAttribute("listItemByO", orderItemService.listOrderItemByOrderId(id));
        model.addAttribute("orderByOrderId",orderService.getOrder(id));
        return "detail";
    }
}
