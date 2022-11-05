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

import static com.example.sboot.utils.JsonUtils.writeValue;
import static com.example.sboot.utils.UserTestUtils.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends AbstractControllerTest {

    public static final String URL = "/api/users/";

    @Autowired
    private UserRepository userRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + USER_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonMatcher(UserTestUtils.user, UserTestUtils::assertNoIdEquals));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        // Todo: Check content yourself.
        perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaTypes.HAL_JSON_VALUE));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getByEmail() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + "search/by-email?email=" + ADMIN_MAIL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonMatcher(UserTestUtils.admin, UserTestUtils::assertNoIdEquals));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL + USER_ID))
                .andExpect(status().isNoContent());
        Assertions.assertFalse(userRepository.findById(USER_ID).isPresent());
        Assertions.assertTrue(userRepository.findById(ADMIN_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        User newUser = UserTestUtils.getNew();
        perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonMatcher(newUser, UserTestUtils::assertNoIdEquals));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        User updatedUser = UserTestUtils.getUpdated();
        perform(MockMvcRequestBuilders.put(URL + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updatedUser)))
                .andExpect(status().isNoContent());
        UserTestUtils.assertEquals(updatedUser, userRepository.findById(USER_ID).orElseThrow());
    }
}
