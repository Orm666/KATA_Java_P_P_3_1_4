package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;

        List<Role> roleList = this.roleService.getAllRoles();

        User user2 = new User("User", "user", "2", (byte) 12, "2");
        this.userService.save(user2);

        user2.addRoleToUser(roleList.get(0));
        user2.addRoleToUser(roleList.get(1));
        this.userService.update(user2);

        User user1 = new User("Admin", "admin", "1", (byte) 12, "1");
        this.userService.save(user1);

        user1.addRoleToUser(roleList.get(0));
        user1.addRoleToUser(roleList.get(1));
        this.userService.update(user1);
    }

    @GetMapping
    public String displayAllUsers(Model model, Principal principal, @RequestParam(value = "isAddUserPage", required = false) Boolean isAddUserPage) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("userAuth", userService.findByUsername(principal.getName()));

        if (isAddUserPage == null) {
            isAddUserPage = false;
        }
        model.addAttribute("isAddUserPage", isAddUserPage);

        model.addAttribute("newUser", new User());
        model.addAttribute("rolesList", roleService.getAllRoles());

        return "bootstrapAllUser";
    }

    @GetMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/updateUser")
    public String updateNewUser(@RequestParam("id") Long id, @RequestParam("firstName") String firstName,
                                @RequestParam("lastName") String lastName, @RequestParam("age") Byte age,
                                @RequestParam("email") String email, @RequestParam("password") String password,
                                @RequestParam("roles") String[] roles) {

        User user = new User(firstName, lastName, email, age, password);
        user.setId(id);
        List<Role> roleList = this.roleService.getAllRoles();
        for (Role role : roleList) {
            for (String newRole : roles) {
                if (role.getId().equals(Long.parseLong(newRole))) {
                    user.addRoleToUser(role);
                }
            }
        }

        userService.update(user);
        return "redirect:/admin";
    }

    @GetMapping("/deleteUser")
    public String removeUser(@RequestParam("id") Long id) {
        userService.remove(id);
        return "redirect:/admin";
    }

}