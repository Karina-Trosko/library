package edu.grsu.karina.library.controller;

import edu.grsu.karina.library.model.Book;
import edu.grsu.karina.library.model.IssuanceRequest;
import edu.grsu.karina.library.model.User;
import edu.grsu.karina.library.payload.LoginRequest;
import edu.grsu.karina.library.repository.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.json.JSONArray;

import java.util.List;

@RestController
public class RestBookController {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    LiteratureTypeRepository literatureTypeRepository;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    IssuanceRequestRepository issuanceRequestRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/api/books")
    public String books() {
//
//        model.addAttribute("authors", authorRepository.findAll());
//        model.addAttribute("books", bookRepository.findAll());
//        model.addAttribute("literatureTypes", literatureTypeRepository.findAll());
//        model.addAttribute("genres", genreRepository.findAll());
        List<Book> books = bookRepository.findAll();
        for(int i =0; i< books.size(); i++){
            System.out.println(books.get(i).getTitle());
        }
        JSONArray ja = new JSONArray(books);
        return ja.toString();
    }
    @GetMapping("/api/book/{id}")
    public String book(@PathVariable int id) {
        Book book = bookRepository.findById(id).get();
        return new JSONObject(book).toString();
    }
    //@PostMapping(path ="/api/test/update", consumes = "application/json", produces = "application/json")
    @PostMapping(path = "/api/book/choose/{id}/{username}")// , consumes = "application/json", produces = "application/json"
    public String chooseBookApi(@PathVariable int id, @PathVariable String username) { // , @RequestBody LoginRequest req
        Book book = bookRepository.findById(id).get();
        User user = userRepository.findByUsername(username);
        book.setHere(false);
        IssuanceRequest ir = new IssuanceRequest(book.getTitle(), user, book);
        ir.setValid(true);
        ir.setAccept(false);
        ir.setSend(false);
        issuanceRequestRepository.save(ir);
        System.out.println(String.valueOf(id));
        System.out.println(user.getName());
        return new JSONObject(book).toString();
    }
}
