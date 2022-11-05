package com.example.sboot.utils;

import com.example.sboot.model.Role;
import com.example.sboot.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTestUtils {
    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final String USER_MAIL = "user@gmail.com";
    public static final String ADMIN_MAIL = "admin@gmail.com";
    public static final User user = new User(USER_ID, USER_MAIL, "User_First", "User_Last", "user", List.of(Role.USER));
    public static final User admin = new User(ADMIN_ID, ADMIN_MAIL, "Admin_First", "Admin_Last", "admin", List.of(Role.ADMIN, Role.USER));

    public static User getNew() {
        return new User(null, "new@gmail.com", "New_First", "New_Last", "new", List.of(Role.USER));
    }

    public static User getUpdated() {
        return new User(USER_ID, "user_update@gmail.com", "User_Update_First", "User_Update_Last", "update", List.of(Role.USER));
    }

    public static void assertEquals(User actual, User expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("password").isEqualTo(expected);
    }

    // No id in HATEOAS answer.
    public static void assertNoIdEquals(User actual, User expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("id", "password").isEqualTo(expected);
    }

    public static User asUser(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        String jsonActual = mvcResult.getResponse().getContentAsString();
        return JsonUtils.readValue(jsonActual, User.class);
    }

    public static ResultMatcher jsonMatcher(User expected, BiConsumer<User, User> equalsAssertion) {
        return mvcResult -> equalsAssertion.accept(asUser(mvcResult), expected);
    }
}
