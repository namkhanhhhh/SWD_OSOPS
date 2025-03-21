package SWD392_OSOPS.controllers;

import SWD392_OSOPS.entities.Cart;
import SWD392_OSOPS.services.CartItemService;
import SWD392_OSOPS.services.CartService;
import SWD392_OSOPS.services.ShoesService;
import SWD392_OSOPS.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


    @Controller
    public class CartItemController {

        @Autowired
        ShoesService shoesService;
        @Autowired
        CartItemService cartItemService;
        @Autowired
        CartService cartService;
        @Autowired
        UserService userService;

            @GetMapping("/cart/shoes/{id}")
        public String addShoesToCart(@PathVariable("id") int id, Model model){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {model.addAttribute("isLogin", false);
                return "cart";
            }
            model.addAttribute("isLogin", true);
            model.addAttribute("username", authentication.getName());
            cartItemService.addShoesToCart(authentication.getName(),id);
            Cart cart= cartService.getCart(authentication.getName());
            model.addAttribute("listPByC", cart.getItems());
            model.addAttribute("cartTotal", cart.getTotal());
                return "redirect:/shop";
        }

    }
