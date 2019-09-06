package edu.grsu.karina.library.controller;

import edu.grsu.karina.library.model.Role;
import edu.grsu.karina.library.model.User;
import edu.grsu.karina.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String userList(Model model){
        model.addAttribute("users",userRepository.findAll());
        return "userList";
    }

    @GetMapping("{id}")
    public String edit(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping("/edit")
    public String userSave(@ModelAttribute User req/*, Map<String,String> form*/) {
        User user = userRepository.findById(req.getId()).get();
        user.setUsername(req.getUsername());

        user.setRoles(req.getRoles());
        userRepository.save(user);
        return "success";
    }
    @PostMapping("/delete")
    public String delete(@RequestParam("id") long id) {
        userRepository.deleteById(id);
        return "redirect:/books";
    }
}
