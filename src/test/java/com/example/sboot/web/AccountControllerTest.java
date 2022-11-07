package com.example.sboot.web;

import com.example.sboot.model.User;
import com.example.sboot.repository.UserRepository;
import com.example.sboot.utils.UserTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.sboot.controllers.AccountController.URL;
import static com.example.sboot.utils.JsonUtils.writeValue;
import static com.example.sboot.utils.UserTestUtils.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountControllerTest extends AbstractControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders
                .get(URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonMatcher(UserTestUtils.user, UserTestUtils::assertNoIdEquals));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders
                .get(URL))
                .andExpect(status().isUnauthorized());

    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders
                .delete(URL))
                .andExpect(status().isNoContent());
        Assertions.assertFalse(userRepository.findById(USER_ID).isPresent());
        Assertions.assertTrue(userRepository.findById(ADMIN_ID).isPresent());
    }

    @Test
    void register() throws Exception {
        User newUser = UserTestUtils.getNew();
        User registeredUser = asUser(perform(MockMvcRequestBuilders
                .post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newUser)))
                .andExpect(status().isCreated()).andReturn());

        UserTestUtils.assertNoIdEquals(registeredUser, newUser);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update() throws Exception {
        User updatedUser = UserTestUtils.getUpdated();
        perform(MockMvcRequestBuilders
                .put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updatedUser)))
                .andExpect(status().isNoContent());

        UserTestUtils.assertEquals(updatedUser, userRepository.findById(USER_ID).orElseThrow());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateHtmlUnsafeFirstName() throws Exception {
        User updated = UserTestUtils.getUpdated();
        updated.setFirstName("<script>alert()</script>");
        perform(MockMvcRequestBuilders.put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateHtmlUnsafeLastName() throws Exception {
        User updated = UserTestUtils.getUpdated();
        updated.setLastName("<script>alert()</script>");
        perform(MockMvcRequestBuilders.put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateHtmlUnsafeEmail() throws Exception {
        User updated = UserTestUtils.getUpdated();
        updated.setEmail("<script>alert()</script>");
        perform(MockMvcRequestBuilders.put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}
