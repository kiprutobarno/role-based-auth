package com.tuts.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tuts.auth.payload.requests.UserRequest;
import com.tuts.auth.payload.responses.ResponseHandler;
import com.tuts.auth.services.UserService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService service;

    @Autowired
    ResponseHandler response;

    @PostMapping("/api/v1/users")
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserRequest userRequest) {
        service.saveUser(userRequest);
        return response.responseBuilder("User created Successfully", HttpStatus.CREATED);
    }

    // @CrossOrigin(origins = "http://localhost:3000/dashboard", allowCredentials =
    // "true")
    @GetMapping("/api/v1/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> getUsers() {
        return response.responseBuilder("Success", service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/api/v1/users/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Integer id) {
        return response.responseBuilder("Success", service.getOne(id),
                HttpStatus.OK);
    }

    @PutMapping("/api/v1/users/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Integer id, @RequestBody @Valid UserRequest userRequest) {

        service.update(id, userRequest);
        return response.responseBuilder("Success", service.update(id, userRequest), HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Integer id) {
        service.delete(id);
        return response.responseBuilder("Success", HttpStatus.OK);
    }
}