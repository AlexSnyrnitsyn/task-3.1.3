package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping("/create")
    public String addUser(@ModelAttribute("user") User user){
        userService.createUser(user);
        return "redirect:/admin";
    }

    @GetMapping
    public String getAllUsers(Model model){
        model.addAttribute("allUsers", userService.getAllUser());
        model.addAttribute("allRoles", roleService.roleList());
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "admin-page";
    }

    @GetMapping("/{id}/updateProfile")
    public String updateUser(@PathVariable("id") Long id, Model model){
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("allRoles", roleService.roleList());
        return "fragments/user-updateform";
    }

    @RequestMapping("/editUser/{id}")
    public String edit(@PathVariable("id") Long id, @ModelAttribute("user") User user){
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/deleteProfile")
    public String userDeleteProfile(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("allRoles", roleService.roleList());
        return "fragments/user-deleteform";
    }

    @RequestMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
