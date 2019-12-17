package edu.grsu.karina.library.controller;

import edu.grsu.karina.library.model.Author;
import edu.grsu.karina.library.model.Role;
import edu.grsu.karina.library.model.User;
import edu.grsu.karina.library.payload.AuthorRequest;
import edu.grsu.karina.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class AuthorController {
    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping("/authors")
    public String requestList(Model model, @AuthenticationPrincipal User req) {
        boolean isAd = req.getRoles().contains(Role.ADMIN);
        model.addAttribute("isEmpl", req.getRoles().contains(Role.EMPLOYEE));
        model.addAttribute("isAd", isAd);
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("isUs",User.isUser(req));
        return "authors";
    }

    @PreAuthorize("hasAuthority('EMPLOYEE')")
    @GetMapping("/author/{id}")
    public String edit(@PathVariable int id, Model model, @AuthenticationPrincipal User user) {
        Author author = authorRepository.findById(id).get();
        model.addAttribute("isAd", user.getRoles().contains(Role.ADMIN));
        model.addAttribute("author", author);
        return "authorupdate";
    }

    @PreAuthorize("hasAuthority('EMPLOYEE')")
    @PostMapping("/author/update")
    public String authorSave(@ModelAttribute Author req, @AuthenticationPrincipal User user, Model model) {
        Author author = authorRepository.findById(req.getId()).get();
        author.setFirstName(req.getFirstName());
        author.setSecondName(req.getSecondName());
        model.addAttribute("isAd", user.getRoles().contains(Role.ADMIN));
        authorRepository.save(author);
        return "success";
    }


    @PreAuthorize("hasAuthority('EMPLOYEE')")
    @PostMapping("/author/delete")
    public String delete(@RequestParam("id") int id) {
        authorRepository.deleteById(id);
        return "redirect:/authors";
    }

    @PreAuthorize("hasAuthority('EMPLOYEE')")
    @GetMapping("/author/create")
    public String authorCreate(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("isAd", user.getRoles().contains(Role.ADMIN));
        return "authorcreate";
    }

    @PreAuthorize("hasAuthority('EMPLOYEE')")
    @PostMapping("/author")
    public String create(@ModelAttribute AuthorRequest req, Model model, @AuthenticationPrincipal User user) {
        Author author = new Author();
        model.addAttribute("isAd", user.getRoles().contains(Role.ADMIN));
        if (req.getSecondName() == null || req.getFirstName() == null) {
            model.addAttribute("message", "User exists!");
            return "authorcreate";
        }
        author.setSecondName(req.getSecondName());
        author.setFirstName(req.getFirstName());
        //author.setDate(req.getDate());
        authorRepository.save(author);

        return "success";
    }

}
