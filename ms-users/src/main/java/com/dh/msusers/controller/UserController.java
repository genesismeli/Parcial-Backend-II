package com.dh.msusers.controller;

import com.dh.msusers.model.User;
import com.dh.msusers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/findBy")
    public ResponseEntity<User> getAll(@RequestParam String id) {
        return ResponseEntity.ok().body(userService.getAllBill(id));
    }
}
