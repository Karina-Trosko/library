package edu.grsu.karina.library.controller;

import edu.grsu.karina.library.model.IssuanceRequest;
import edu.grsu.karina.library.repository.IssuanceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class IssuanceRequestController {
    @Autowired
    private IssuanceRequestRepository issuanceRequestRepository;
    @GetMapping("/requestsList")
    public String requestList(Model model){
        model.addAttribute("requests",issuanceRequestRepository.findAll());
        return "requestsList";
    }

    @PostMapping("/request/delete")
    public String delete(@RequestParam("id") int id) {
        issuanceRequestRepository.findById(id).get().getBook().setHere(true);
        issuanceRequestRepository.deleteById(id);

        return "redirect:/requestsList";
    }

    @PostMapping("/request/return")
    public String requestReturn(@RequestParam("id") int id) {

        issuanceRequestRepository.findById(id).get().getBook().setHere(true);
        issuanceRequestRepository.deleteById(id);
        return "redirect:/requestsList";
    }

    @PostMapping("/request/search")
    public String search(@RequestParam String search, Model model) {
        List<IssuanceRequest> ir;
        if(search!=null&& !search.isEmpty()){
            ir= issuanceRequestRepository.findByName(search);
        } else {
            ir= issuanceRequestRepository.findAll();
        }

        model.addAttribute("requests", ir);

        return "requestsList";
    }

}
