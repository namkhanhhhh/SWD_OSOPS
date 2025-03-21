package SWD392_OSOPS.controllers;

import SWD392_OSOPS.exceptions.FileNotFoundException;
import SWD392_OSOPS.services.ShoesService;
import SWD392_OSOPS.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@CrossOrigin
public class MainController {
    @Autowired
    private UserService userService;

    @Autowired
    ShoesService shoesService;

    @GetMapping("/page/login")
    @CrossOrigin
    public String login() {
        return "login";
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    @CrossOrigin
    public String index(Model model) throws FileNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("isLogin", false);

            return "index";
        }
        String role = userService.findByUsername(authentication.getName()).getRoles().get(0).getRoleName();
        if (role.equalsIgnoreCase("ADMIN")) {
            model.addAttribute("isLogin", true);
            model.addAttribute("username", authentication.getName());
            return "redirect:/admin-dashboard";
        } else if (role.equalsIgnoreCase("MANAGER")) {
            model.addAttribute("isLogin", true);
            model.addAttribute("username", authentication.getName());
            return "redirect:/manager";
        } else {
            model.addAttribute("listPhone", shoesService.getbestsale());
            model.addAttribute("isLogin", true);
            model.addAttribute("username", authentication.getName());
            model.addAttribute("userRole", role);
            return "index";
        }
    }
}
