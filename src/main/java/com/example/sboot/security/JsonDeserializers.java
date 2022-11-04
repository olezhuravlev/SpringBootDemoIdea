package com.example.sboot.security;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;

public class JsonDeserializers {

    public static class PasswordDeserializer extends JsonDeserializer<String> {

        @Autowired
        PasswordEncoder passwordEncoder;

        // curl -X POST -H "Content-Type: application/json" --user admin@gmail.com:admin -d '{"email":"test2@test.com","firstName":"Test","lastName":"Test","password":"test","roles":["USER","ADMIN"]}' http://localhost:8080/api/users
        @Override
        public String deserialize(JsonParser parser, DeserializationContext context) throws IOException, JacksonException {
            ObjectCodec objectCodec = parser.getCodec();
            JsonNode node = objectCodec.readTree(parser);
            String rawPassword = node.asText();
            return passwordEncoder.encode(rawPassword);
        }
    }
}