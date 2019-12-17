package edu.grsu.karina.library.controller;

import edu.grsu.karina.library.model.Role;
import edu.grsu.karina.library.model.User;
import edu.grsu.karina.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.*;

@Controller

public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model) {//Map<String, Object> model
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {

model.addAttribute("message", "User exists!"); //("message", "User exists!");
            //model.put("message", "User exists!");
            return "registration";
        }
        if (user.getUsername() == "" || user.getPassword() == "") {
            //model.put("message", "Username and password are required");
            return "registration";
        }


        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        //user.setRoles(new HashSet<Role>(Arrays.asList(Role.values())));
        //user.setRoles(user.getRoles());

        userRepository.save(user);
        return "redirect:/login";
    }
}
