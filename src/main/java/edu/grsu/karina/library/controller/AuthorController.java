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
    public String requestList(Model model, @AuthenticationPrincipal User req){
        boolean isAd = req.getRoles().contains(Role.ADMIN);
        model.addAttribute("isAd", isAd);
        model.addAttribute("authors",authorRepository.findAll());
        return "authors";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/author/{id}")
    public String edit(@PathVariable int id, Model model) {
        Author author = authorRepository.findById(id).get();
        model.addAttribute("author",author );
        return "authorupdate";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/author/update")
    public String authorSave(@ModelAttribute Author req/*, Map<String,String> form*/) {
        Author author = authorRepository.findById(req.getId()).get();
        author.setFirstName(req.getFirstName());
        author.setSecondName(req.getSecondName());
        authorRepository.save(author);
        return "success";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/author/delete")
    public String delete(@RequestParam("id") int id) {
        authorRepository.deleteById(id);
        return "redirect:/authors";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/author/create")
    public String authorCreate( Model model) {
return "authorcreate";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/author")
    public String create(@ModelAttribute AuthorRequest req, Map<String,Object> model) {
        Author author = new Author();

        if(req.getSecondName()==null||req.getFirstName()==null){
            model.put( "message", "User exists!");
            return "authorcreate";
        }
        author.setSecondName(req.getSecondName());
        author.setFirstName(req.getFirstName());
        authorRepository.save(author);

        return "success";
    }

}
