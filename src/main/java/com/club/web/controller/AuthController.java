package com.club.web.controller;

import com.club.web.dto.RegistrationDto;
import com.club.web.models.UserEntity;
import com.club.web.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/login")
    public String loginpage(){
        return "login";
    }
    

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        model.addAttribute("user", new RegistrationDto());
        return "register";
    }

    @GetMapping("/save")
    public String getRegisterSave() {
        return "redirect:/register";
    }

    @PostMapping("/save")
    public String register(@Valid @ModelAttribute("user") RegistrationDto user,
                           BindingResult result, Model model) {
        // Check for existing email
        UserEntity existingUserEmail = userService.findByEmail(user.getEmail());
        if (existingUserEmail != null) {
            result.rejectValue("email", "error.user",
                    "There is already a user registered with this email");
        }

        // Check for existing username
        UserEntity existingUserName = userService.findByUserName(user.getUsername());
        if (existingUserName != null) {
            result.rejectValue("username", "error.user",
                    "There is already a user registered with this username");
        }

        if (result.hasErrors()) {
            return "register";
        }

        userService.saveUser(user);
        return "redirect:/clubs?success";
    }
}
