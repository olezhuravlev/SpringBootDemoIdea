package com.example.sboot.controllers;

import com.example.sboot.model.Role;
import com.example.sboot.model.User;
import com.example.sboot.repository.UserRepository;
import com.example.sboot.security.AuthUser;
import com.example.sboot.utils.ValidationUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/account")
@AllArgsConstructor
@Slf4j
public class AccountController {

    private final UserRepository userRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get {}", authUser);
        return authUser.getUser();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser) {
        log.info("delete {}", authUser);
        userRepository.deleteById(authUser.id());
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody User user) {

        log.info("register {}", user);

        ValidationUtils.checkNew(user);
        user.setRoles(Set.of(Role.USER));
        user = userRepository.save(user);

        // It's a good practice to provide created entity URL in response header.
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/account")
                .build()
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(user);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody User user, @AuthenticationPrincipal AuthUser authUser) {

        log.info("update {} to {}", authUser, user);

        User oldUser = authUser.getUser();

        // User can update only themselves.
        ValidationUtils.assureIdConsistent(user, oldUser.id());
        user.setRoles(oldUser.getRoles());
        if (user.getPassword() == null) {
            user.setPassword(oldUser.getPassword());
        }
        userRepository.save(user);
    }
}
