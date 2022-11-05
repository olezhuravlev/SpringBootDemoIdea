package com.example.sboot.repository;

import com.example.sboot.model.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Tag(name = "UserRepository", description = "This is an implementation of JpaRepository")
public interface UserRepository extends JpaRepository<User, Integer> {

    @RestResource(rel = "by-email", path = "by-email")
    @Query("SELECT u FROM User u WHERE u.email = LOWER(:email)")
    Optional<User> findByEmailIgnoreCase(String email); // @Param("email") String email - isn't necessary anymore!

    @RestResource(rel = "by-lastname", path = "by-lastname")
    Page<User> findByLastNameContainingIgnoreCase(String lastName, Pageable page);
}
