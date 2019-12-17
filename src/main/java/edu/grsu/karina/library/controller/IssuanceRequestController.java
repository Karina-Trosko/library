package edu.grsu.karina.library.controller;

import edu.grsu.karina.library.model.IssuanceRequest;
import edu.grsu.karina.library.model.Role;
import edu.grsu.karina.library.model.User;
import edu.grsu.karina.library.repository.IssuanceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller

public class IssuanceRequestController {
    @Autowired
    private IssuanceRequestRepository issuanceRequestRepository;

    @PreAuthorize("hasAuthority('EMPLOYEE')")
    @GetMapping("/requestsList")
    public String requestList(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("isAd", user.getRoles().contains(Role.ADMIN));
        model.addAttribute("isEmpl", user.getRoles().contains(Role.EMPLOYEE));
        model.addAttribute("requests", issuanceRequestRepository.findBySend(true));
        return "requestsList";
    }

    @PostMapping("/request/delete")
    public String delete(@RequestParam("id") int id, @AuthenticationPrincipal User user) {
        issuanceRequestRepository.findById(id).get().getBook().setHere(true);
        issuanceRequestRepository.deleteById(id);
        if (user.getRoles().contains(Role.EMPLOYEE))
            return "redirect:/requestsList";
        return "redirect:/user/RequestsList";
    }
    @PostMapping("/sendRequest")
    public String sendRequest(@RequestParam("id") int id) {
        IssuanceRequest ir = issuanceRequestRepository.findById(id).get();
        ir.setSend(true);
        issuanceRequestRepository.save(ir);
        return "redirect:/user/RequestsList";
    }
    @PostMapping("/acceptRequest")
    public String acceptRequest(@RequestParam("id") int id) {
        IssuanceRequest ir = issuanceRequestRepository.findById(id).get();
        ir.setAccept(true);
        ir.setDate(new Date());
        issuanceRequestRepository.save(ir);
        return "redirect:/requestsList";
    }

    @PreAuthorize("hasAuthority('EMPLOYEE')")
    @PostMapping("/request/return")
    public String requestReturn(@RequestParam("id") int id) {

        issuanceRequestRepository.findById(id).get().getBook().setHere(true);
        issuanceRequestRepository.deleteById(id);
        return "redirect:/requestsList";
    }

    @PreAuthorize("hasAuthority('EMPLOYEE')")
    @PostMapping("/request/search")
    public String search(@RequestParam String search, Model model, @AuthenticationPrincipal User user) {
        List<IssuanceRequest> ir;
        if (search != null && !search.isEmpty()) {
            ir = issuanceRequestRepository.findByName(search);
        } else {
            ir = issuanceRequestRepository.findAll();
        }

        model.addAttribute("requests", ir);
        model.addAttribute("isAd", user.getRoles().contains(Role.ADMIN));
        model.addAttribute("isEmpl", user.getRoles().contains(Role.EMPLOYEE));

        return "requestsList";
    }

}
