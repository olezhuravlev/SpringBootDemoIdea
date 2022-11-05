package com.example.sboot.controllers;

import com.example.sboot.model.Role;
import com.example.sboot.model.User;
import com.example.sboot.repository.UserRepository;
import com.example.sboot.security.AuthUser;
import com.example.sboot.utils.ValidationUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.EnumSet;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/account")
@AllArgsConstructor
@Slf4j
@Tag(name = "AccountController", description = "API controller")
public class AccountController implements RepresentationModelProcessor<RepositoryLinksResource> {

    private final UserRepository userRepository;

    private static final RepresentationModelAssemblerSupport<User, EntityModel<User>> ASSEMBLER =
            new RepresentationModelAssemblerSupport<>(AccountController.class, (Class<EntityModel<User>>) (Class<?>) EntityModel.class) {
                @Override
                public EntityModel<User> toModel(User user) {
                    return EntityModel.of(user, linkTo(AccountController.class).withSelfRel());
                }
            };

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<User> get(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get {}", authUser);
        return ASSEMBLER.toModel(authUser.getUser());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser) {
        log.info("delete {}", authUser);
        userRepository.deleteById(authUser.id());
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<EntityModel<User>> register(@Valid @RequestBody User user) {

        log.info("register {}", user);

        ValidationUtils.checkNew(user);
        user.setRoles(EnumSet.of(Role.USER));
        user = userRepository.save(user);

        // It's a good practice to provide created entity URL in response header.
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/account")
                .build()
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(ASSEMBLER.toModel(user));
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

    @Override
    public RepositoryLinksResource process(RepositoryLinksResource resource) {
        resource.add(linkTo(AccountController.class).withRel("account"));
        return resource;
    }

    @GetMapping(value = "/pageDemo", produces = MediaTypes.HAL_JSON_VALUE)
    public PagedModel<EntityModel<User>> pageDemo(Pageable pageable, PagedResourcesAssembler<User> pagedAssembler) {
        Page<User> users = userRepository.findAll(pageable);
        return pagedAssembler.toModel(users, ASSEMBLER);
    }
}
