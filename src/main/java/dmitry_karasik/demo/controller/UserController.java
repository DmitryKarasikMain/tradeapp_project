package dmitry_karasik.demo.controller;

import dmitry_karasik.demo.dto.RegisterFormDTO;
import dmitry_karasik.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @GetMapping
    public String registerForm() {
        return "register";
    }

    @PostMapping
    public String register(RegisterFormDTO form, Model model) {
        var user = form.toEntity();
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        userService.save(user);
        return "redirect:/login";
    }
}
