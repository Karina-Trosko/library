package edu.grsu.karina.library.controller;

import edu.grsu.karina.library.model.IssuanceRequest;
import edu.grsu.karina.library.model.Role;
import edu.grsu.karina.library.model.User;
import edu.grsu.karina.library.repository.IssuanceRequestRepository;
import edu.grsu.karina.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/user")

public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IssuanceRequestRepository issuanceRequestRepository;

    @PreAuthorize("hasAuthority('EMPLOYEE')")
    @GetMapping
    public String userList(Model model, @AuthenticationPrincipal User user) {
//        model.addAttribute("users", userRepository.findByRoleCount(1));
        model.addAttribute("users", getUsersByRolesCount(1));
        model.addAttribute("isAd", user.getRoles().contains(Role.ADMIN));
        return "userList";
    }

    @GetMapping("/RequestsList")
    public String userRequests(Model model, @AuthenticationPrincipal User user) {
//        model.addAttribute("users", userRepository.findByRoleCount(1));
        model.addAttribute("requests",getRequestsByUser(user) );//
        model.addAttribute("isAd", user.getRoles().contains(Role.ADMIN));
        model.addAttribute("isEmpl",user.getRoles().contains(Role.EMPLOYEE));


        return "userRequestsList";
    }


    @PreAuthorize("hasAuthority('EMPLOYEE')")
    @GetMapping("{id}")
    public String edit(@PathVariable Long id, Model model,@AuthenticationPrincipal User userCurrent) {
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        //model.addAttribute("roles", Role.values());
        model.addAttribute("isAd", userCurrent.getRoles().contains(Role.ADMIN));
        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/addemployee")
    public String registration() {
        return "employeesAdd";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addemployee")
    public String addEmployee(User user, Model model) {//Map<String, Object> model
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            model.addAttribute("message", "Employee exists!"); //("message", "User exists!");
            //model.put("message", "User exists!");
            return "employeesAdd";
        }
        if (user.getUsername() == "" || user.getPassword() == "") {
            //model.put("message", "Username and password are required");
            return "employeesAdd";
        }
        user.setActive(true);
        user.setRoles(new HashSet<Role>(Arrays.asList(Role.USER, Role.EMPLOYEE)));
        userRepository.save(user);
        return "success";
    }

    @PreAuthorize("hasAuthority('EMPLOYEE')")
    @PostMapping("/edit")
    public String userSave(@ModelAttribute User req,@AuthenticationPrincipal User userCurrent, Model model) {
        User user = userRepository.findById(req.getId()).get();
        user.setUsername(req.getUsername());
        model.addAttribute("isAd", userCurrent.getRoles().contains(Role.ADMIN));
        user.setRoles(req.getRoles());
        userRepository.save(user);
        return "success";
    }

    @PreAuthorize("hasAuthority('EMPLOYEE')")
    @PostMapping("/delete")
    public String delete(@RequestParam("id") long id) {
        userRepository.deleteById(id);
        return "redirect:/books";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/employeesList")
    public String employeeList(Model model) {
//        model.addAttribute("users", userRepository.findByRoleCount(1));
        model.addAttribute("users", getUsersByRolesCount(2));

        return "employeeList";
    }

    public List<User> getUsersByRolesCount(int count) {
        List<User> allusers = userRepository.findAll();
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < allusers.size(); i++) {
            if (allusers.get(i).getRoleCount() == count) {
                users.add(allusers.get(i));
            }
        }
        return users;
    }
 public List<IssuanceRequest> getRequestsByUser(User user){
        List<IssuanceRequest> requests = issuanceRequestRepository.findAll();
        List<IssuanceRequest> requests1 = new ArrayList<IssuanceRequest>();
        for(int i=0;i<requests.size();i++){
            if(requests.get(i).getReader().getId()==user.getId())
                requests1.add(requests.get(i));
        }
        return requests1;
 }

}
