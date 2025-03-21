package SWD392_OSOPS.controllers;

import SWD392_OSOPS.exceptions.FileNotFoundException;
import SWD392_OSOPS.services.OrderItemService;
import SWD392_OSOPS.services.OrderService;
import SWD392_OSOPS.services.ShoesService;
import SWD392_OSOPS.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ManagerController {


    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private UserService userService;
    @Autowired
    private ShoesService phoneService;

    @GetMapping("/manager")
    public String viewOrderList(Model model) throws FileNotFoundException {
        model.addAttribute("orderList", orderService.getAllOrder());
        if(phoneService.GetTotalRevenue() == null)  model.addAttribute("revenue","Unknow");
        else model.addAttribute("revenue", phoneService.GetTotalRevenue() );
        return "manager";
    }

    @GetMapping("/order-detail-manager/{id}")
    public String detailOrderManager(@PathVariable("id") int id, Model model) throws FileNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());
        model.addAttribute("listItemByO", orderItemService.listOrderItemByOrderId(id));
        model.addAttribute("userByOrderId", userService.findUserByOrderId(id));
            return "order-detail";
    }

    @GetMapping("/order-detail")
    public String orderDetail(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());
        return "order-detail";
    }
    @GetMapping("/approve/{id}")
    public String approveOrder(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        try {
            orderService.updateOrderStatus(id, "Approved");
            redirectAttributes.addFlashAttribute("message", "Order approved successfully");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/manager";
    }

    @GetMapping("/reject/{id}")
    public String rejectOrder(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        try {
            orderService.updateOrderStatus(id, "Rejected");
            redirectAttributes.addFlashAttribute("message", "Order rejected successfully");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/manager";
    }

    @GetMapping("/complete/{id}")
    public String completeOrder(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        orderService.updateOrderStatus(id, "Completed");
        redirectAttributes.addFlashAttribute("message", "Order completed successfully");
        return "redirect:/manager";
    }

    @GetMapping("/inCompleted/{id}")
    public String inCompletedOrder(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        orderService.updateOrderStatus(id, "InCompleted");
        redirectAttributes.addFlashAttribute("message", "Order Incomplete successfully");
        return "redirect:/manager";
    }

}
