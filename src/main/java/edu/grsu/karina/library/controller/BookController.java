package edu.grsu.karina.library.controller;

import edu.grsu.karina.library.model.*;
import edu.grsu.karina.library.payload.BookRequest;
import edu.grsu.karina.library.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class BookController {

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

    @GetMapping("/books")
    public String books(@RequestParam(required = false) String name, Model model, @AuthenticationPrincipal User req) {

        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("books", bookRepository.findAll());
        model.addAttribute("literatureTypes", literatureTypeRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());

        boolean isEmpl = req.getRoles().contains(Role.EMPLOYEE);
        model.addAttribute("isEmpl", isEmpl);
        model.addAttribute("isAd", req.getRoles().contains(Role.ADMIN));
        return "books";
    }

    @PreAuthorize("hasAuthority('EMPLOYEE')")
    @PostMapping("/book")
    public String create(@ModelAttribute BookRequest req, Model model, @AuthenticationPrincipal User user) {
        Book book = new Book();
        Author author = authorRepository.findById(req.getAuthorId()).get();
        Genre genre = genreRepository.findById(req.getGenreId()).get();
        model.addAttribute("isAd",user.getRoles().contains(Role.ADMIN));
        LiteratureType literatureType = literatureTypeRepository.findById(req.getLiteratureTypeId()).get();
        if (req.getPublishingHouse() == null || req.getTitle() == null || author == null || literatureType == null || genre == null) {
            model.addAttribute("message", "All fields are requaet!");
            return "bookcreat";
        }

        book.setTitle(req.getTitle());
        book.setYearOfPublishing(req.getYearOfPublishing());
        book.setAuthor(author);
        book.setLiteratureType(literatureType);
        book.setGenre(genre);
        book.setNumbOfPages(req.getNumbOfPages());
        book.setPublishingHouse(req.getPublishingHouse());
        book.setHere(req.getHere());
        book.setOnlyInReadingRoom(req.getOnlyInReadingRoom());

        bookRepository.save(book);
        return "success";
    }

    @PreAuthorize("hasAuthority('EMPLOYEE')")
    @GetMapping("/book/create")
    public String bookCreate(Model model,@AuthenticationPrincipal User user) {

        List<Author> authors = authorRepository.findAll();
        List<LiteratureType> literatureTypes = literatureTypeRepository.findAll();
        List<Genre> genres = genreRepository.findAll();


        model.addAttribute("authors", authors);
        model.addAttribute("literatureTypes", literatureTypes);
        model.addAttribute("genres", genres);
        model.addAttribute("isAd",user.getRoles().contains(Role.ADMIN));


        return "bookcreat";
    }

    @PreAuthorize("hasAuthority('EMPLOYEE')")
    @GetMapping("/book/{id}")
    public String book(@PathVariable int id, Model model, @AuthenticationPrincipal User req) {
        Book book = bookRepository.findById(id).get();
        List<Author> authors = authorRepository.findAll();
        List<LiteratureType> literatureTypes = literatureTypeRepository.findAll();
        List<Genre> genres = genreRepository.findAll();

        model.addAttribute("book", book);
        model.addAttribute("authors", authors);
        model.addAttribute("literatureTypes", literatureTypes);
        model.addAttribute("genres", genres);
        model.addAttribute("isAd",req.getRoles().contains(Role.ADMIN));


        return "book";
    }

    @PreAuthorize("hasAuthority('EMPLOYEE')")
    @PostMapping("/book/update")
    public String update(@ModelAttribute Book req, Model model,@AuthenticationPrincipal User user ) {
        model.addAttribute("isAd",user.getRoles().contains(Role.ADMIN));
        Book book = bookRepository.findById(req.getId()).get();
        book.setTitle(req.getTitle());
        book.setLiteratureType(req.getLiteratureType());
        book.setOnlyInReadingRoom(req.getOnlyInReadingRoom());
        book.setNumbOfPages(req.getNumbOfPages());
        book.setHere(req.getHere());
        book.setAuthor(req.getAuthor());
        book.setYearOfPublishing(req.getYearOfPublishing());
        book.setGenre(req.getGenre());
        bookRepository.save(book);
        return "success";
    }

    @PreAuthorize("hasAuthority('EMPLOYEE')")
    @PostMapping("/book/delete")
    public String delete(@RequestParam("id") int id) {
        bookRepository.deleteById(id);
        return "redirect:/books";
    }

    @PostMapping("/book/choose")
    public String choose(@RequestParam("id") int id, @AuthenticationPrincipal User req) {
        Book book = bookRepository.findById(id).get();
        book.setHere(false);
        IssuanceRequest ir = new IssuanceRequest(book.getTitle(), req, book);
        ir.setValid(true);
        issuanceRequestRepository.save(ir);


        return "redirect:/books";
    }

    @GetMapping("/")
    public String start() {
        return "start";
    }

    @PostMapping("search")
    public String search(@RequestParam String search, Model model,@AuthenticationPrincipal User user) {
        List<Book> books;
        if (search != null && !search.isEmpty()) {
            books = bookRepository.findByTitle(search);
        } else {
            books = bookRepository.findAll();
        }
model.addAttribute("isAd",user.getRoles().contains(Role.ADMIN));
        model.addAttribute("books", books);

        return "books";
    }
}
