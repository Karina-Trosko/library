package edu.grsu.karina.library.controller;

import edu.grsu.karina.library.model.Book;
import edu.grsu.karina.library.model.IssuanceRequest;
import edu.grsu.karina.library.model.Role;
import edu.grsu.karina.library.model.User;
import edu.grsu.karina.library.payload.LoginRequest;
import edu.grsu.karina.library.repository.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class RestUserController {
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

    public List<IssuanceRequest> getRequestsByUserId(int userId) {
        List<IssuanceRequest> requests = issuanceRequestRepository.findAll();

        List<IssuanceRequest> requests1 = new ArrayList<IssuanceRequest>();
        for (int i = 0; i < requests.size(); i++) {
            System.out.println(String.valueOf(requests.get(i).getReader().getId()));

            if (requests.get(i).getReader().getId() == userId)
                requests1.add(requests.get(i));
        }
        return requests1;
    }

    @GetMapping("/api/requests/{id}")
    public String userRequests(@PathVariable int id) {
        List<IssuanceRequest> requests = getRequestsByUserId(id);
        JSONArray ja = new JSONArray(requests);
        return ja.toString();
    }

    @PostMapping(path = "/api/request/delete/{id}/{userId}")
    public String deleteRequestApi(@PathVariable int id, @PathVariable int userId) {
    issuanceRequestRepository.findById(id).get().getBook().setHere(true);
    issuanceRequestRepository.deleteById(id);
    return new JSONArray(getRequestsByUserId(userId)).toString();
    }
    @PostMapping(path = "/api/request/send/{id}/{userId}")// , consumes = "application/json", produces = "application/json"
    public String sendRequestApi(@PathVariable int id, @PathVariable int userId) { // , @RequestBody LoginRequest req
        IssuanceRequest ir = issuanceRequestRepository.findById(id).get();
        ir.setSend(true);
        issuanceRequestRepository.save(ir);
        return new JSONArray(getRequestsByUserId(userId)).toString();
    }

    @PostMapping(path = "/api/login")
    public String loginFromApi(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());
        if (user == null) {
            return "User not found";
        } else {
            return new JSONObject(user).toString();
        }
    }
    @PostMapping(path = "/api/registration")
    public String registrationFromApi(@RequestBody User newUser) {
        User user = userRepository.findByUsername(newUser.getUsername());
        if (user != null) {
            return "User with this username already exists.";
        } else if (newUser.getUsername() == "" || newUser.getPassword() == ""){
            return "Username and password are required";
        } else {
            newUser.setActive(true);
            newUser.setRoles(Collections.singleton(Role.USER));
            userRepository.save(newUser);
            return new JSONObject(newUser).toString();
        }
    }
}
