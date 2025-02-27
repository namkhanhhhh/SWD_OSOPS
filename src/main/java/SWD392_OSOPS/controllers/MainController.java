package SWD392_OSOPS.controllers;

import SWD392_OSOPS.services.ShoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import SWD392_OSOPS.dtos.UserDto;
import SWD392_OSOPS.entities.User;
import SWD392_OSOPS.exceptions.FileNotFoundException;
import SWD392_OSOPS.services.ShoesService;
import SWD392_OSOPS.services.RoleService;
import SWD392_OSOPS.services.UserService;

@Controller
@CrossOrigin
public class MainController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

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
           // model.addAttribute("listPhone", shoesService.getbestsale());
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

    @PostMapping("/signin-facebook")
    public ResponseEntity<?> facebookLogin(@RequestBody OAuth2AuthenticationToken authentication) {
        try {
            OAuth2User oAuth2User = authentication.getPrincipal();
            String facebookId = oAuth2User.getAttribute("id");
            String username = oAuth2User.getAttribute("name");
            String email = oAuth2User.getAttribute("email");

            User user = userService.findByEmail(email);
            if (user == null) {
                UserDto userDto = new UserDto();
                userDto.setUsername(username);
                userDto.setEmail(email);
                user = userService.save(userDto);
            }

            return ResponseEntity.status(200).body("redirect:/");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @RequestMapping(value = "/manager-dashboard", method = RequestMethod.GET)
    public String managerDashBoard() {
        return "manager-dashboard";
    }


    @GetMapping("/about")
    public String about(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            String role = userService.findByUsername(authentication.getName()).getRoles().get(0).getRoleName();
            if ("ADMIN".equalsIgnoreCase(role) || "MANAGER".equalsIgnoreCase(role)) {
                throw new AccessDeniedException("You do not have permission to access this page");
            }
        }
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("isLogin", false);
            return "about";
        }
        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());
        return "about";
    }


    @GetMapping("/detail")
    public String detail(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("isLogin", false);
            return "detail";
        }
        model.addAttribute("isLogin", true);
        model.addAttribute("username", authentication.getName());
        return "detail";
    }
}
