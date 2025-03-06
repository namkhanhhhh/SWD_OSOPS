package SWD392_OSOPS.controllers;

import SWD392_OSOPS.entities.Cart;
import SWD392_OSOPS.entities.Shoes;
import SWD392_OSOPS.exceptions.FileNotFoundException;
import SWD392_OSOPS.services.CartItemService;
import SWD392_OSOPS.services.CartService;
import SWD392_OSOPS.services.ShoesService;
import SWD392_OSOPS.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SingleProductController {
    @Autowired
    ShoesService shoesService;
    @Autowired
    CartItemService cartItemService;
    @Autowired
    CartService cartService;
    @Autowired
UserService userService;

    @GetMapping("/single-product")
    public String SingleProduct(@RequestParam("id") String idShoes, Model model) throws FileNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            String role = userService.findByUsername(authentication.getName()).getRoles().get(0).getRoleName();
            model.addAttribute("userRole", role);
            if ("ADMIN".equalsIgnoreCase(role) || "MANAGER".equalsIgnoreCase(role)) {
                throw new AccessDeniedException("You do not have permission to access this page");
            }
        }
        if(idShoes.isEmpty() || idShoes.equals("")) {
            throw new FileNotFoundException("Not Found");
        }
        int id = Integer.parseInt(idShoes);
        Shoes p = shoesService.getShoesByID(id);
        if(p!=null){
            model.addAttribute("product",p);
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                model.addAttribute("isLogin", false);
                model.addAttribute("check", false);
                return "single-product";
            }
            model.addAttribute("isLogin", true);
            model.addAttribute("username", authentication.getName());
            model.addAttribute("check", false);
            return "single-product";
        }
        model.addAttribute("check", true);
        return "single-product";


    }


    @PostMapping("/cart-single/shoes/{id}")
    public String addPhoneQuantityToCart(@PathVariable("id") String id, Model model, @RequestParam("quantity") int quantity) throws FileNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(id.isEmpty() || id==null)   throw new FileNotFoundException("Not Found");

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("isLogin", false);
            return "cart";
        }
        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());
        cartItemService.addPhoneSingleToCart(authentication.getName(),Integer.parseInt(id), quantity);
        Cart cart= cartService.getCart(authentication.getName());
        model.addAttribute("listPByC", cart.getItems());
        model.addAttribute("cartTotal", cart.getTotal());
        return "redirect:/shop";
    }
}
