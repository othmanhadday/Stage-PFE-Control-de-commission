package com.hadday.gestion_commission.controller;

import com.hadday.gestion_commission.PasswordEncoder;
import com.hadday.gestion_commission.Service.UserService;
import com.hadday.gestion_commission.entities.RoleApp;
import com.hadday.gestion_commission.entities.UserApp;
import com.hadday.gestion_commission.repositories.PermissionRepository;
import com.hadday.gestion_commission.repositories.RoleRepository;
import com.hadday.gestion_commission.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @RequestMapping
    public String index(Model model) {
        List<UserApp> usersActivated = userRepository.findUserAppsByActivateTrueAndDeletedFalse();
        List<UserApp> usersNotActivated = userRepository.findUserAppsByActivateFalseAndDeletedFalse();
        model.addAttribute("usersActivated", usersActivated);
        model.addAttribute("usersNotActivated", usersNotActivated);
        model.addAttribute("user", new UserApp());
        model.addAttribute("allRoles", roleRepository.findRoleAppsByDeletedFalse());
        model.addAttribute("allPermissions", permissionRepository.findAll());
        return "/gestion_users/users";
    }

    @PostMapping("/createOrUpdateUser")
    public String createOrUpdateUser(@ModelAttribute("user") UserApp userApp) {
        userService.addNewUser(userApp);
        return "redirect:/user";
    }


    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Optional<Long> id) {
        userService.deleteUser(id);
        return "redirect:/user";
    }

    @GetMapping("/reinitialiser/{id}")
    public String reinitialiserPwd(@PathVariable Optional<Long> id, Model model, RedirectAttributes redirAttrs) {
        Map<String, UserApp> map = new HashMap<String, UserApp>();
        map = userService.reinitialiserPwd(id);
        String pwd = "";
        Iterator<String> passwords = map.keySet().iterator();
        while (passwords.hasNext()) {
            pwd = passwords.next();
        }
        redirAttrs.addFlashAttribute("passwordGenerer", pwd);
        redirAttrs.addFlashAttribute("userNom", map.get(pwd).getNom());

        return "redirect:/user";
    }

    @GetMapping("/profile/{username}")
    public String profile(@PathVariable("username") String username, Model model) {
        UserApp user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        return "/gestion_users/pages-profile";
    }

    @PostMapping("/profileUpdateUser")
    public String profileUpdateUser(@ModelAttribute("user") UserApp userApp) {
        userApp = userService.addNewUser(userApp);
        return "redirect:/user/profile/" + userApp.getUsername();
    }
}

@RestController
class RestUserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/user/{id}")
    public Optional<UserApp> getUserById(@PathVariable Optional<Long> id) {
        return userRepository.findById(id);
    }

    @GetMapping("/role/{id}")
    public Optional<RoleApp> getGetById(@PathVariable Optional<Long> id) {
        return roleRepository.findById(id);
    }

    @GetMapping("/roles/{id}")
    public List<UserApp> getUserByRole(@PathVariable Optional<Long> id){
        RoleApp roleApp = roleRepository.findById(id).get();
        return userRepository.findUserAppsByRolesAndActivateTrueAndDeletedFalse(roleApp);
    }
}
