package com.example.sboot;

import com.example.sboot.model.User;
import com.example.sboot.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.SpringServletContainerInitializer;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.HashSet;

@SpringBootApplication
@AllArgsConstructor
public class SpringBootDemoIdeaApplication /*implements ApplicationRunner*/ extends SpringBootServletInitializer{

//    private final SpringBootServletInitializer springBootServletInitializer;
//    private final ServletContainerInitializer servletContainerInitializer;

//    private final UserRepository userRepository;

    // For "java -jar" to work.
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoIdeaApplication.class, args);
    }

    // Tomcat invokes this method.
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
    }

    // Tomcat invokes this method.
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return super.configure(builder);
    }

//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        userRepository.save(new User("user@gmail.com", "User_First", "User_Last", "pwd", new HashSet<>()));
//        userRepository.save(new User("admin@mail.ru", "Admin_First", "Admin_Last", "pwd", new HashSet<>()));
//        System.out.println(userRepository.findAll());
//    }
}
